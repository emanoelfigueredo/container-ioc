package br.com.efigueredo.container.obtencao_configuracao;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;

public class ObtentorClassePorConfiguracao {
	
	private ConfiguracaoIoC configuracao;

	public ObtentorClassePorConfiguracao(ConfiguracaoIoC configuracao) {
		this.configuracao = configuracao;
	}

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
