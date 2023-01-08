package br.com.efigueredo.container.exception;

public class ConfiguracaoDependenciaInterrompidaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConfiguracaoDependenciaInterrompidaException(Throwable causa) {
		super(causa);
	}

}
