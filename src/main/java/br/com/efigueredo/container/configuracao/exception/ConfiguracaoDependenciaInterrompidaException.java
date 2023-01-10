package br.com.efigueredo.container.configuracao.exception;

/**
 * <h4>Exceção para a situação onde a configuração de dependências foi
 * interrompida.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoDependenciaInterrompidaException extends ConfiguracaoDependenciaException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param excecao Exceção responsável pela interrupção.
	 */
	public ConfiguracaoDependenciaInterrompidaException(Exception excecao) {
		super(excecao);
	}

}
