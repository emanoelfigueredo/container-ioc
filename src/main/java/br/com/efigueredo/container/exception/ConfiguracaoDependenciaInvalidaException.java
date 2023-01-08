package br.com.efigueredo.container.exception;

/**
 * Exceção para situações onde a classe valor não é subclasse ou implementação
 * da classe chave.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoDependenciaInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Mensagem que descreva a situação com mais detalhes.
	 */
	public ConfiguracaoDependenciaInvalidaException(String mensagem) {
		super(mensagem);
	}

}
