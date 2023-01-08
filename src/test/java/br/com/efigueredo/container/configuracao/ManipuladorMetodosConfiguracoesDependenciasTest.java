package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo1;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo3;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo4;
import br.com.efigueredo.container.configuracao.prototipos.ClasseConfiguracaoPrototipo5;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInterrompidaException;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

@Tag("unitario")
class ManipuladorMetodosConfiguracoesDependenciasTest {

	private ManipuladorMetodosConfiguracoesDependencias manipulador;
	private ConfiguracaoIoC configuracao;

	@BeforeEach
	public void setup() {
		this.configuracao = new ConfiguracaoIoC();
		this.manipulador = new ManipuladorMetodosConfiguracoesDependencias(this.configuracao);
	}

	@Test
	public void deveriaRetornarUmMapa_OndeSoTemUmElemento_ChaveAClasseDeConfiguracao_ValorSeuMetodoDeConfiguracao()
			throws PacoteInexistenteException, NoSuchMethodException, SecurityException {
		List<Class<?>> listaClassesConfiguracao = Arrays.asList(ClasseConfiguracaoPrototipo1.class);
		Map<Class<?>, Method> metodosConfiguracao = this.manipulador.getMetodosConfiguracao(listaClassesConfiguracao);
		assertTrue(metodosConfiguracao.size() == 1);
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoPrototipo1.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoPrototipo1.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
	}

	@Test
	public void deveriaRetornarUmMapa_OndeExistemDoisElementos_ChaveAClasseDeConfiguracao_ValorSeuMetodoDeConfiguracao()
			throws PacoteInexistenteException, NoSuchMethodException, SecurityException {
		List<Class<?>> listaClassesConfiguracao = Arrays.asList(ClasseConfiguracaoPrototipo1.class,
				ClasseConfiguracaoPrototipo3.class);
		Map<Class<?>, Method> metodosConfiguracao = this.manipulador.getMetodosConfiguracao(listaClassesConfiguracao);
		assertTrue(metodosConfiguracao.size() == 2);
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoPrototipo1.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoPrototipo1.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
		assertTrue(metodosConfiguracao.containsKey(ClasseConfiguracaoPrototipo3.class));
		assertTrue(metodosConfiguracao.containsValue(
				ClasseConfiguracaoPrototipo3.class.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class)));
	}

	/*
	 * Adicionei uma configuraço de dependências na classe
	 * ClasseConfiguracaoPrototipo3.
	 * 
	 * Deve apontar para ArrayList quando for pedido uma List.
	 */

	@Test
	public void deveriaConfigurarOObjetoConfiguracaoIoC_QuandoInvocarOsMetodosDeConfiguracoes()
			throws PacoteInexistenteException, NoSuchMethodException, SecurityException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
		Map<Class<?>, Method> metodosConfiguracao = new HashMap<>();
		Method metodoConfiguracao1 = ClasseConfiguracaoPrototipo1.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);
		Method metodoConfiguracao2 = ClasseConfiguracaoPrototipo3.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);

		metodosConfiguracao.put(ClasseConfiguracaoPrototipo1.class, metodoConfiguracao1);
		metodosConfiguracao.put(ClasseConfiguracaoPrototipo3.class, metodoConfiguracao2);

		this.manipulador.invocarMetodos(metodosConfiguracao);

		Class<?> objetoConfigurado = this.configuracao.getConfiguracao(List.class);
		assertEquals(objetoConfigurado, ArrayList.class);
		Map<Class<?>, Class<?>> mapaConfiguracaoDependencia = this.configuracao.getMapaConfiguracaoDependencia();
		assertTrue(mapaConfiguracaoDependencia.size() == 1);
		assertTrue(mapaConfiguracaoDependencia.containsKey(List.class));
		assertTrue(mapaConfiguracaoDependencia.containsValue(ArrayList.class));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoADependenciaValorNaoForImplementacaoDaInterfaceChave() throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException, NoSuchMethodException, SecurityException {
		Map<Class<?>, Method> metodosConfiguracao = new HashMap<>();
		Method metodoConfiguracao = ClasseConfiguracaoPrototipo4.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);
		metodosConfiguracao.put(ClasseConfiguracaoPrototipo4.class, metodoConfiguracao);
		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.manipulador.invocarMetodos(metodosConfiguracao));
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoAClasseValorForInterface() throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException, NoSuchMethodException, SecurityException {
		Map<Class<?>, Method> metodosConfiguracao = new HashMap<>();
		Method metodoConfiguracao = ClasseConfiguracaoPrototipo5.class.getMethod("configuracao",
				InterfaceConfiguracaoIoCBuilder.class);
		metodosConfiguracao.put(ClasseConfiguracaoPrototipo5.class, metodoConfiguracao);
		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.manipulador.invocarMetodos(metodosConfiguracao));
	}

}
