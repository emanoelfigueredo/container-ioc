package br.com.efigueredo.container.exception;

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
	 * @causa causa Causa da exceção.
	 */
	public ContainerIocException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	/**
	 * Construtor com causa.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ContainerIocException(Throwable causa) {
		super(causa);
	}

}
