package br.com.efigueredo.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.construtor.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.objetos.verificacao.exception.LoopDeInjecaoDependenciaException;
import br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoCorreta.ClasseDependenciaConfiguradaExistente;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseConstrutorAnotadoComParametrosPrototipo;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseConstrutorComParametroPublicoPrototipo;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseConstrutorPadraoAnotadoPrototipo;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseConstrutorPadraoPrivadoAnotadoPrototipo;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseConstrutorPadraoPrototipo;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseDependenciaNaoConfigurada;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseDuploConstrutorAnotado;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseInjecaoLoopPrototipo1;
import br.com.efigueredo.container.prototipos_sem_configuracoes.ClasseSemConstrutorValido;

@Tag("integracao")
class ContainerIocTest {

	private ContainerIoc container;

	public void setup(String pacotePrototipo) throws Exception {
		this.container = new ContainerIoc(pacotePrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorPadrao_Publico()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoSemParametros_Publico()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoAnotadoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoAnotadoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoSemParametros_Privado()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Object instancia = this.container.getInstancia(ClasseConstrutorPadraoPrivadoAnotadoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrivadoAnotadoPrototipo);
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoComParametros_Publico()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Object instancia = this.container.getInstancia(ClasseConstrutorComParametroPublicoPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorComParametroPublicoPrototipo);
		ClasseConstrutorComParametroPublicoPrototipo resultado = (ClasseConstrutorComParametroPublicoPrototipo) instancia;
		assertEquals(resultado.getArg1(), "");
	}

	@Test
	public void deveriaRetornarInstanciaDe_ClasseConstrutorAnotadoComParametros_Privado()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Object instancia = this.container.getInstancia(ClasseConstrutorAnotadoComParametrosPrototipo.class);
		assertTrue(instancia instanceof ClasseConstrutorAnotadoComParametrosPrototipo);
		ClasseConstrutorAnotadoComParametrosPrototipo resultado = (ClasseConstrutorAnotadoComParametrosPrototipo) instancia;
		assertEquals(resultado.getArg1(), "");
	}

	@Test
	public void deveriaLancarExcecao_QuandoHouverDoisConstrutoresAnotados_ArrobaInjecao()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.container.getInstancia(ClasseDuploConstrutorAnotado.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoAClasseNaoTem_ConstrutorPadraoEConstrutorAnotado()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		assertThrows(InversaoDeControleInvalidaException.class,
				() -> this.container.getInstancia(ClasseSemConstrutorValido.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoHouver_LoopDeInjecaoDeDependencias()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		assertThrows(LoopDeInjecaoDependenciaException.class,
				() -> this.container.getInstancia(ClasseInjecaoLoopPrototipo1.class));
	}

	@Test
	public void deveriaLancarExcecao_QuandoForPedidoUmaInterface_SemConfiguracao()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		assertThrows(ClasseIlegalParaIntanciaException.class,
				() -> this.container.getInstancia(ClasseDependenciaNaoConfigurada.class));
	}

	@Test
	public void deveriaRetornarObjetoConfigurado_List_Para_ArrayList()
			throws Exception {
		this.setup("br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoCorreta");
		Object instancia = this.container.getInstancia(ClasseDependenciaConfiguradaExistente.class);
		assertTrue(instancia instanceof ClasseDependenciaConfiguradaExistente);
		ClasseDependenciaConfiguradaExistente resultado = (ClasseDependenciaConfiguradaExistente) instancia;
		assertTrue(resultado.getArg1() instanceof ArrayList);
		assertTrue(resultado.getArg1().isEmpty());
	}

}
