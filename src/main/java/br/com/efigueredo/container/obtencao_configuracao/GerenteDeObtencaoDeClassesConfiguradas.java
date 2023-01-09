package br.com.efigueredo.container.obtencao_configuracao;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;

/**
 * <h4>Classe responsável por gerenciar os recursos para a obteção de uma
 * configuração de classe.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenteDeObtencaoDeClassesConfiguradas {

	/** Objeto responsável por obter a classe configurada. */
	private ObtentorClassePorConfiguracao obtentor;

	/**
	 * Construtor.
	 *
	 * @param configuracao Objeto de configuração de dependências.
	 */
	public GerenteDeObtencaoDeClassesConfiguradas(ConfiguracaoIoC configuracao) {
		this.obtentor = new ObtentorClassePorConfiguracao(configuracao);
	}

	/**
	 * Obtenha a classe configurada pela classe inserida.
	 *
	 * @param classe Classe chave.
	 * @return Classe valor configurada.
	 * @throws ClasseIlegalParaIntanciaException Ocorrerá se a classe inserida não
	 *                                           estiver configurada.
	 */
	public Class<?> getClasseConfigurada(Class<?> classe) throws ClasseIlegalParaIntanciaException {
		if (classe.isInterface()) {
			return this.obtentor.obterClasseConfigurada(classe);
		}
		return classe;
	}

}
