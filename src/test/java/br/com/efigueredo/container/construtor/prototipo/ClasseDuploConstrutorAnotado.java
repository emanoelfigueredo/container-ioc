package br.com.efigueredo.container.construtor.prototipo;

import br.com.efigueredo.container.anotacao.Injecao;

public class ClasseDuploConstrutorAnotado {

	@Injecao
	public ClasseDuploConstrutorAnotado(String arg1) {
		
	}
	
	@Injecao
	public ClasseDuploConstrutorAnotado(String arg1, String arg2) {
		
	}
	
}