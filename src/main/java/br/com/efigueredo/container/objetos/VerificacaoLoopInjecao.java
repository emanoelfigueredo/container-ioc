package br.com.efigueredo.container.objetos;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.efigueredo.container.construtor.ManipuladorConstrutoresContainer;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.container.exception.LoopDeInjecaoDependenciaException;
import br.com.efigueredo.project_loader.projeto.ProjetoFactory;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe que representa a verificação de ocorrência de loop de injeção de
 * depêndencia.</h4>
 * 
 * Essa situação ocorre quando um construtor necessista de uma depêndencia que
 * pode apontar o construtor inicial de alguma maneira. Dessa forma, criando um
 * loop.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificacaoLoopInjecao implements VerificacaoConstrutor {

	/**
	 * As classes verificadas. Atrbuto será utilizado para a verificação em cadeia.
	 */
	private List<Class<?>> classes;

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
	 * @throws PacoteInexistenteException the pacote inexistente exception
	 */
	public VerificacaoLoopInjecao() throws PacoteInexistenteException {
		this.classes = new ArrayList<Class<?>>();
		this.todasAsClassesDoProjeto = new ProjetoFactory().criarProjeto().getSRC_MAIN_JAVA().getClasses();
	}

	/**
	 * Realizar a verificação em cadeia das depêndencias de todos os objetos que
	 * serão necessários para a intância principal.
	 * 
	 * Seu funcionamento consiste em iterar por todos os parâmetros do construtor
	 * principal. Verificando se os objetos no qual ele depêndem estão no projeto.
	 * Pois, eles que poderão ocasionar num loop.
	 * 
	 * Em seguida é verificado se eles estão na lista de classes no atributo da
	 * classe. Caso estejam, a exceção será lançada na pilha. Caso contrário, todos
	 * os parâmetros do construtor desse objeto serão verificado novamente pelo
	 * mesmo processo. Assim criando uma cadeia de verificações.
	 *
	 * @param construtor Objeto {@linkplain Contructor} que representa o construtor
	 *                   da instância principal.
	 */
	@Override
	public void verificar(Constructor<?> construtor) {
		this.classes.clear();
		List<Class<?>> classesParametros = Arrays.asList(construtor.getParameterTypes());
		classesParametros.forEach(c -> {
			if (this.todasAsClassesDoProjeto.contains(c)) {
				try {
					if (this.classes.contains(c)) {
						throw new LoopDeInjecaoDependenciaException("A classe " + c.getName()
								+ " possui uma dependência que irá gerar um loop de injeção.");
					}
					this.classes.add(c);
					Constructor<?> construtorDaClasse = new ManipuladorConstrutoresContainer(c).getConstrutorAdequado();
					this.verificar(construtorDaClasse);
				} catch (InversaoDeControleInvalidaException | LoopDeInjecaoDependenciaException
						| ClasseIlegalParaIntanciaException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
	}

}
