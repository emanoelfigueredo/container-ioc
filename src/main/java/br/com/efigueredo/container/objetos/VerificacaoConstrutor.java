package br.com.efigueredo.container.objetos;

import java.lang.reflect.Constructor;

/**
 * <h4>Interface que especifica uma verificação para os construtores manipulados.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public interface VerificacaoConstrutor {

	/**
	 * Especificação do método que deve conter toda a lógica de verificação. Deve
	 * ser utilizada pelo design pattern Observer para executar todas as
	 * verificações.
	 *
	 * @param construtor Objeto {@linkplain Constructor} para ser verificado.
	 */
	void verificar(Constructor<?> construtor);

}
