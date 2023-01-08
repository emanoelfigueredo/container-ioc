package br.com.efigueredo.container.configuracao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoIoT.ConfiguracaoIoTBuilder;
import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

/**
 * <h4>Classe responsável por gerenciar as classes de configurações e seus
 * recursos.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenteDeConfiguracoesDeDependencias {

	/** O objeto de configuração. */
	private ConfiguracaoIoT configuracao;

	/** Objeto responsável por gerenciar as classes do projeto. */
	private GerenteDeClasses gerenteClasses;

	/**
	 * Construtor.
	 *
	 * @param gerenteClasses Objeto responsável por gerenciar as classes do projeto.
	 */
	public GerenteDeConfiguracoesDeDependencias(GerenteDeClasses gerenteClasses) {
		this.gerenteClasses = gerenteClasses;
		this.configuracao = new ConfiguracaoIoT();
	}

	/**
	 * Unir todos os passos que são necesários para obter o mapa de configuração
	 * para ser utilizado pelo container ioc.
	 * 
	 * @return Objeto {@linkplain ConfiguracaoIoT} pronto para uso.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public ConfiguracaoIoT configurar() throws PacoteInexistenteException {
		Map<Class<?>, Method> metodosConfiguracao = this.getMetodosConfiguracao();
		this.invocarMetodos(metodosConfiguracao);
		return this.configuracao;
	}

	/**
	 * Método responsável por obter todas as classes do sistema anotadas
	 * com @ConfiguracaoDependencia.
	 * 
	 * Utilizando o objeto {@linkplain GerenteDeClasses}, é possível obter as
	 * classes anotadas.
	 *
	 * @return Uma lista de objetos {@linkplain Class} que representam todas as
	 *         classes correspondentes.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 * 
	 */
	List<Class<?>> obterClassesDeConfiguracao() throws PacoteInexistenteException {
		List<Class<?>> classesPelaAnotacao = this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class);
		if (classesPelaAnotacao == null) {
			return new ArrayList<Class<?>>();
		}
		return classesPelaAnotacao;
	}

	/**
	 * Classe responsável por verificar se as classes anotadas
	 * com @ConfiguracaoDependencia. são filhas da super classe abstrata
	 * {@linkplain ConfiguracaoDependenciaIoC}.
	 *
	 * @param classe A classe a ser verificada.
	 * @throws HerancaConfiguracaoNaoIdentificadaException Ocorrerá quando a classe
	 *                                                     não for filha da classe
	 *                                                     {@linkplain ConfiguracaoDependenciaIoC}.
	 */
	void verificarSeExtendeConfiguracaoDependenciaIoC(Class<?> classe)
			throws HerancaConfiguracaoNaoIdentificadaException {
		try {
			classe.asSubclass(ConfiguracaoDependenciaIoC.class);
		} catch (Exception e) {
			throw new HerancaConfiguracaoNaoIdentificadaException("A classe " + classe.getName()
					+ " não herda da classe "
					+ "ConfiguracaoDependenciaIoC. Extenda a classe indicada e implemente o método de configuração.");
		}
	}

	/**
	 * Método responsável por extrair o método de configuração de todas às classes
	 * de configuração de dependência.
	 * 
	 * Seu funcionamento consiste em iterar por todas as classes de configuração,
	 * obtendo seu método implementado da super classe 'configuracao'. Adicionando
	 * num HashMap, onde a chave é a classe e o valor o método.
	 *
	 * @return Um {@linkplain Map} de chaves {@linkplain Class} e valores
	 *         {@linkplain Method} representando o método de configuração de cada
	 *         classe.
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote do projeto não
	 *                                    exista no sistema operaocial.
	 */
	Map<Class<?>, Method> getMetodosConfiguracao() throws PacoteInexistenteException {
		List<Class<?>> classes = this.obterClassesDeConfiguracao();
		Map<Class<?>, Method> classesMetodos = new HashMap<Class<?>, Method>();
		classes.forEach(c -> {
			try {
				this.verificarSeExtendeConfiguracaoDependenciaIoC(c);
				classesMetodos.put(c, c.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class));
			} catch (HerancaConfiguracaoNaoIdentificadaException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

		});
		return classesMetodos;
	}

	/**
	 * Método responsável por invocar os métodos de configuração, assim atribuíndo o
	 * resultado ao atributo {@code configuracao}.
	 * 
	 * Seu funcionamento consiste em iterar por todos os métodos inseridos como
	 * parâmetro. Onde lhe são injetados um objeto
	 * {@linkplain InterfaceConfiguracaoIoCBuilder}. O usuário deverá usar a
	 * referência desse objeto para setar suas configurações. Após invocar o método,
	 * o resultado será setado no atributo {@code configuracao}.
	 *
	 * @param classesMetodos Um {@linkplain Map} de chaves {@linkplain Class} e
	 *                       valores {@linkplain Method} representando o método de
	 *                       configuração de cada classe.
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote do projeto não
	 *                                    exista no sistema operaocial.
	 */
	void invocarMetodos(Map<Class<?>, Method> classesMetodos) throws PacoteInexistenteException {
		Set<Class<?>> classes = classesMetodos.keySet();
		classes.forEach(c -> {
			Method m = classesMetodos.get(c);
			ConfiguracaoIoTBuilder builder = new ConfiguracaoIoTBuilder();
			InterfaceConfiguracaoIoCBuilder ic = new InterfaceConfiguracaoIoCBuilder(builder);
			try {
				m.invoke(c.getDeclaredConstructor().newInstance(), ic);
				this.configuracao.adicionarConfiguracao(builder);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
	}

}
