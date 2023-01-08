package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo1;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo3;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipoSemSuperClasse2;
import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

class GerenteDeConfiguracoesDeDependenciasTest {

	@Mock
	private GerenteDeClasses gerenteClasses;

	@InjectMocks
	private GerenteDeConfiguracoesDeDependencias gerente;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarUmaLista_ContendoAClasseDeConfiguracaoPrototipo1_QuandoUsarMetodoObterClassesDeConfiguracao() throws PacoteInexistenteException {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class)).thenReturn(Arrays.asList(ClasseConfiguracaoPrototipo1.class));
		List<Class<?>> classesConfiguracao = this.gerente.obterClassesDeConfiguracao();
		assertTrue(classesConfiguracao.size() == 1);
		assertTrue(classesConfiguracao.contains(ClasseConfiguracaoPrototipo1.class));
	}

	@Test
	void deveriaRetornarListaVazia_QuandoUsarMetodoObterClassesDeConfiguracao_ENaoHouverClasseDeConfiguracao() throws PacoteInexistenteException {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class)).thenReturn(new ArrayList<Class<?>>());
		List<Class<?>> classesConfiguracao = this.gerente.obterClassesDeConfiguracao();
		assertTrue(classesConfiguracao.isEmpty());
	}

	@Test
	public void naoDeveriaLancarExcecao_HerancaConfiguracaoNaoIdentificadaException_SeAClasseDeConfiguracaoForFilhaDaClasse_ConfiguracaoDependenciaIoC()
			throws HerancaConfiguracaoNaoIdentificadaException {
		Class<?> classe = ClasseConfiguracaoPrototipo1.class;
		assertDoesNotThrow(() -> this.gerente.verificarSeExtendeConfiguracaoDependenciaIoC(classe));
	}

	
	
	@Test
	public void deveriaLancarExcecao_HerancaConfiguracaoNaoIdentificadaException_SeAClasseDeConfiguracaoNaoForFilhaDaClasse_ConfiguracaoDependenciaIoC()
			throws HerancaConfiguracaoNaoIdentificadaException {
		Class<?> classe = ClasseConfiguracaoPrototipoSemSuperClasse2.class;
		assertThrows(HerancaConfiguracaoNaoIdentificadaException.class,
				() -> this.gerente.verificarSeExtendeConfiguracaoDependenciaIoC(classe));
	}



	

}
