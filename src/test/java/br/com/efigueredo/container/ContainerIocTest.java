package br.com.efigueredo.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseConstrutorAnotadoComParametrosPrototipo;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseConstrutorComParametroPublicoPrototipo;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseConstrutorPadraoAnotadoPrototipo;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseConstrutorPadraoPrivadoAnotadoPrototipo;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseConstrutorPadraoPrototipo;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseDependenciaConfiguradaExistente;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseDependenciaNaoConfigurada;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseDuploConstrutorAnotado;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseInjecaoLoopPrototipo1;
import br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado.ClasseSemConstrutorValido;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.InstanciacaoObjetoInterrompidaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.exception.LoopDeInjecaoDependenciaException;

@Tag("integracao")
class ContainerIocTest {

	private ContainerIoc container;

	@BeforeEach
	public void setup() throws Exception {
		this.container = new ContainerIoc();
	}

	/*
	 * Para testar todos os métodos, copie o pacote de prototipos
	 * [br.com.efigueredo.container.construtor.prototipos_teste_geral_integrado]
	 * para algum pacote do diretório [src/main/java].
	 */

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorPadrao_Publico()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoSemParametros_Publico()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoAnotadoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoAnotadoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoSemParametros_Privado()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {

		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoPrivadoAnotadoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrivadoAnotadoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoComParametros_Publico()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		Object instancia = this.container.getInstancia(ClasseConstrutorComParametroPublicoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorComParametroPublicoPrototipo);
		ClasseConstrutorComParametroPublicoPrototipo resultado = (ClasseConstrutorComParametroPublicoPrototipo) instancia;
		assertEquals(resultado.getArg1(), "");
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoComParametros_Privado()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		Object instancia = this.container.getInstancia(ClasseConstrutorAnotadoComParametrosPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorAnotadoComParametrosPrototipo);
		ClasseConstrutorAnotadoComParametrosPrototipo resultado = (ClasseConstrutorAnotadoComParametrosPrototipo) instancia;
		assertEquals(resultado.getArg1(), "");
	}

	@Test
	public void deveriaLancarExcecao_QuandoHouverDoisConstrutoresAnotados_ArrobaInjecao()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.container.getInstancia(ClasseDuploConstrutorAnotado.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoAClasseNaoTem_ConstrutorPadraoEConstrutorAnotado()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.container.getInstancia(ClasseSemConstrutorValido.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoHouver_LoopDeInjecaoDeDependencias()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		assertThrows(LoopDeInjecaoDependenciaException.class,
				() -> this.container.getInstancia(ClasseInjecaoLoopPrototipo1.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoForPedidoUmaInterface_SemConfiguracao()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		assertThrows(ClasseIlegalParaIntanciaException.class,
				() -> this.container.getInstancia(ClasseDependenciaNaoConfigurada.class));
	}

	@Test
	public void deveriaRetornarObjetoConfigurado_List_Para_ArrayList()
			throws ClasseIlegalParaIntanciaException, ContainerIocException, InstanciacaoObjetoInterrompidaException {
		Object instancia = this.container.getInstancia(ClasseDependenciaConfiguradaExistente.class);
		assertTrue(instancia instanceof ClasseDependenciaConfiguradaExistente);
		ClasseDependenciaConfiguradaExistente resultado = (ClasseDependenciaConfiguradaExistente) instancia;
		assertTrue(resultado.getArg1() instanceof ArrayList);
		assertTrue(resultado.getArg1().isEmpty());
	}

}
