package br.com.efigueredo.container.prototipos_sem_configuracoes;

import br.com.efigueredo.container.anotacao.Injecao;

public class ClasseInjecaoNormalPrototipo {

	private ClasseInjecaoNormalPrototipo2 classe;

	@Injecao
	public ClasseInjecaoNormalPrototipo(ClasseInjecaoNormalPrototipo2 classe) {
		this.classe = classe;
	}
	
	public ClasseInjecaoNormalPrototipo2 getClasse() {
		return classe;
	}
	
}
