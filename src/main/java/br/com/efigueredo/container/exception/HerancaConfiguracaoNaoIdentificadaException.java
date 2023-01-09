package br.com.efigueredo.container.exception;

/**
 * <h4>Exceção para a situação onde a classe anotada com @ConfiguracaoDependencia
 * não extende a super classe abstrata {@linkplain ConfiguracaoDependenciaIoC}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class HerancaConfiguracaoNaoIdentificadaException extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public HerancaConfiguracaoNaoIdentificadaException(String mensagem) {
		super(mensagem);
	}

}
