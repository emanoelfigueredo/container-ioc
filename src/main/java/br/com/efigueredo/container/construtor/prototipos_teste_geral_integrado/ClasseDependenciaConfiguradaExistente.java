package br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado;

import java.util.List;

import br.com.efigueredo.container.anotacao.Injecao;

public class ClasseDependenciaConfiguradaExistente {

	private List<?> arg1;

	@Injecao
	public ClasseDependenciaConfiguradaExistente(List<?> arg1) {
		this.arg1 = arg1;	
	}
	
	public List<?> getArg1() {
		return arg1;
	}
	
}
