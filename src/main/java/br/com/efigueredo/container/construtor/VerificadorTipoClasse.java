package br.com.efigueredo.container.construtor;

public class VerificadorTipoClasse {

	public boolean verificarClasse(Class<?> classe) {
		if (classe.isInterface()) {
			return true;
		}
		return false;
	}
	
	
}
