package br.com.efigueredo.container.configuracao;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInterrompidaException;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

/**
 * <h4>Classe responsável por gerenciar as classes de configurações e seus
 * recursos.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class GerenteDeConfiguracoesDeDependencias {

	/** O objeto de configuração. */
	private ConfiguracaoIoC configuracao;

	private ManipuladorClassesConfiguracaoDependencias manipuladorClassesConfiguracao;
	private ManipuladorMetodosConfiguracoesDependencias manipuladorMetodosConfiguracao;
	private VerificadorConfiguracoesDependencias verificador;

	/**
	 * Construtor.
	 *
	 * @param gerenteClasses Objeto responsável por gerenciar as classes do projeto.
	 * @throws PacoteInexistenteException Ocorrerá caso o pacote raiz do projeto não
	 *                                    esteja no sistema de arquivos do sistema
	 *                                    operacial.
	 */
	public GerenteDeConfiguracoesDeDependencias() throws PacoteInexistenteException {
		this.configuracao = new ConfiguracaoIoC();
		this.manipuladorClassesConfiguracao = new ManipuladorClassesConfiguracaoDependencias();
		this.manipuladorMetodosConfiguracao = new ManipuladorMetodosConfiguracoesDependencias(this.configuracao);
		this.verificador = new VerificadorConfiguracoesDependencias();
	}

	/**
	 * Unir todos os passos que são necesários para obter o mapa de configuração
	 * para ser utilizado pelo container ioc.
	 * 
	 * @return Objeto {@linkplain ConfiguracaoIoT} pronto para uso.
	 * @throws PacoteInexistenteException Ocorrerá se o pacote do projeto não
	 *                                    existir no sistema de arquivos do sistema
	 *                                    operacional.
	 * @throws ConfiguracaoDependenciaInterrompidaException 
	 * @throws ConfiguracaoDependenciaInvalidaException 
	 */
	public ConfiguracaoIoC configurar() throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
		List<Class<?>> classesConfiguracao = this.obterClassesConfiguracaoDependencias();
		this.verificarClassesObtidas(classesConfiguracao);
		Map<Class<?>, Method> metodosConfiguracao = this.obterMetodosConfiguracaoDependencias(classesConfiguracao);
		this.invocarMetodosConfiguracao(metodosConfiguracao);
		return this.configuracao;
	}

	private void verificarClassesObtidas(List<Class<?>> classes) {
		classes.forEach(c -> {
			try {
				this.verificador.verificarSeExtendeConfiguracaoDependenciaIoC(c);
			} catch (HerancaConfiguracaoNaoIdentificadaException e) {
				e.printStackTrace();
			}
		});

	}

	private List<Class<?>> obterClassesConfiguracaoDependencias() throws PacoteInexistenteException {
		return this.manipuladorClassesConfiguracao.obterClassesDeConfiguracao();
	}

	private Map<Class<?>, Method> obterMetodosConfiguracaoDependencias(List<Class<?>> classesConfiguracao) {
		return this.manipuladorMetodosConfiguracao.getMetodosConfiguracao(classesConfiguracao);
	}

	private void invocarMetodosConfiguracao(Map<Class<?>, Method> metodosConfiguracao)
			throws PacoteInexistenteException, ConfiguracaoDependenciaInvalidaException, ConfiguracaoDependenciaInterrompidaException {
		this.manipuladorMetodosConfiguracao.invocarMetodos(metodosConfiguracao);
	}

}
