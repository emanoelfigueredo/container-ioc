package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInterrompidaException;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseConfiguracaoComConfiguracaoCorreta;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseConfiguracaoCorretaSemConfiguracao;

@Tag("unitario")
class ManipuladorMetodosConfiguracoesDependenciasTest {

	private ManipuladorMetodosConfiguracoesDependencias manipulador;

	@BeforeEach
	public void setup() {
		this.manipulador = new ManipuladorMetodosConfiguracoesDependencias();
	}

	@Test
	public void deveriaRetornarUmMapa_OndeSoTemUmElemento_ChaveAClasseDeConfiguracao_ValorSeuMetodoDeConfiguracao()
			throws NoSuchMethodException, SecurityException {
		List<Class<?>> listaClassesConfiguracao = Arrays.asList(ClasseConfiguracaoComConfiguracaoCorreta.class);
		Map<Class<?>, Method> metodosConfiguracao = this.manipulador.getMetodosConfiguracao(listaClassesConfiguracao);
		assertTrue(metodosConfiguracao.size() == 1);
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoComConfiguracaoCorreta.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoComConfiguracaoCorreta.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
	}

	@Test
	public void deveriaRetornarUmMapa_OndeExistemDoisElementos_ChaveAClasseDeConfiguracao_ValorSeuMetodoDeConfiguracao()
			throws NoSuchMethodException, SecurityException {
		List<Class<?>> listaClassesConfiguracao = Arrays.asList(ClasseConfiguracaoComConfiguracaoCorreta.class,
				ClasseConfiguracaoCorretaSemConfiguracao.class);
		Map<Class<?>, Method> metodosConfiguracao = this.manipulador.getMetodosConfiguracao(listaClassesConfiguracao);
		assertTrue(metodosConfiguracao.size() == 2);
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoComConfiguracaoCorreta.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoComConfiguracaoCorreta.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoCorretaSemConfiguracao.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoCorretaSemConfiguracao.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
	}

	/*
	 * Adicionei uma configuraço de dependências na classe
	 * ClasseConfiguracaoComConfiguracaoCorreta.
	 * 
	 * Deve apontar para ArrayList quando for pedido uma List.
	 */

	@Test
	public void deveriaRetornarUmBuilderConfigurado_QuandoInvocarOsMetodosDeConfiguracoes()
			throws NoSuchMethodException, SecurityException,
			ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
		Map<Class<?>, Method> metodosConfiguracao = new HashMap<>();
		Method metodoConfiguracao1 = ClasseConfiguracaoCorretaSemConfiguracao.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);
		Method metodoConfiguracao2 = ClasseConfiguracaoComConfiguracaoCorreta.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);

		metodosConfiguracao.put(ClasseConfiguracaoCorretaSemConfiguracao.class, metodoConfiguracao1);
		metodosConfiguracao.put(ClasseConfiguracaoComConfiguracaoCorreta.class, metodoConfiguracao2);

		List<ConfiguracaoIoCBuilder> listaBuildersConfigurados = this.manipulador.invocarMetodos(metodosConfiguracao);
		assertTrue(listaBuildersConfigurados.size() == 2);
	}

}
