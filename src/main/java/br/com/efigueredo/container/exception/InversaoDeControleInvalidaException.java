package br.com.efigueredo.container.exception;

/**
 * Exceção para a situação onde o processo de inversão de controle não pode ser
 * utilizado. Pode ocorrer em<br>
 * <ul>
 * <li>Classe requerida não tem construtor padrão e construtor anotado com
 * {@linkplain Injecao}</li>
 * <li>Classe requerida tem dois construtores anotados com
 * {@linkplain Injecao}</li>
 * </ul>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InversaoDeControleInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Mensagem que descreva a situação com mais detalhes.
	 */
	public InversaoDeControleInvalidaException(String mensagem) {
		super(mensagem);
	}

}
