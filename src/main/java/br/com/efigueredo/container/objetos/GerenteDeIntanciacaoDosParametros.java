package br.com.efigueredo.container.objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

/**
 * <h4>Classe responsável por gerenciar a instânciação dos parâmetros de um
 * construtor.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenteDeIntanciacaoDosParametros {

	/** Objeto responsável por obter intâncias pela classe */
	private ContainerIoc container;

	/**
	 * Construtor.
	 *
	 * @param container Objeto responsável por obter intâncias pela classe.
	 */
	public GerenteDeIntanciacaoDosParametros(ContainerIoc container) {
		this.container = container;
	}

	/**
	 * Obtenha um array de instâncias do array de classes inseridos.
	 *
	 * @param classesParametros Array de objetos {@linkplain Class}.
	 * @return Array de intâncias das classes inseridas.
	 * @throws InversaoDeControleInvalidaException Lançado quando há alguma situação
	 *                                             em que não seja possível realizar
	 *                                             a intanciação do objeto.
	 * @throws ClasseIlegalParaIntanciaException   Lançado quando é requerido uma
	 *                                             intância de interface.
	 */
	public Object[] getIntanciaDosParametros(Class<?>[] classesParametros)
			throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		List<Object> objetosParametros = new ArrayList<Object>();
		List<Class<?>> listaClasses = Arrays.asList(classesParametros);
		for (Class<?> classe : listaClasses) {
			Object intancia = this.container.getIntancia(classe);
			objetosParametros.add(intancia);
		}
		return objetosParametros.toArray();
	}

}
