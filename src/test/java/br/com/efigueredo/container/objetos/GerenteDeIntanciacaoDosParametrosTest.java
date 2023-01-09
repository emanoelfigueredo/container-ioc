package br.com.efigueredo.container.objetos;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

class GerenteDeIntanciacaoDosParametrosTest {
	
	@Mock
	private ContainerIoc container;
	
	@InjectMocks
	private GerenteDeIntanciacaoDosParametros gerente;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveriaRetornarUmArrayDeInstancias_DadoUmArrayDeClasses() throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		when(this.container.getIntancia(String.class)).thenReturn("");
		when(this.container.getIntancia(ArrayList.class)).thenReturn(new ArrayList<>());
		Class<?>[] classes = {String.class, ArrayList.class};
		Object[] intanciaDosParametros = this.gerente.getIntanciaDosParametros(classes);
		assertTrue(intanciaDosParametros[0] instanceof String);
		assertTrue(intanciaDosParametros[1] instanceof ArrayList);
	}

}
