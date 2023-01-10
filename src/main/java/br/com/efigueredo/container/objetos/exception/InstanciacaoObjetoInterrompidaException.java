package br.com.efigueredo.container.objetos.exception;

import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Exceção para a situação onde a instânciação de um construtor foi
 * interrompida</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InstanciacaoObjetoInterrompidaException extends ContainerIocException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 *
	 * @param mensagem Texto que descreva a situação com mais detalhes.
	 * @param causa    Causa da exceção.
	 */
	public InstanciacaoObjetoInterrompidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
