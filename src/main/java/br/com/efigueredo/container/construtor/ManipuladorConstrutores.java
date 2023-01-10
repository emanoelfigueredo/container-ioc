package br.com.efigueredo.container.construtor;



import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h4>Classe responsável por manipular construtores de uma classe.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorConstrutores {

	/**
	 * Lista de objetos {@linkplain Constructor} representando os construtores
	 * privados, publicos e herdados.
	 */
	private List<Constructor<?>> construtores;

	/**
	 * Construtor.
	 *
	 * @param classe Classe a ter seus construtores manipulados.
	 */
	public ManipuladorConstrutores(Class<?> classe) {
		this.construtores = new ArrayList<Constructor<?>>();
		this.setupConstutores(Arrays.asList(classe.getDeclaredConstructors()));
		this.setupConstutores(Arrays.asList(classe.getConstructors()));
	}

	/**
	 * Método privado auxiliar por verificar os construtores existem no parametro
	 * {@code construtores}.
	 * 
	 * Caso não exista, será adicionado.
	 *
	 * @param listaConstrutores Lista de objetos {@linkplain Constructor}.
	 */
	private void setupConstutores(List<Constructor<?>> listaConstrutores) {
		listaConstrutores.forEach(c -> {
			if (!this.construtores.contains(c)) {
				this.construtores.add(c);
			}
		});
	}

	/**
	 * Método responsável por buscar em todos os construtores da classe os que
	 * possuem uma determinada anotação.
	 * 
	 * Seu funcionamento consiste em iterar por todos os constutores e extrair os
	 * que possuam a anotação inserida por parâmetro para uma {@linkplain List}.
	 *
	 * @param classeAnotacao A classe da anotação.
	 * @return Caso haja construtores correspondentes, uma lista de objetos
	 *         {@linkplain Constructor}. <br>
	 *         Caso não, null.
	 */
	public List<Constructor<?>> buscarConstrutoresPorAnotacao(Class<? extends Annotation> classeAnotacao) {
		List<Constructor<?>> construtores = this.construtores.stream()
				.filter(c -> c.isAnnotationPresent(classeAnotacao)).toList();
		return this.verificaResultadoLista(construtores);
	}
	
	/**
	 * Método auxiliar responsável por verificar a lista de resultados de um recurso
	 * manipulado.
	 *
	 * @param <T>       A classe do recurso.
	 * @param resultado Uma lista de resultados da manipulação.
	 * @return Se a lista de resultados não for vazia, retornará a mesma.<br>
	 *         Caso seja vazia, retornará null.
	 */
	private <T> List<T> verificaResultadoLista(List<T> resultado) {
		if (resultado.isEmpty()) {
			return null;
		}
		return resultado;
	}

}
