package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo1;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

@Tag("unitario")
class ManipuladorClassesConfiguracaoDependenciasTest {

	@Mock
	private GerenteDeClasses gerenteClasses;

	@InjectMocks
	private ManipuladorClassesConfiguracaoDependencias manipulador;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deveriaRetornarUmaLista_ContendoAClasseDeConfiguracaoPrototipo1_QuandoUsarMetodoObterClassesDeConfiguracao() throws PacoteInexistenteException {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class)).thenReturn(Arrays.asList(ClasseConfiguracaoPrototipo1.class));
		List<Class<?>> classesConfiguracao = this.manipulador.obterClassesDeConfiguracao();
		assertTrue(classesConfiguracao.size() == 1);
		assertTrue(classesConfiguracao.contains(ClasseConfiguracaoPrototipo1.class));
	}

	@Test
	public void deveriaRetornarListaVazia_QuandoUsarMetodoObterClassesDeConfiguracao_ENaoHouverClasseDeConfiguracao() throws PacoteInexistenteException {
		when(this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class)).thenReturn(new ArrayList<Class<?>>());
		List<Class<?>> classesConfiguracao = this.manipulador.obterClassesDeConfiguracao();
		assertTrue(classesConfiguracao.isEmpty());
	}

}
