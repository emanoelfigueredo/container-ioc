package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para a situação onde a configuração de dependências foi
 * interrompida.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoDependenciaInterrompidaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public ConfiguracaoDependenciaInterrompidaException(Throwable causa) {
		super(causa);
	}

}
