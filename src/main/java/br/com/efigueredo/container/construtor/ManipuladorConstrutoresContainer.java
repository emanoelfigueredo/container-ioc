package br.com.efigueredo.container.construtor;

import java.lang.reflect.Constructor;
import java.util.List;

import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
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

	/** A classe que terá seus construtores manipulados. */
	private Class<?> classe;

	/** Objeto responsável por gerenciar todos os construtores de uma classe. */
	private ManipuladorConstrutores manipuladorConstrutoresProjeto;

	/**
	 * Construtor.
	 *
	 * @param classe A classe na qual terá seus construtores manipulados.
	 * @throws ClasseIlegalParaIntanciaExpcetion
	 */
	public ManipuladorConstrutoresContainer(Class<?> classe) throws ClasseIlegalParaIntanciaException {
		if (this.eInterface(classe)) {
			throw new ClasseIlegalParaIntanciaException("O objeto Class " + classe.getName()
					+ " representa uma interface."
					+ " Portanto não pode ser instânciada. Crie uma configuração de depêndencia para poder utilizar interface"
					+ " nos construtores de suas classes onde se deve injetar depêndencias.");
		}
		this.classe = classe;
		this.manipuladorConstrutoresProjeto = new ManipuladorConstrutores(classe);
	}

	private boolean eInterface(Class<?> classe) {
		return classe.isInterface();
	}

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
	public Constructor<?> getConstrutorAdequado() throws InversaoDeControleInvalidaException {
		List<Constructor<?>> construtores = this.manipuladorConstrutoresProjeto.buscarConstrutoresPorAnotacao(Injecao.class);
		if (construtores == null) {
			return this.getConstrutorPadrao();
		}
		if (construtores.size() > 1) {
			throw new InversaoDeControleInvalidaException(
					"Mais de um construtor anotados com @Injecao na classe [" + this.classe + "]."
							+ " Para utilizar dos recursos de inversão de controle e injeção de dependência utilize "
							+ "a anotação em somente um construtor na classe.");
		}
		return construtores.get(0);
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
	private Constructor<?> getConstrutorPadrao() throws InversaoDeControleInvalidaException {
		try {
			return this.classe.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new InversaoDeControleInvalidaException("Não há construtor padrão nem anotados com @Injecao na "
					+ "classe [" + this.classe.getName() + "].");
		}
	}

}
