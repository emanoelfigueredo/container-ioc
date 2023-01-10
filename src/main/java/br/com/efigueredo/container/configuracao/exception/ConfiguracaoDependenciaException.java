package br.com.efigueredo.container.configuracao.exception;

import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Exceção para a tarefa de configuração de dependências.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoDependenciaException extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 * @param excecao  A exceção que causou o erro.
	 */
	public ConfiguracaoDependenciaException(String mensagem, Exception excecao) {
		super(mensagem, excecao);
	}

	/**
	 * Construtor
	 *
	 * @param excecao A exceção que causou o erro.
	 */
	public ConfiguracaoDependenciaException(Exception excecao) {
		super(excecao);
	}

	/**
	 * Construtor
	 *
	 * @param mensagem Texto que descreva a exceção com mais detalhes.
	 */
	public ConfiguracaoDependenciaException(String mensagem) {
		super(mensagem);
	}

}
