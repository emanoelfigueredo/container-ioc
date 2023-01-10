package br.com.efigueredo.container.construtor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseConstrutorAnotado;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseConstrutorPadraoPrototipo;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseDuploConstrutorAnotado;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseSemConstrutorValido;

/*
 * Para realizar os testes de integração dessa classe, copie o pacote prototipo
 * [br.com.efigueredo.container.construtor.prototipo] para o diretório [src/main/java]
 */
@Tag("integrado")
public class ManipuladorConstrutoresContainerIntegracaoTest {

	private ManipuladorConstrutoresContainer manipuladorConstrutoresContainer;

	@BeforeEach
	public void setUp() throws Exception 
	{
		this.manipuladorConstrutoresContainer = new ManipuladorConstrutoresContainer();
	}

	/*
	 * Para realizar esse teste, copia a classe ClasseConstrutorPadraoPrototipo para
	 * qualquer pacote do projeto src/main/java.
	 */

	@Test
	public void deveriaRetornarConstrutorPadrao_DeClassePrototipo() throws InversaoDeControleInvalidaException,
			NoSuchMethodException, SecurityException, ClasseIlegalParaIntanciaException {
		Constructor<?> construtorAdequado = this.manipuladorConstrutoresContainer
				.getConstrutorAdequado(ClasseConstrutorPadraoPrototipo.class);
		Constructor<ClasseConstrutorPadraoPrototipo> construtorPadrao = ClasseConstrutorPadraoPrototipo.class
				.getDeclaredConstructor();
		assertEquals(construtorAdequado.getName(), construtorPadrao.getName());
	}

	/*
	 * Para realizar esse teste, copia a classe ClasseConstrutorAnotado para
	 * qualquer pacote do projeto src/main/java.
	 */

	@Test
	public void deveriaRetornarConstrutorAntotado_ComArrobaInjecao_DeClassePrototipo()
			throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException,
			ClasseIlegalParaIntanciaException {
		Constructor<?> construtorAdequado = this.manipuladorConstrutoresContainer
				.getConstrutorAdequado(ClasseConstrutorAnotado.class);
		Constructor<ClasseConstrutorAnotado> construtorPadrao = ClasseConstrutorAnotado.class
				.getDeclaredConstructor(String.class);
		assertEquals(construtorAdequado.getName(), construtorPadrao.getName());
	}

	/*
	 * Para realizar esse teste, copia a classe ClasseDuploConstrutorAnotado para
	 * qualquer pacote do projeto src/main/java.
	 */

	@Test
	public void deveriaLancarExcecao_QuandoHouverMaisDeUmConstrutorAnotado_ComArrobaInjecao()
			throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException,
			ClasseIlegalParaIntanciaException {
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseDuploConstrutorAnotado.class));
	}

	/*
	 * Para realizar esse teste, copia a classe ClasseSemConstrutorValido para
	 * qualquer pacote do projeto src/main/java.
	 */

	@Test
	public void deveriaLancarExcecao_NaoHouverConstrutorPadraoNemConstrurAnotado_ComArrobaInjecao()
			throws InversaoDeControleInvalidaException, NoSuchMethodException, SecurityException,
			ClasseIlegalParaIntanciaException {
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.manipuladorConstrutoresContainer.getConstrutorAdequado(ClasseSemConstrutorValido.class));
	}

}
