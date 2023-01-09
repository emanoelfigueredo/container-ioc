package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para situações onde a classe valor não é subclasse ou implementação
 * da classe chave.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoDependenciaInvalidaException extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ConfiguracaoDependenciaInvalidaException(String mensagem) {
		super(mensagem);
	}

}
