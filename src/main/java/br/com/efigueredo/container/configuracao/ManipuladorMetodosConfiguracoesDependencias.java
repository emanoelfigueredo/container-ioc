package br.com.efigueredo.container.configuracao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInterrompidaException;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por manipular os métodos de configuração das classes
 * de configuração de dependências.</h4>
 */
public class ManipuladorMetodosConfiguracoesDependencias {

	/**
	 * Método responsável por extrair o método de configuração de todas às classes
	 * de configuração de dependência.
	 * 
	 * Seu funcionamento consiste em iterar por todas as classes de configuração,
	 * obtendo seu método implementado da super classe 'configuracao'. Adicionando
	 * num HashMap, onde a chave é a classe e o valor o método.
	 *
	 * @param classes Lista de classes de configuração.
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
	 * Seu funcionamento consiste em iterar por todas as classes de configuração.
	 * Invocando seu método de configuração, obtendo um builder configurado.
	 *
	 * @param classesMetodos Um {@linkplain Map} de chaves {@linkplain Class} e
	 *                       valores {@linkplain Method} representando o método de
	 *                       configuração de cada classe.
	 * @return Lista de objetos {@linkplain ConfiguracaoIoCBuilder} configurados.
	 * @throws PacoteInexistenteException                   Ocorrerá caso o pacote
	 *                                                      do projeto não exista no
	 *                                                      sistema operaocial.
	 * @throws ConfiguracaoDependenciaInvalidaException     Ocorrerá caso a
	 *                                                      configuração seja
	 *                                                      inválida. Podendo ser a
	 *                                                      classe valor não sendo
	 *                                                      uma interface ou filha
	 *                                                      da classe chave. Ou a
	 *                                                      classe valor sendo uma
	 *                                                      inteface.
	 * @throws ConfiguracaoDependenciaInterrompidaException Ocorrerá se houver algum
	 *                                                      erro de reflexão na
	 *                                                      configuração.
	 */
	List<ConfiguracaoIoCBuilder> invocarMetodos(Map<Class<?>, Method> classesMetodos) throws PacoteInexistenteException,
			ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
		Set<Class<?>> classes = classesMetodos.keySet();
		List<ConfiguracaoIoCBuilder> buildersConfigurados = new ArrayList<ConfiguracaoIoCBuilder>();
		for (Class<?> classe : classes) {
			ConfiguracaoIoCBuilder builderConfigurado = this.invocarMetodoConfiguracao(classesMetodos, classe);
			buildersConfigurados.add(builderConfigurado);
		}
		return buildersConfigurados;
	}

	/**
	 * Método privado auxiliar responsável por invocar o método pela sua classe de
	 * configuração.
	 * 
	 * Seu funcionamento consite em obter o método pela classe de configuração.
	 * Injetar o builder e invocar o método. Assim retornando o builder injetado
	 * configurado.
	 *
	 * @param classesMetodos Mapa de classes e métodos de configuração.
	 * @param classe         Classe que terá seu método invocado.
	 * @return Builder configurado.
	 * @throws ConfiguracaoDependenciaInterrompidaException Ocorrerá se houver algum
	 *                                                      erro de reflexão na
	 *                                                      configuração.
	 */
	private ConfiguracaoIoCBuilder invocarMetodoConfiguracao(Map<Class<?>, Method> classesMetodos, Class<?> classe)
			throws ConfiguracaoDependenciaInterrompidaException {
		try {
			Method m = classesMetodos.get(classe);
			ConfiguracaoIoCBuilder builder = new ConfiguracaoIoCBuilder();
			InterfaceConfiguracaoIoCBuilder ic = new InterfaceConfiguracaoIoCBuilder(builder);
			m.invoke(classe.getDeclaredConstructor().newInstance(), ic);
			return builder;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException
				| NoSuchMethodException | SecurityException e) {
			throw new ConfiguracaoDependenciaInterrompidaException(e.getCause());
		}
	}

}
