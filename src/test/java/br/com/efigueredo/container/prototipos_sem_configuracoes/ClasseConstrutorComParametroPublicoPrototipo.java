package br.com.efigueredo.container.prototipos_sem_configuracoes;

import br.com.efigueredo.container.anotacao.Injecao;

public class ClasseConstrutorComParametroPublicoPrototipo {
	
	private String arg1;

	@Injecao
	public ClasseConstrutorComParametroPublicoPrototipo(String arg1) {
		this.arg1 = arg1;	
	}
	
	public String getArg1() {
		return arg1;
	}

}
