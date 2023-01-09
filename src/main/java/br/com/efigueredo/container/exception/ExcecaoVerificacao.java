package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para verificações da biblioteca.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ExcecaoVerificacao extends ContainerIocException {

	/** The Constant serialVersionUID. */
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
