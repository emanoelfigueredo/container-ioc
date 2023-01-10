package br.com.efigueredo.container.configuracao;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;

/**
 * <h4>A classe {@code InterfaceConfiguracaoIoCBuilder} é responsável por
 * fornecer ao usuário a única funcionalidade do builder da classe
 * {@linkplain ConfiguracaoIoC} necessário para configurar as dependências.
 * <h4>
 * 
 * Seu funcionamento consiste em encapsular o builder da classe e fornecer
 * somente a possibilidade de adicionar configurações.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class InterfaceConfiguracaoIoCBuilder {

	/** Objeto do tipo {@linkplain ConfiguracaoIoCBuilder} que será manipulado. */
	private ConfiguracaoIoCBuilder builder;

	/**
	 * Construtor padrão.
	 * 
	 * Instância o builder e atribui aos atributos da classe.
	 */
	public InterfaceConfiguracaoIoCBuilder(ConfiguracaoIoCBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Método responsável por adicionar configuração ao builder.
	 * 
	 * Seu funcionamento consiste em receber objetos do tipo {@linkplain Class} que
	 * representem a chave e o valor das configurações.
	 *
	 * @param classeChave O objeto do tipo {@linkplain Class} que representa a chave
	 *                    para obter a classe desejada.
	 * @param classeValor O objeto do tipo {@linkplain Class} que representa a
	 *                    classe desejada.
	 * @return O próprio objeto InterfaceConfiguracaoIoCBuilder.
	 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá caso a configuração
	 *                                                  seja inválida. Podendo ser a
	 *                                                  classe valor não sendo uma
	 *                                                  interface ou filha da classe
	 *                                                  chave. Ou a classe valor
	 *                                                  sendo uma inteface.
	 */
	public InterfaceConfiguracaoIoCBuilder adicionarConfiguracao(Class<?> classeChave, Class<?> classeValor)
			throws ConfiguracaoDependenciaInvalidaException {
		this.builder.addConfiguracao(classeChave, classeValor);
		return this;
	}

}
