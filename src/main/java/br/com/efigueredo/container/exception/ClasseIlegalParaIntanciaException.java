package br.com.efigueredo.container.exception;

/**
 * Exceção para a situação onde é requerido uma instância de uma interface. Na
 * qual não está configurada como chave de uma classe concreta.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ClasseIlegalParaIntanciaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Mensagem que descreva a situação com mais detalhes.
	 */
	public ClasseIlegalParaIntanciaException(String mensagem) {
		super(mensagem);
	}

}
