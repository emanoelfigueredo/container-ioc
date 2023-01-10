package br.com.efigueredo.container.configuracao;

import java.util.List;

import org.reflections.Reflections;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;

/**
 * <h4>Classe responsável por manipular todas as classes de configuração de
 * dependências do projeto.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorClassesConfiguracaoDependencias {

	/** Objeto responsável pela reflexão de todo o projeto. */
	private Reflections reflections;

	/**
	 * Construtor.
	 *
	 * @param reflections Objeto responsável pela reflexão de todo o projeto.
	 */
	public ManipuladorClassesConfiguracaoDependencias(Reflections reflections) {
		this.reflections = reflections;
	}

	/**
	 * Obtenha todas as classes do projeto anotadas com
	 * {@linkplain @ConfiguracaoDependencia}.
	 *
	 * @return Uma lista de objetos {@linkplain Class} que representam todas as
	 *         classes correspondentes.
	 */
	List<Class<?>> obterClassesAnotadasComConfiguracaoDependencia() {
		return this.reflections.getTypesAnnotatedWith(ConfiguracaoDependencia.class).stream().toList();
	}

}
