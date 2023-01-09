package br.com.efigueredo.container.exception;

public class ExcecaoVerificacao extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ExcecaoVerificacao(String mensagem) {
		super(mensagem);
	}

	
}
