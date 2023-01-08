package br.com.efigueredo.container.configuracao.prototipos;

import java.util.Collection;
import java.util.List;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoDependenciaIoC;
import br.com.efigueredo.container.configuracao.InterfaceConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;

@ConfiguracaoDependencia
public class ClasseConfiguracaoPrototipo5 extends ConfiguracaoDependenciaIoC {

	@Override
	public void configuracao(InterfaceConfiguracaoIoCBuilder icb) throws ConfiguracaoDependenciaInvalidaException {
		icb.adicionarConfiguracao(Collection.class, List.class);
	}

}
