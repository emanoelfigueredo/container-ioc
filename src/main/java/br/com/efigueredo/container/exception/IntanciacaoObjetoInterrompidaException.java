package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para a situação onde a instânciação de um construtor foi
 * interrompida</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class IntanciacaoObjetoInterrompidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 * @param causa    Causa da exceção.
	 */
	public IntanciacaoObjetoInterrompidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
