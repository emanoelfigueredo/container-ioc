package br.com.efigueredo.container.construtor;

import java.lang.reflect.Constructor;
import java.util.List;

import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.recursos.java.ManipuladorConstrutores;

/**
 * Classe responsável por manipular os construtores dos objetos que devem ser
 * instânciados.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorConstrutoresContainer {

	/**
	 * Método responsável por entregar o construtor adequado de acordo com o a
	 * classe do objeto requisitado.
	 * 
	 * Seu funcionamento consiste em obter todos os métodos anotados com @Injecao.
	 * Se o resultado for null, será retornado o construtor padrão. Caso ele não
	 * exista, será lançado uma exceção. Se houver mais de um construtor anotado,
	 * será lançado uma exceção. Por fim, se houver apenas um construtor, ele será o
	 * resultado.
	 *
	 * @return O objeto {@linkplain Constructor} adequado.
	 * @throws InversaoDeControleInvalidaException Ocorrerá se houver mais de um
	 *                                             construtor anotado, ou se não
	 *                                             houver construtores anotados e
	 *                                             nem o padrão.
	 */
	public Constructor<?> getConstrutorAdequado(Class<?> classe) throws InversaoDeControleInvalidaException {
		List<Constructor<?>> construtoresAnotados = this.obterTodosOsConstrutoresAnotadosComInjecao(classe);
		this.verificarSeExisteMaisDeUmConstrutorAnotado(construtoresAnotados, classe);
		return this.obterConstrutorAdequado(construtoresAnotados, classe);
	}

	private Constructor<?> obterConstrutorAdequado(List<Constructor<?>> construtoresAnotados, Class<?> classe)
			throws InversaoDeControleInvalidaException {
		if (construtoresAnotados == null) {
			return this.getConstrutorPadrao(classe);
		}
		return this.getConstrutorAnotado(construtoresAnotados);
	}

	private List<Constructor<?>> obterTodosOsConstrutoresAnotadosComInjecao(Class<?> classe) {
		ManipuladorConstrutores manipuladorConstrutoresProjeto = new ManipuladorConstrutores(classe);
		return manipuladorConstrutoresProjeto.buscarConstrutoresPorAnotacao(Injecao.class);
	}

	private Constructor<?> getConstrutorAnotado(List<Constructor<?>> construtoresAnotados) {
		return construtoresAnotados.get(0);
	}

	private void verificarSeExisteMaisDeUmConstrutorAnotado(List<Constructor<?>> construtoresAnotados, Class<?> classe)
			throws InversaoDeControleInvalidaException {
		if (construtoresAnotados != null && construtoresAnotados.size() > 1) {
			throw new InversaoDeControleInvalidaException(
					"Mais de um construtor anotados com @Injecao na classe [" + classe + "]."
							+ " Para utilizar dos recursos de inversão de controle e injeção de dependência utilize "
							+ "a anotação em somente um construtor na classe.");
		}
	}

	/**
	 * Método privado auxiliar responsável por obter o construtor padrão da classe.
	 * 
	 * Caso ele não exista, uma exceção será lançada.
	 *
	 * @return Objeto {@linkplain Constructor} representando o construtor padrão.
	 * @throws InversaoDeControleInvalidaException Ocorerá se não houver construtor
	 *                                             padrão na classe.
	 */
	private Constructor<?> getConstrutorPadrao(Class<?> classe) throws InversaoDeControleInvalidaException {
		try {
			return classe.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new InversaoDeControleInvalidaException(
					"Não há construtor padrão nem anotados com @Injecao na " + "classe [" + classe.getName() + "].");
		}
	}

}
