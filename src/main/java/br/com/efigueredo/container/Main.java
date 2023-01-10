package br.com.efigueredo.container;

import br.com.efigueredo.container.exception.ContainerIocException;

public class Main {
	
	public static void main(String[] args) throws ContainerIocException {
		
		ContainerIoc container = new ContainerIoc("br.com.efigueredo.container");
		
	}

}
