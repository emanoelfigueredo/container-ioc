package br.com.efigueredo.container.configuracao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/*
 * Devido ao objeto Projeto somente carregar os diretórios src/main/java e src/main/resources
 * Realizar testes de integração no diretório de testes [src/test/java] fica impossível.
 * Para realizar os testes, siga as instruções de cada método
 */

@Tag("integracao")
class GerenteDeConfiguracoesDeDependenciasTest {
	
	private GerenteDeConfiguracoesDeDependencias gerente;

	@BeforeEach
	public void setup() throws PacoteInexistenteException {
		this.gerente = new GerenteDeConfiguracoesDeDependencias();
	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração configurada corretamente com apenas uma configuração 
	 * de List para ArrayList
	 * 
	 * Para realizar esse teste copie a classe ClasseConfiguracaoPrototipo3 para algum pacote do
	 * diretório [src/main/java].
	 */
	
//	@Test
//	public void deveriaRetornarUmObjetoDeConfiguracao_ContendoUmaConfiguracaoParaAIntefaceList() throws PacoteInexistenteException {
//		ConfiguracaoIoC configuracao = this.gerente.configurar();
//		assertTrue(configuracao.getMapaConfiguracaoDependencia().size() == 1);
//		assertTrue(configuracao.getConfiguracao(List.class) == ArrayList.class);
//	}
	
	/*
	 * SITUAÇÃO => Não existe classe de configuração. O objeto de configuração deveria ter uma HashMap vazio.
	 * 
	 * Para realizar esse teste não utilize classes de configuração no diretório [src/main/java].
	 */
	
//	@Test
//	public void deveriaRetornarObjetoDeConfiguracaoComMapaDeConfiguracaoVazio_QuandoNaoHouverClassesDeConfiguracao() throws PacoteInexistenteException {
//		ConfiguracaoIoC configuracao = this.gerente.configurar();
//		assertTrue(configuracao.getMapaConfiguracaoDependencia().size() == 0);
//	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração, mas a configuração está incorreta.
	 * Uma classe concreta que não é implementação da interface inserida.
	 * 
	 * Para realizar esse teste copie a classe ClasseConfiguracaoPrototipo4 para algum pacote do
	 * diretório [src/main/java].
	 */
	
//	@Test
//	public void deveriaLancarExcecao_QuandoAClasseConfiguradaNaoForImplementacao_DaClasseChave() throws PacoteInexistenteException {
//		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.gerente.configurar());
//	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração, mas a configuração está incorreta.
	 * A inteface Collection está apontando para a inteface List. A classe chave não pode ser uma inteface.
	 * 
	 * Para realizar esse teste copie a classe ClasseConfiguracaoPrototipo5 para algum pacote do
	 * diretório [src/main/java].
	 */
	
//	@Test
//	public void deveriaLancarExcecao_QuandoAClasseConfiguradaNaoForImplementacao_DaClasseChave() throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
//		assertThrows(ConfiguracaoDependenciaInvalidaException.class, () -> this.gerente.configurar());
//	}
	
	/*
	 * SITUAÇÃO => Existe classe de configuração anotada, mas não extende a classe ConfiguracaoDependenciaIoC.
	 * 
	 * Para realizar esse teste copie a classe ClasseConfiguracaoPrototipoSemSuperClasse2 para algum pacote do
	 * diretório [src/main/java].
	 */
	
//	@Test
//	public void deveriaLancarExcecao_QuandoHouverUmaClasseDeConfiguracao_QueNaoExtendaAClasse_ConfiguracaoDependenciaIoC() {
//		assertThrows(HerancaConfiguracaoNaoIdentificadaException.class, () -> this.gerente.configurar());
//	}

}
