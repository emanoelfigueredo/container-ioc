package br.com.efigueredo.container.objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.objetos.exception.InstanciacaoObjetoInterrompidaException;

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
	 * @throws ClasseIlegalParaIntanciaException      Lançado quando é requerido uma
	 *                                                intância de interface.
	 * @throws ContainerIocException
	 * @throws IntanciacaoObjetoInterrompidaException Ocorrerá se a instânciação for
	 *                                                interrompida. Analise a causa
	 *                                                na stack trace.
	 */
	public Object[] getIntanciaDosParametros(Class<?>[] classesParametros)
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		List<Object> objetosParametros = new ArrayList<Object>();
		List<Class<?>> listaClasses = Arrays.asList(classesParametros);
		for (Class<?> classe : listaClasses) {
			Object intancia = this.container.getInstancia(classe);
			objetosParametros.add(intancia);
		}
		return objetosParametros.toArray();
	}

}
