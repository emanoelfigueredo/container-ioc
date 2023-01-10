package br.com.efigueredo.container.construtor;

import java.lang.reflect.Constructor;
import java.util.List;

import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;

/**
 * <h4>Classe responsável por obter os construroes</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ObtentorConstrutores {

	/**
	 * Obtenha todos os construtores anotados com @Injecao de uma classe.
	 *
	 * @param classe A classe.
	 * @return Lista de construtores anotados com @Injecao.
	 */
	public List<Constructor<?>> obterTodosOsConstrutoresAnotadosComInjecao(Class<?> classe) {
		return new ManipuladorConstrutores(classe).buscarConstrutoresPorAnotacao(Injecao.class);
	}

	/**
	 * Obtenha o construtor padrão de uma classe.
	 * 
	 * Caso ele não exista, uma exceção será lançada.
	 *
	 * @param classe the classe
	 * @return Objeto {@linkplain Constructor} representando o construtor padrão.
	 * @throws InversaoDeControleInvalidaException Ocorerá se não houver construtor
	 *                                             padrão na classe.
	 */
	public Constructor<?> getConstrutorPadrao(Class<?> classe) throws InversaoDeControleInvalidaException {
		try {
			return classe.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new InversaoDeControleInvalidaException(
					"Não há construtor padrão nem anotados com @Injecao na " + "classe [" + classe.getName() + "].");
		}
	}

}
