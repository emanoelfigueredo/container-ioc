package br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaImplementacao;

import java.util.List;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoDependenciaIoC;
import br.com.efigueredo.container.configuracao.InterfaceConfiguracaoIoCBuilder;
import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;

@ConfiguracaoDependencia
public class Configuracao3 extends ConfiguracaoDependenciaIoC {

	@Override
	public void configuracao(InterfaceConfiguracaoIoCBuilder icb) throws ConfiguracaoDependenciaInvalidaException {
		icb.adicionarConfiguracao(List.class, String.class);
	}

}
