package br.com.efigueredo.container.exception;

/**
 * Exceção para a situação onde a classe anotada com @ConfiguracaoDependencia
 * não extende a super classe abstrata {@linkplain ConfiguracaoDependenciaIoC}.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class HerancaConfiguracaoNaoIdentificadaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Mensagem que descreva a situação com mais detalhes.
	 */
	public HerancaConfiguracaoNaoIdentificadaException(String mensagem) {
		super(mensagem);
	}

}
