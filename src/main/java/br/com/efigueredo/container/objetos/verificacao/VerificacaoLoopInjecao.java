package br.com.efigueredo.container.objetos.verificacao;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.reflections.Reflections;

import br.com.efigueredo.container.construtor.ManipuladorConstrutoresContainer;
import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.objetos.verificacao.exception.LoopDeInjecaoDependenciaException;

/**
 * <h4>Classe que representa a verificação de ocorrência de loop de injeção de
 * depêndencia.</h4>
 * 
 * Essa situação ocorre quando um construtor necessista de uma depêndencia que
 * pode apontar para algum construtor anterior. O que faria com que ele
 * reiniciasse a sequência de instâncias infinitamente.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificacaoLoopInjecao implements VerificacaoConstrutor {

	/** Objeto responsável pela reflexão do projeto. */
	private Reflections reflections;
	
	/**
	 * As classes verificadas. Atrbuto será utilizado para a verificação em cadeia.
	 */
	private List<Class<?>> classesOcorrentes;

	/**
	 * Lista com todos as classes do projeto dentro do diretório [src/main/java] ou
	 * [src\main\java].
	 */
	private List<Class<?>> todasAsClassesDoProjeto;

	/**
	 * Construtor.
	 * 
	 * Obtém todas as clases do sistema e atribui ao atributo
	 * {@code todasAsClassesDoProjeto}.
	 *
	 * @param reflections Objeto responsável pela reflexão do projeto.
	 */
	public VerificacaoLoopInjecao(Reflections reflections) {
		this.classesOcorrentes = new ArrayList<Class<?>>();
		this.reflections = reflections;
		this.todasAsClassesDoProjeto = this.reflections.getSubTypesOf(Object.class).stream().toList();
	}

	/**
	 * Realizar a verificação em cadeia das depêndencias de todos os objetos que
	 * serão necessários para a intância principal.
	 * 
	 * Seu funcionamento consiste em iterar por todos os parâmetros do construtor
	 * principal. Verificando se as classes das qual esse construtror dependênde
	 * existem no projeto. Pois, são essas classes que podem gerar loop. Uma classe
	 * como String por exemplo, não poderia ocasionar nesse evento.
	 * 
	 * Em seguida é verificado se a classe do parâmetro está na lista de classes já
	 * ocorrentes. Caso estejam, a exceção será lançada na pilha. Caso contrário,
	 * todos os parâmetros do construtor desse objeto serão verificado novamente
	 * pelo mesmo processo. Assim criando uma cadeia de verificações.
	 *
	 * @param construtor Objeto {@linkplain Contructor} que representa o construtor
	 *                   da instância principal.
	 * @throws ContainerIocException Ocorrerá caso haja um loop de injeção ou uma
	 *                               inversão de controle inválida possa ocorrer.
	 */
	@Override
	public void verificar(Constructor<?> construtor) throws ContainerIocException {
		List<Class<?>> classesParametros = Arrays.asList(construtor.getParameterTypes());
		for (Class<?> classe : classesParametros) {
			if (this.todasAsClassesDoProjeto.contains(classe)) {
				this.verificarSeAClasseJaEsteveNaListaDeClassesOcorrentes(classe);
				this.classesOcorrentes.add(classe);
				this.verificarConstrutorDaClasseQuePassouNaVerificacao(classe);
			}
		}
	}

	/**
	 * Método de pacote para auxiliar nos testes automatizados.
	 *
	 * @param classes Lista de classes.
	 */
	void setTodasAsClassesDoProjeto(List<Class<?>> classes) {
		this.todasAsClassesDoProjeto = classes;
	}

	/**
	 * Método privado auxiliar responsável por verificar se a classe já esteve na
	 * lista de classes ocorrentes. Caso o resultado seja verdadeiro, o loop de
	 * injeção foi identificado e uma exceção será lançada.
	 *
	 * @param classe Classe para executar verificação.
	 * @throws LoopDeInjecaoDependenciaException Ocorrerá caso a classe já exista na
	 *                                           lista de classes ocorrentes.
	 *                                           Portanto, indicando um loop.
	 */
	private void verificarSeAClasseJaEsteveNaListaDeClassesOcorrentes(Class<?> classe)
			throws LoopDeInjecaoDependenciaException {
		if (this.classesOcorrentes.contains(classe)) {
			throw new LoopDeInjecaoDependenciaException(
					"A classe " + classe.getName() + " possui uma dependência que irá gerar um loop de injeção.");
		}
	}

	/**
	 * Método privado auxiliar responsável por reiniciar a verificação do construtor
	 * da classe que não foi encontrada na lista de classes ocorrentes.
	 *
	 * @param classe A classe.
	 * @throws ContainerIocException Ocorrerá caso haja um loop de injeção ou uma
	 *                               inversão de controle inválida possa ocorrer.
	 */
	private void verificarConstrutorDaClasseQuePassouNaVerificacao(Class<?> classe) throws ContainerIocException {
		Constructor<?> construtorDaClasse = new ManipuladorConstrutoresContainer().getConstrutorAdequado(classe);
		this.verificar(construtorDaClasse);
	}

}
