package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaException;
import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.container.configuracao.exception.HerancaConfiguracaoNaoIdentificadaException;


@Tag("integracao")
class GerenteDeConfiguracoesDeDependenciasTest {
	
	private GerenteDeConfiguracoesDeDependencias gerente;

	public void setup(String pacoteProtitpo) {
		Reflections reflections = new Reflections(pacoteProtitpo, new TypeAnnotationsScanner(), new SubTypesScanner(false));
		this.gerente = new GerenteDeConfiguracoesDeDependencias(reflections);
	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração configurada corretamente com apenas uma configuração 
	 * de List para ArrayList
	 * 
	 * => ClasseConfiguracaoComConfiguracaoCorreta
	 */
	
	@Test
	public void deveriaRetornarUmObjetoDeConfiguracao_ContendoUmaConfiguracaoParaAIntefaceList() throws ConfiguracaoDependenciaException {
		this.setup("br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoCorreta");
		ConfiguracaoIoC configuracao = this.gerente.getConfiguracao();
		assertTrue(configuracao.getMapaConfiguracaoDependencia().size() == 1);
		assertTrue(configuracao.getConfiguracao(List.class) == ArrayList.class);
	}
	
	/*
	 * SITUAÇÃO => Não existe classe de configuração. O objeto de configuração deveria ter uma HashMap vazio.
	 */
	
	@Test
	public void deveriaRetornarObjetoDeConfiguracaoComMapaDeConfiguracaoVazio_QuandoNaoHouverClassesDeConfiguracao() throws ConfiguracaoDependenciaException {
		this.setup("br.com.efigueredo.container.prototipos_sem_configuracoes");
		ConfiguracaoIoC configuracao = this.gerente.getConfiguracao();
		assertTrue(configuracao.getMapaConfiguracaoDependencia().size() == 0);
	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração, mas a configuração está incorreta.
	 * Uma classe concreta que não é implementação da interface inserida.
	 * Classe valor não implementa a classe chave.
	 */
	
	@Test
	public void deveriaLancarExcecao_QuandoAClasseConfiguradaNaoForImplementacao_DaClasseChave() {
		this.setup("br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaImplementacao");
		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.gerente.getConfiguracao());
	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração, mas a configuração está incorreta.
	 * A inteface Collection está apontando para a inteface List. A classe chave não pode ser uma inteface.
	 */
	
	@Test
	public void deveriaLancarExcecao_QuandoAClasseConfiguradaNaoForImplementacao_DaClasseChave_Interface() {
		this.setup("br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoIncorretaValorInterface");
		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.gerente.getConfiguracao());
	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração anotada, mas não extende a classe ConfiguracaoDependenciaIoC.
	 */
	
	@Test
	public void deveriaLancarExcecao_QuandoHouverUmaClasseDeConfiguracao_QueNaoExtendaAClasse_ConfiguracaoDependenciaIoC() throws ConfiguracaoDependenciaException {
		this.setup("br.com.efigueredo.container.prototipos_com_configuracoes.configuracaoSemHerancaClasseConfigura");
		assertThrows(HerancaConfiguracaoNaoIdentificadaException.class, () -> this.gerente.getConfiguracao());
	}

}
