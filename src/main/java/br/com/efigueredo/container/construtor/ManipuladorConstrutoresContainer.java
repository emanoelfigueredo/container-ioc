package br.com.efigueredo.container.construtor;

import java.lang.reflect.Constructor;
import java.util.List;

import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

/**
 * <h4>Classe responsável por manipular os construtores dos objetos que devem
 * ser instânciados.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorConstrutoresContainer {

	/** Objeto responsável por verificar os construtores. */
	private VerificadorConstrutores verificador;
	
	/** Objeto responsável por obter os construtores */
	private ObtentorConstrutores obtentor;

	/**
	 * Construtor.
	 */
	public ManipuladorConstrutoresContainer() {
		this.verificador = new VerificadorConstrutores();
		this.obtentor = new ObtentorConstrutores();
	}

	/**
	 * Método responsável por entregar o construtor adequado de acordo com o a
	 * classe do objeto requisitado.
	 * 
	 * Seu funcionamento consiste em integrar todos os métodos necessários para a
	 * obtenção do construtor adequado.
	 *
	 * @param classe A classe
	 * @return O objeto {@linkplain Constructor} adequado.
	 * @throws InversaoDeControleInvalidaException Ocorrerá se houver mais de um
	 *                                             construtor anotado, ou se não
	 *                                             houver construtores anotados e
	 *                                             nem o padrão.
	 */
	public Constructor<?> getConstrutorAdequado(Class<?> classe) throws InversaoDeControleInvalidaException {
		List<Constructor> construtoresAnotados = this.obtentor.obterTodosOsConstrutoresAnotadosComInjecao(classe);
		this.verificador.verificarSeExisteMaisDeUmConstrutorAnotado(construtoresAnotados, classe);
		return this.obterConstrutorAdequado(construtoresAnotados, classe);
	}

	/**
	 * Método privado auxiliar responsável por obter o construtor adequado de acordo
	 * com os construtores obtidos da classe.
	 * 
	 * Caso a lista de construtores anotados vala null, então será executado o
	 * processo de obtenção do construtor padrão. Caso exista consutrores anotados,
	 * seja delegado ao método responsável a sua extração.
	 *
	 * @param construtoresAnotados Lista de construtores anotados com @Injecao.
	 * @param classe               A classe dos construtores obtidos.
	 * @return O construtor adequando.
	 * @throws InversaoDeControleInvalidaException Ocorerá se não houver construtor
	 *                                             padrão na classe.
	 */
	private Constructor<?> obterConstrutorAdequado(List<Constructor> construtoresAnotados, Class<?> classe)
			throws InversaoDeControleInvalidaException {
		if (construtoresAnotados == null) {
			return this.obtentor.getConstrutorPadrao(classe);
		}
		return this.getConstrutorAnotado(construtoresAnotados);
	}

	/**
	 * Método privado auxiliar responsável por extrair da lista de construtores o único
	 * construtor possível.
	 *
	 * @param construtoresAnotados Lista de construtores anotados com @Injecao.
	 * @return O construtor adequando.
	 */
	private Constructor<?> getConstrutorAnotado(List<Constructor> construtoresAnotados) {
		return construtoresAnotados.get(0);
	}

}
