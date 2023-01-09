package br.com.efigueredo.container.obtencao_configuracao;

public class VerificadorTipoClasse {

	public boolean verificarClasse(Class<?> classe) {
		if (classe.isInterface()) {
			return true;
		}
		return false;
	}
	
	
}
