package br.com.efigueredo.container;

import java.lang.reflect.Constructor;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC;
import br.com.efigueredo.container.configuracao.GerenteDeConfiguracoesDeDependencias;
import br.com.efigueredo.container.construtor.ManipuladorConstrutoresContainer;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.InstanciacaoObjetoInterrompidaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.objetos.GerenteDeIntanciacaoDosParametros;
import br.com.efigueredo.container.objetos.InstanciadorDeObjetos;
import br.com.efigueredo.container.objetos.verificacao.VerificacaoLoopInjecao;
import br.com.efigueredo.container.objetos.verificacao.VerificadorDeConstrutores;
import br.com.efigueredo.container.obtencao_configuracao.GerenteDeObtencaoDeClassesConfiguradas;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por instânciar objetos pela sua classe.</h4>
 * 
 * Deve ser usado em situações onde é necessário que haja a inversão de controle
 * e injeção de depêndencias.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ContainerIoc {

	/** Objeto responsável por instânciar os objetos pelos seus construtores. */
	private InstanciadorDeObjetos instanciador;

	/** Objeto responsável por verificar os parâmetros dos construtores. */
	private VerificadorDeConstrutores verificador;

	/**
	 * Objeto responsável por gerenciar os procedimentos de obteção da classe
	 * configurada adequada.
	 */
	private GerenteDeObtencaoDeClassesConfiguradas gerenteObtencaoConfiguracao;

	/** Objeto responsável por instânciar os parâmetros de construtores. */
	private GerenteDeIntanciacaoDosParametros gerenteInstanciasParametros;

	/**
	 * Objeto responsável por disponibilizar as configurações de depêndencias
	 * inseridas pelo usuário.
	 */
	private ConfiguracaoIoC configuracaoDependencias;

	/**
	 * Construtor.
	 *
	 * @throws ContainerIocException      Ocorrerá se houver alguma falha na
	 *                                    configuração de dependências ou
	 *                                    instânciação do objeto.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote raiz do projeto não
	 *                                    for encontrado no sistema de arquivos do
	 *                                    sistema operacional.
	 */
	public ContainerIoc() throws ContainerIocException, PacoteInexistenteException {
		this.instanciador = new InstanciadorDeObjetos();
		this.verificador = new VerificadorDeConstrutores();
		this.configuracaoDependencias = new GerenteDeConfiguracoesDeDependencias().getConfiguracao();
		this.gerenteObtencaoConfiguracao = new GerenteDeObtencaoDeClassesConfiguradas(this.configuracaoDependencias);
		this.gerenteInstanciasParametros = new GerenteDeIntanciacaoDosParametros(this);
		this.setupVerificacoes();
	}

	/**
	 * Setup para as verificacoes de construtores.
	 *
	 * @throws PacoteInexistenteException Ocorrerá se o pacote raiz do projeto não
	 *                                    for encontrado no sistema de arquivos do
	 *                                    sistema operacional.
	 */
	private void setupVerificacoes() throws PacoteInexistenteException {
		this.verificador.adicionarConfiguracao(new VerificacaoLoopInjecao());
	}

	/**
	 * Obtenha uma instância de um objeto pela sua classe.
	 * 
	 * Seu funcionamento consiste em unir o funcionamento de todas as classes
	 * reponsáveis pelas tarefas necessárias para a intância de um objeto. Primeiro
	 * se obtém a classe configurada se ela existir. Segundo, é obtido o contrutor
	 * adequado para essa classe. Terceiro, é verificado se os parâmetros do
	 * construtor possuem dependências que possam gerar loop. Quarto, é realizada a
	 * instânciação do objeto.
	 *
	 * @param classe Objeto {@linkplain Class} para obter uma intância do mesmo.
	 * @return Intância da classe inserida.
	 * @throws ContainerIocException Ocorrerá se houver alguma falha na configuração
	 *                               de dependências ou instânciação do objeto.
	 */
	public Object getInstancia(Class<?> classe) throws ContainerIocException {
		classe = this.obterConfiguracaoDeDependencia(classe);
		Constructor<?> construtor = this.obterConstrutor(classe);
		this.verificador.verificar(construtor);
		return this.instanciar(construtor);
	}

	/**
	 * Método auxiliar privado responsável por instânciar um objeto.
	 *
	 * @param construtor Construtor da classe.
	 * @return O objeto instânciado.
	 * @throws InstanciacaoObjetoInterrompidaException Ocorrerá se a instânciação
	 *                                                 for interrompida. Analise a
	 *                                                 causa na stack trace.
	 * @throws ClasseIlegalParaIntanciaException       Lançado quando é requerido
	 *                                                 uma intância de interface não
	 *                                                 configurada.
	 * @throws ContainerIocException                   Ocorrerá se houver alguma
	 *                                                 falha na configuração de
	 *                                                 dependências ou instânciação
	 *                                                 do objeto.
	 */
	private Object instanciar(Constructor<?> construtor)
			throws InstanciacaoObjetoInterrompidaException, ClasseIlegalParaIntanciaException, ContainerIocException {
		if (construtor.getParameterCount() == 0) {
			return instanciador.intanciarPorContrutorPadrao(construtor);
		}
		Object[] intanciaDosParametros = gerenteInstanciasParametros
				.getIntanciaDosParametros(construtor.getParameterTypes());
		return instanciador.instanciarPorContrutorComParametros(construtor, intanciaDosParametros);
	}

	/**
	 * Método auxiliar privado responsável por obter o construtor para a classe
	 * solicitada.
	 *
	 * @param classe Classe.
	 * @return Construtor adequado.
	 * @throws InversaoDeControleInvalidaException Ocorrerá se houver mais de um
	 *                                             construtor anotado, ou se não
	 *                                             houver construtores anotados e
	 *                                             nem o padrão.
	 */
	private Constructor<?> obterConstrutor(Class<?> classe) throws InversaoDeControleInvalidaException {
		return new ManipuladorConstrutoresContainer().getConstrutorAdequado(classe);
	}

	/**
	 * Método auxiliar privado responsável por obter a classe configurada pelo
	 * usuário.
	 *
	 * @param classe Classe chave.
	 * @return Classe valor, classe configurada.
	 * @throws ClasseIlegalParaIntanciaException Lançado quando é requerido uma
	 *                                           intância de interface não
	 *                                           configurada.
	 */
	private Class<?> obterConfiguracaoDeDependencia(Class<?> classe) throws ClasseIlegalParaIntanciaException {
		return this.gerenteObtencaoConfiguracao.getClasseConfigurada(classe);
	}

}
