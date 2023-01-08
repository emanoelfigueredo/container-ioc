package br.com.efigueredo.container.objetos;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

/**
 * <h4>Classe responsável por instânciar os objetos requeridos pelo
 * {@linkplain ContainerIoc}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InstanciadorDeObjetos {

	/** Objeto {@linkplain ContainerIoc} */
	private ContainerIoc container;

	/**
	 * Construtor.
	 *
	 * @param container Objeto {@linkplain ContainerIoc} que está instânciando um
	 *                  objeto utilizando um objeto dessa classe.
	 */
	public InstanciadorDeObjetos(ContainerIoc container) {
		this.container = container;
	}

	/**
	 * Obtenha uma instância de um objeto pelo seu construtor padrão.
	 *
	 * @param construtorPadrao Objeto {@linkplain Constructor} para que seja
	 *                         utilizado na instanciação.
	 * @return Uma intância do objeto pelo seu construtor.
	 */
	public Object intanciarPorContrutorPadrao(Constructor<?> construtorPadrao) {
		construtorPadrao.setAccessible(true);
		try {
			return construtorPadrao.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Obtenha uma instância de um objeto pelo seu construtor que recebe parâmetros.
	 * 
	 * A intânciação do objeto ocorre ao usar seu construtor e adicionar um
	 * {@linkplain Array} de parâmetros.
	 *
	 * @param construtor          O construtor com parâmetros do objeto
	 * @param instanciaParametros Um {@linkplain Array} contendo uma intância de
	 *                            todos os seus parâmetros.
	 * @return O objeto instânciado.
	 */
	public Object instanciarPorContrutorComParametros(Constructor<?> construtor, Object[] instanciaParametros) {
		construtor.setAccessible(true);
		try {
			return construtor.newInstance(instanciaParametros);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Obtenha um {@linkplain Array} de instâncias de objetos {@linkplain Class}
	 * contidos num Array, representando os parâmetros.
	 *
	 * @param classes {@linkplain Array} de objetos {@linkplain Class}.
	 * @return {@linkplain Array} de intâncias das classes inseridas.
	 */
	public Object[] getIntanciaDosParametros(Class<?>[] classes) {
		List<Class<?>> listaClasses = Arrays.asList(classes);
		List<Object> objetosParametros = new ArrayList<Object>();
		listaClasses.forEach(c -> {
			Object intancia;
			try {
				intancia = this.container.getIntancia(c);
				objetosParametros.add(intancia);
			} catch (IllegalArgumentException | InversaoDeControleInvalidaException
					| ClasseIlegalParaIntanciaException e) {
				e.printStackTrace();
			}
		});
		return objetosParametros.toArray();
	}

}
