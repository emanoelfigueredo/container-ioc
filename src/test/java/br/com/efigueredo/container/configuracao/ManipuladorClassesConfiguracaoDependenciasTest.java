package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoCorreta.Configuracao1;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoCorretaSemConfiguracao.Configuracao2;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaImplementacao.Configuracao3;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaValorInterface.Configuracao4;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoSemHerancaClasseConfigura.Configuracao5;

@Tag("unitaruio")
class ManipuladorClassesConfiguracaoDependenciasTest {

	private ManipuladorClassesConfiguracaoDependencias manipulador;

	public void setUp(String pacoteRaiz) throws ContainerIocException {
		Reflections reflections = new Reflections(pacoteRaiz, new TypeAnnotationsScanner(), new SubTypesScanner(false));
		this.manipulador = new ManipuladorClassesConfiguracaoDependencias(reflections);
	}

	@Test
	public void deveriaRetornarUmaLista_ContentoAs5ClassesDeConfiguracoes_NoPacoteDePrototipos() throws ContainerIocException {
		this.setUp("br.com.efigueredo.container.prototipos_com_configuracoes");
		List<Class<?>> classesConfiguracao = this.manipulador.obterClassesAnotadasComConfiguracaoDependencia();
		assertTrue(classesConfiguracao.size() == 5);
		assertTrue(classesConfiguracao.contains(Configuracao1.class));
		assertTrue(classesConfiguracao.contains(Configuracao2.class));
		assertTrue(classesConfiguracao.contains(Configuracao3.class));
		assertTrue(classesConfiguracao.contains(Configuracao4.class));
		assertTrue(classesConfiguracao.contains(Configuracao5.class));
	}

	@Test
	public void deveriaRetornarListaVazia_QuandoUsarMetodoObterClassesDeConfiguracao_ENaoHouverClasseDeConfiguracao_PacoteSemClassesConfiguracoes() throws ContainerIocException {
		this.setUp("br.com.efigueredo.container.prototipos_sem_configuracoes");
		List<Class<?>> classesConfiguracao = this.manipulador.obterClassesAnotadasComConfiguracaoDependencia();
		assertTrue(classesConfiguracao.isEmpty());
	}

}
