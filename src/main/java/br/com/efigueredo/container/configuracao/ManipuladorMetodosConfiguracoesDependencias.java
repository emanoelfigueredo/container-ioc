package br.com.efigueredo.container.configuracao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

public class ManipuladorMetodosConfiguracoesDependencias {
	
	private ConfiguracaoIoC configuracao;
	
	public ManipuladorMetodosConfiguracoesDependencias(ConfiguracaoIoC configuracao) {
		this.configuracao = configuracao;
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
	 */
	Map<Class<?>, Method> getMetodosConfiguracao(List<Class<?>> classes) {
		Map<Class<?>, Method> classesMetodos = new HashMap<Class<?>, Method>();
		classes.forEach(c -> {
			try {
				classesMetodos.put(c, c.getMethod("configuracao", InterfaceConfiguracaoIoCBuilder.class));
			} catch (NoSuchMethodException | SecurityException e) {
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
			ConfiguracaoIoCBuilder builder = new ConfiguracaoIoCBuilder();
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
