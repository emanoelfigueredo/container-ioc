package br.com.efigueredo.container.configuracao;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInterrompidaException;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por gerenciar os objetos encarregados das funções
 * necessárias para a obtenção das classes de configurações, invocação dos
 * métodos de configuração e configuração do objeto de
 * {@linkplain ConfiguracaoIoC}.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenteDeConfiguracoesDeDependencias {

	/** O objeto de configuração. */
	private ConfiguracaoIoC configuracao;

	/** Objeto responsável por manipular as classes de configuração do projeto. */
	private ManipuladorClassesConfiguracaoDependencias manipuladorClassesConfiguracao;

	/** Objecto responsável por manipular os métodos de configurações. */
	private ManipuladorMetodosConfiguracoesDependencias manipuladorMetodosConfiguracao;

	/**
	 * Objeto responsável por verificar os resultados das verificações ou classes de
	 * configurações.
	 */
	private VerificadorConfiguracoesDependencias verificador;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote raiz do projeto não
	 *                                    esteja no sistema de arquivos do sistema
	 *                                    operacial.
	 */
	public GerenteDeConfiguracoesDeDependencias() throws PacoteInexistenteException {
		this.configuracao = new ConfiguracaoIoC();
		this.manipuladorClassesConfiguracao = new ManipuladorClassesConfiguracaoDependencias();
		this.manipuladorMetodosConfiguracao = new ManipuladorMetodosConfiguracoesDependencias();
		this.verificador = new VerificadorConfiguracoesDependencias();
	}

	/**
	 * Obtenha um objeto {@linkplain ConfiguracaoIoC} configurado.
	 * 
	 * Seu funcionamento consiste em unir todos os processos necessários para a
	 * configuração derivados de vários objetos responsáveis.
	 *
	 * @return Objeto {@linkplain ConfiguracaoIoT} pronto para uso.
	 * @throws PacoteInexistenteException                   Ocorrerá se o pacote do
	 *                                                      projeto não existir no
	 *                                                      sistema de arquivos do
	 *                                                      sistema operacional.
	 * @throws ConfiguracaoDependenciaInvalidaException     Ocorrerá caso a
	 *                                                      configuração seja
	 *                                                      inválida. Podendo ser a
	 *                                                      classe valor não sendo
	 *                                                      uma interface ou filha
	 *                                                      da classe chave. Ou a
	 *                                                      classe valor sendo uma
	 *                                                      inteface.
	 * @throws ConfiguracaoDependenciaInterrompidaException Ocorrerá se houver algum
	 *                                                      erro de reflexão na
	 *                                                      configuração.
	 * @throws HerancaConfiguracaoNaoIdentificadaException  Ocorrerá se houver uma
	 *                                                      classe de configuração
	 *                                                      que não extenda a classe
	 *                                                      {@linkplain ConfiguracaoIoC}.
	 */
	public ConfiguracaoIoC getConfiguracao()
			throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException,
			ConfiguracaoDependenciaInterrompidaException, HerancaConfiguracaoNaoIdentificadaException {
		List<Class<?>> classesConfiguracao = this.obterClassesConfiguracaoDependencias();
		this.verificarClassesObtidas(classesConfiguracao);
		Map<Class<?>, Method> metodosConfiguracao = this.obterMetodosConfiguracaoDependencias(classesConfiguracao);
		this.configurarObjetosConfiguracao(metodosConfiguracao);
		return this.configuracao;
	}

	/**
	 * Método privado auxiliar para realizar as verificações das classes de
	 * configuração obtidas.
	 * 
	 * A única verificação necessária até o momento é verificar se alguma das
	 * classes de configuração não extende da classe {@linkplain ConfiguracaoIoC}.
	 *
	 * @param classes Lista de classes de configuração para serem verificadas.
	 * @throws HerancaConfiguracaoNaoIdentificadaException Ocorrerá se houver uma
	 *                                                     classe de configuração
	 *                                                     que não extenda a classe
	 *                                                     {@linkplain ConfiguracaoIoC}.
	 */
	private void verificarClassesObtidas(List<Class<?>> classes) throws HerancaConfiguracaoNaoIdentificadaException {
		for (Class<?> classe : classes) {
			this.verificador.verificarSeExtendeConfiguracaoDependenciaIoC(classe);
		}
	}

	/**
	 * Método privado auxiliar para obter todas as classes de configuração do
	 * projeto.
	 *
	 * @return Lista com todas as classes de configuração do projeto.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	private List<Class<?>> obterClassesConfiguracaoDependencias() throws PacoteInexistenteException {
		return this.manipuladorClassesConfiguracao.obterClassesDeConfiguracao();
	}

	/**
	 * Método privado auxiliar para obter todos os métodos de configuração das
	 * classes de configuração de dependências.
	 *
	 * @param classesConfiguracao Lista de classes de configuração de dependências.
	 * @return Mapa com chaves as classes e valores os métodos de configuração.
	 */
	private Map<Class<?>, Method> obterMetodosConfiguracaoDependencias(List<Class<?>> classesConfiguracao) {
		return this.manipuladorMetodosConfiguracao.getMetodosConfiguracao(classesConfiguracao);
	}

	/**
	 * Método privado auxiliar responsável por configurar o objeto de configuração.
	 * 
	 * Seu funcionamento consiste em obter a lista de builders configurados após a
	 * invocação dos métodos de configuração. Verificar os resultados e adicioná-los
	 * ao objeto de configuração.
	 *
	 * @param metodosConfiguracao Lista de métodos de configuração para serem
	 *                            invocados
	 * @throws PacoteInexistenteException                   Ocorrerá se o pacote do
	 *                                                      projeto não existir no
	 *                                                      sistema de arquivos do
	 *                                                      sistema operacional.
	 * @throws ConfiguracaoDependenciaInvalidaException     Ocorrerá caso a
	 *                                                      configuração seja
	 *                                                      inválida. Podendo ser a
	 *                                                      classe valor não sendo
	 *                                                      uma interface ou filha
	 *                                                      da classe chave. Ou a
	 *                                                      classe valor sendo uma
	 *                                                      inteface.
	 * @throws ConfiguracaoDependenciaInterrompidaException Ocorrerá se houver algum
	 *                                                      erro de reflexão na
	 *                                                      configuração.
	 */
	private void configurarObjetosConfiguracao(Map<Class<?>, Method> metodosConfiguracao)
			throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException,
			ConfiguracaoDependenciaInterrompidaException {
		List<ConfiguracaoIoCBuilder> buildersConfigurados = this.manipuladorMetodosConfiguracao
				.invocarMetodos(metodosConfiguracao);
		for (ConfiguracaoIoCBuilder builderConfigurado : buildersConfigurados) {
			this.executarVerificacoesResultadosConfiguracoes(builderConfigurado.getMapaConfiguracaoDependencia());
			this.configuracao.adicionarConfiguracao(builderConfigurado);
		}
	}

	/**
	 * Método privado auxiliar responsável por executar as verificações de
	 * resultados dos builders de configuração.
	 *
	 * @param mapaConfiguracoesInseridas Mapa de configurações dos builders.
	 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá caso a configuração
	 *                                                  seja inválida. Podendo ser a
	 *                                                  classe valor não sendo uma
	 *                                                  interface ou filha da classe
	 *                                                  chave. Ou a classe valor
	 *                                                  sendo uma inteface.
	 */
	private void executarVerificacoesResultadosConfiguracoes(Map<Class<?>, Class<?>> mapaConfiguracoesInseridas)
			throws ConfiguracaoDependenciaInvalidaException {
		this.verificador.verificarImplementacaoOuHerancaEntreChaveValor(mapaConfiguracoesInseridas);
		this.verificador.verificarSeExisteClasseValorInterface(mapaConfiguracoesInseridas);
	}

}
