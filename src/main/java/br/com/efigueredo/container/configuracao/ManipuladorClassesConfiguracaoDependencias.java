package br.com.efigueredo.container.configuracao;

import java.util.ArrayList;
import java.util.List;

import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.project_loader.projeto.ProjetoFactory;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;
import br.com.efigueredo.project_loader.projeto.recursos.java.GerenteDeClasses;

/**
 * <h4>Classe responsável por manipular todas as classes de configuração de
 * dependências do projeto.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ManipuladorClassesConfiguracaoDependencias {

	/** Objeto responsável por gerenciar as classes do projeto. */
	private GerenteDeClasses gerenteClasses;

	/**
	 * Construtor.
	 *
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	public ManipuladorClassesConfiguracaoDependencias() throws PacoteInexistenteException {
		this.gerenteClasses = new ProjetoFactory().criarProjeto().getSRC_MAIN_JAVA().getGerenteDeClasses();
	}

	/**
	 * Método responsável por obter todas as classes do sistema anotadas
	 * com @ConfiguracaoDependencia.
	 * 
	 * Utilizando o objeto {@linkplain GerenteDeClasses}, é possível obter as
	 * classes anotadas.
	 *
	 * @return Uma lista de objetos {@linkplain Class} que representam todas as
	 *         classes correspondentes.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 */
	List<Class<?>> obterClassesDeConfiguracao() throws PacoteInexistenteException {
		List<Class<?>> classesPelaAnotacao = this.gerenteClasses.getClassesPelaAnotacao(ConfiguracaoDependencia.class);
		if (classesPelaAnotacao == null) {
			return new ArrayList<Class<?>>();
		}
		return classesPelaAnotacao;
	}

}
