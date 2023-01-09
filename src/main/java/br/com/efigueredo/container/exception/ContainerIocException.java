package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção que representa todas as exceções da biblioteca.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ContainerIocException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor com mensagem.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ContainerIocException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Construtor com causa e mensagem.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 * @param causa    Causa da exceção.
	 */
	public ContainerIocException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	/**
	 * Construtor com causa.
	 *
	 * @param causa the causa
	 */
	public ContainerIocException(Throwable causa) {
		super(causa);
	}

}
