package br.com.efigueredo.container.objetos.verificacao.exception;

/**
 * <h4>Exceções a situação onde a classe requerida tem dependências que em algum
 * momento apontam novamente para uma das classes que depende. Assim gerando um
 * loop.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class LoopDeInjecaoDependenciaException extends ExcecaoVerificacao {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 */
	public LoopDeInjecaoDependenciaException(String mensagem) {
		super(mensagem);
	}

}
