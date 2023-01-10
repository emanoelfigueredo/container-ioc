package br.com.efigueredo.container.construtor;

import java.lang.reflect.Constructor;
import java.util.List;

import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;

/**
 * <h4>Classe responsável por disponibilizar verificações para
 * construtores.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificadorConstrutores {

	/**
	 * Verificar se existe mais de um construtor anotado com @Injecao.
	 *
	 * @param construtoresAnotados Lista de construtores anotados.
	 * @param classe               A classe dos construtores.
	 * @throws InversaoDeControleInvalidaException Ocorrerá se houver mais de um
	 *                                             construtor anotado, ou se não
	 *                                             houver construtores anotados e
	 *                                             nem o padrão.
	 */
	public void verificarSeExisteMaisDeUmConstrutorAnotado(List<Constructor<?>> construtoresAnotados, Class<?> classe)
			throws InversaoDeControleInvalidaException {
		if (construtoresAnotados != null && construtoresAnotados.size() > 1) {
			throw new InversaoDeControleInvalidaException(
					"Mais de um construtor anotados com @Injecao na classe [" + classe + "]."
							+ " Para utilizar dos recursos de inversão de controle e injeção de dependência utilize "
							+ "a anotação em somente um construtor na classe.");
		}
	}

}
