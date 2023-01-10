package br.com.efigueredo.container.construtor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.efigueredo.container.anotacao.Injecao;
import br.com.efigueredo.container.construtor.prototipo.ClasseConstrutorAnotado;
import br.com.efigueredo.container.construtor.prototipo.ClasseConstrutorPadraoPrototipo;
import br.com.efigueredo.container.construtor.prototipo.ClasseDuploConstrutorAnotado;
import br.com.efigueredo.container.construtor.prototipo.ClasseSemConstrutorValido;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;

@Tag("unitario")
class ManipuladorConstrutoresContainerTest {
	
	@Mock
	private ManipuladorConstrutores manipuladorConstrutores;
	
	@InjectMocks
	private ManipuladorConstrutoresContainer manipuladorConstrutoresContainer;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);	
	}

	@Test
	public void deveriaRetornarConstrutorPadrao_DeClassePrototipo() throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException, ClasseIlegalParaIntanciaException {
		when(this.manipuladorConstrutores.buscarConstrutoresPorAnotacao(Injecao.class)).thenReturn(Arrays.asList());
		Constructor<?> construtorAdequado = this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseConstrutorPadraoPrototipo.class);
		Constructor<ClasseConstrutorPadraoPrototipo> construtorPadrao = ClasseConstrutorPadraoPrototipo.class.getDeclaredConstructor();
		assertEquals(construtorAdequado.getName(), construtorPadrao.getName());
	}
	
	@Test
	public void deveriaRetornarConstrutorAntotado_ComArrobaInjecao_DeClassePrototipo() throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException, ClasseIlegalParaIntanciaException {
		when(this.manipuladorConstrutores.buscarConstrutoresPorAnotacao(Injecao.class)).thenReturn(Arrays.asList(ClasseConstrutorAnotado.class.getDeclaredConstructor(String.class)));
		Constructor<?> construtorAdequado = this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseConstrutorAnotado.class);
		Constructor<ClasseConstrutorAnotado> construtorPadrao = ClasseConstrutorAnotado.class.getDeclaredConstructor(String.class);
		assertEquals(construtorAdequado.getName(), construtorPadrao.getName());
	}
	
	@Test
	public void deveriaLancarExcecao_QuandoHouverMaisDeUmConstrutorAnotado_ComArrobaInjecao() throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException, ClasseIlegalParaIntanciaException {
		when(this.manipuladorConstrutores.buscarConstrutoresPorAnotacao(Injecao.class)).thenReturn(Arrays.asList(ClasseDuploConstrutorAnotado.class.getDeclaredConstructor(String.class)));
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseDuploConstrutorAnotado.class));
	}
	
	@Test
	public void deveriaLancarExcecao_NaoHouverConstrutorPadraoNemConstrurAnotado_ComArrobaInjecao() throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException, ClasseIlegalParaIntanciaException {
		when(this.manipuladorConstrutores.buscarConstrutoresPorAnotacao(Injecao.class)).thenReturn(Arrays.asList(ClasseSemConstrutorValido.class.getDeclaredConstructor(String.class)));
		assertThrows(InversaoDeControleInvalidaException.class, () -> this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseSemConstrutorValido.class));
	}

}
