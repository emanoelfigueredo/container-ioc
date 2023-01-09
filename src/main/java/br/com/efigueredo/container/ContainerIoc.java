package br.com.efigueredo.container;

import java.lang.reflect.Constructor;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC;
import br.com.efigueredo.container.configuracao.GerenteDeConfiguracoesDeDependencias;
import br.com.efigueredo.container.construtor.ManipuladorConstrutoresContainer;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.objetos.InstanciadorDeObjetos;
import br.com.efigueredo.container.objetos.verificacao.VerificacaoLoopInjecao;
import br.com.efigueredo.container.objetos.verificacao.VerificadorDeConstrutores;
import br.com.efigueredo.project_loader.projeto.ProjetoFactory;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por instânciar objetos por sua classe.</h4>
 * 
 * Deve ser usado em situações onde é necessário que haja a inversão de controle
 * e injeção de depêndencias.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ContainerIoc {

	/** Objeto responsável por instânciar os objetos pelos seus contrutores. */
	private InstanciadorDeObjetos instanciador;

	/** Objeto responsável por verificar os parâmetros dos construtores. */
	private VerificadorDeConstrutores verificador;

	/**
	 * Objeto responsável por disponibilizar as configurações de depêndencias
	 * inseridas pelo usuário.
	 */
	private ConfiguracaoIoC configuracaoDependencias;

	/**
	 * Construtor.
	 * 
	 * Nele são intânciados todos os objetos que tem funções específicas para todo o
	 * processo de instância de um objeto por sua classe.
	 *
	 * @throws PacoteInexistenteException Ocorrerá se o pacote raiz não existir no
	 *                                    sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public ContainerIoc() throws PacoteInexistenteException {
		this.instanciador = new InstanciadorDeObjetos(this);
		this.verificador = new VerificadorDeConstrutores();
		this.verificador.adicionarConfiguracao(new VerificacaoLoopInjecao());
		GerenteDeConfiguracoesDeDependencias gerenteDeConfiguracoesDeDependencias = new GerenteDeConfiguracoesDeDependencias(
				new ProjetoFactory().criarProjeto().getSRC_MAIN_JAVA().getGerenteDeClasses());
		this.configuracaoDependencias = gerenteDeConfiguracoesDeDependencias.configurar();
	}

	/**
	 * Obter uma instância de um objeto por sua classe. Ou, outro que esteja
	 * configurado para ser instânciado quando a classe chave for introduzida.
	 * 
	 * Seu funcionamento consiste em unir o funcionamento de todas as classes
	 * reponsáveis por determinadas tarefas. Primeiro se obtém a classe configurada
	 * se ela existir. Segundo, é obtido o contrutor adequado para essa classe.
	 * Terceiro, é verificado se os parâmetros do construtor possuem dependências
	 * que possam gerar loop. Quarto, é realizada a instânciação do objeto.
	 *
	 *
	 * @param classe Objeto {@linkplain Class} para obter uma intância do mesmo.
	 * @return Intância da classe inserida.
	 * @throws InversaoDeControleInvalidaException Lançado quando há alguma situação
	 *                                             em que não seja possível realizar
	 *                                             a intanciação do objeto.
	 * @throws ClasseIlegalParaIntanciaException   Lançado quando é requerido uma
	 *                                             intância de interface.
	 */
	public Object getIntancia(Class<?> classe)
			throws InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		classe = this.obterConfiguracaoDeDependencia(classe);
		Constructor<?> construtor = new ManipuladorConstrutoresContainer(classe).getConstrutorAdequado();
		this.verificador.verificar(construtor);
		return this.intanciar(construtor);
	}

	/**
	 * Método privado auxiliar responsável por realizar o processo de instânciação
	 * de um objeto.
	 *
	 * @param construtor Objeto {@linkplain Contructor} que será utilizado para a
	 *                   instânciação.
	 * @return O objeto instânciado.
	 */
	private Object intanciar(Constructor<?> construtor) {
		if (construtor.getParameterCount() == 0) {
			return instanciador.intanciarPorContrutorPadrao(construtor);
		}
		Object[] intanciaDosParametros = instanciador.getIntanciaDosParametros(construtor.getParameterTypes());
		return instanciador.instanciarPorContrutorComParametros(construtor, intanciaDosParametros);
	}

	/**
	 * Método auxiliar responsável por verificar se há uma classe configurada para a
	 * classe inserida. Se houver ele retorna a classe configurada. Caso contrário,
	 * será retornada a classe inserida.
	 *
	 * @param classe Objeto {@linkplain Class} para servir como chave para obter a
	 *               classe configurada.
	 * @return Se houver ele retorna a classe configurada.<br>
	 *         Caso contrário, será retornada a classe inserida.
	 */
	private Class<?> obterConfiguracaoDeDependencia(Class<?> classe) {
		Class<?> classeResultado = classe;
		if (this.configuracaoDependencias.configuracaoExiste(classe)) {
			classeResultado = this.configuracaoDependencias.getConfiguracao(classe);
		}
		return classeResultado;
	}

}
