package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para a situação onde é requerido uma instância de uma interface. Na
 * qual não está configurada como chave de uma classe concreta.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ClasseIlegalParaIntanciaException extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ClasseIlegalParaIntanciaException(String mensagem) {
		super(mensagem);
	}

}
