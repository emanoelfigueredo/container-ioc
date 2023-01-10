package br.com.efigueredo.container.configuracao;

import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;

/**
 * </h4>A classe {@code ConfiguracaoDependenciaIoC} é reponsável por especificar
 * as classes de configurações das dependências do container IoC o método de
 * configuração obrigatório.</h4>
 */
public abstract class ConfiguracaoDependenciaIoC {

	/**
	 * Método responsável por disponibilizar a interface de configuração. Na qual é
	 * possível configurar as depêndencias.
	 *
	 * @param icb - Objeto do tipo {@linkplain InterfaceConfiguracaoIoCBuilder}
	 *            responsável por manipular o builder da classe de configuração
	 *            {@link ConfiguracaoIoT}.
	 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá quando a classe
	 *                                                  valor não for uma
	 *                                                  implementação ou classe
	 *                                                  filha da classe chave.
	 */
	public abstract void configuracao(InterfaceConfiguracaoIoCBuilder icb)
			throws ConfiguracaoDependenciaInvalidaException;

}
