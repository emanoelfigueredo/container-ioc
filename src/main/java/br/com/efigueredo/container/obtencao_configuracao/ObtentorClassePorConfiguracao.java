package br.com.efigueredo.container.obtencao_configuracao;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;

/**
 * <h4>Classe responsável por obter das configurações a classe configurada.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ObtentorClassePorConfiguracao {

	/** Objeto que representa uma configuração de dependências. */
	private ConfiguracaoIoC configuracao;

	/**
	 * Construtor.
	 *
	 * @param configuracao Objeto que representa uma configuração de dependências.
	 */
	public ObtentorClassePorConfiguracao(ConfiguracaoIoC configuracao) {
		this.configuracao = configuracao;
	}

	/**
	 * Obtenha a classe configurada.
	 *
	 * @param classe Classe chave.
	 * @return Classe valor configurada.
	 * @throws ClasseIlegalParaIntanciaException Ocorrerá se a classe inserida não
	 *                                           estiver configurada.
	 */
	public Class<?> obterClasseConfigurada(Class<?> classe) throws ClasseIlegalParaIntanciaException {
		if (!this.configuracao.configuracaoExiste(classe)) {
			throw new ClasseIlegalParaIntanciaException("O objeto Class " + classe.getName()
					+ " representa uma interface."
					+ " Portanto não pode ser instânciada. Crie uma configuração de depêndencia para poder utilizar interface"
					+ " nos construtores de suas classes onde se deve injetar depêndencias.");
		}
		return configuracao.getConfiguracao(classe);
	}

}
