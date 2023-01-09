package br.com.efigueredo.container.exception;

public class ContainerIocException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ContainerIocException(String mensagem) {
		super(mensagem);
	}

}
