package br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaValorInterface;

import java.util.Collection;
import java.util.List;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoDependenciaIoC;
import br.com.efigueredo.container.configuracao.InterfaceConfiguracaoIoCBuilder;
import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;

@ConfiguracaoDependencia
public class Configuracao4 extends ConfiguracaoDependenciaIoC {

	@Override
	public void configuracao(InterfaceConfiguracaoIoCBuilder icb) throws ConfiguracaoDependenciaInvalidaException {
		icb.adicionarConfiguracao(Collection.class, List.class);
	}

}
