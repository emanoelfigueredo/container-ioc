package br.com.efigueredo.container.objetos;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.IntanciacaoObjetoInterrompidaException;

/**
 * <h4>Classe responsável por instânciar os objetos requeridos pelo
 * {@linkplain ContainerIoc}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InstanciadorDeObjetos {

	/**
	 * Obtenha uma instância de um objeto pelo seu construtor padrão.
	 *
	 * @param construtorPadrao Objeto {@linkplain Constructor} para que seja
	 *                         utilizado na instanciação.
	 * @return Uma intância do objeto pelo seu construtor.
	 * @throws IntanciacaoObjetoInterrompidaException Ocorrerá se a instânciação for
	 *                                                interrompida. Analise a causa
	 *                                                na stack trace.
	 */
	public Object intanciarPorContrutorPadrao(Constructor<?> construtorPadrao)
			throws IntanciacaoObjetoInterrompidaException {
		construtorPadrao.setAccessible(true);
		try {
			return construtorPadrao.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new IntanciacaoObjetoInterrompidaException(
					"A instânciação do construtor " + construtorPadrao.getName() + " foi interropida. Analise a causa.",
					e.getCause());
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
	 * @throws IntanciacaoObjetoInterrompidaException Ocorrerá se a instânciação for
	 *                                                interrompida. Analise a causa
	 *                                                na stack trace.
	 */
	public Object instanciarPorContrutorComParametros(Constructor<?> construtor, Object[] instanciaParametros)
			throws IntanciacaoObjetoInterrompidaException {
		try {
			construtor.setAccessible(true);
			return construtor.newInstance(instanciaParametros);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new IntanciacaoObjetoInterrompidaException(
					"A instânciação do construtor " + construtor.getName() + " foi interropida. Analise a causa.",
					e.getCause());
		}
	}

}
