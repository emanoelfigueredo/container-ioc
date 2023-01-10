package br.com.efigueredo.container.configuracao;

import java.util.HashMap;
import java.util.Map;

import br.com.efigueredo.container.configuracao.exception.ConfiguracaoDependenciaInvalidaException;

/**
 * <h4>Classe que representa a configuração utilizada pelo container IOC para
 * mapear qual objeto deve ser instânciado quando uma instância de interface for
 * solicitada ao container IoC.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class ConfiguracaoIoC {

	/**
	 * Objeto do tipo {@linkplain Map} responsável por armazenar as configurações de
	 * dependências.
	 */
	private Map<Class<?>, Class<?>> mapaConfiguracaoDependencia;

	/**
	 * Construtor padrão.
	 */
	public ConfiguracaoIoC() {
		this.mapaConfiguracaoDependencia = new HashMap<Class<?>, Class<?>>();
	}

	/**
	 * Construtor privado utilizado somente pela classe Builder.
	 *
	 * @param mapaConfiguracaoDependencia Um objeto do tipo {@linkplain Map} com as
	 *                                    configurações.
	 */
	private ConfiguracaoIoC(Map<Class<?>, Class<?>> mapaConfiguracaoDependencia) {
		this.mapaConfiguracaoDependencia = new HashMap<Class<?>, Class<?>>();
		this.mapaConfiguracaoDependencia.putAll(mapaConfiguracaoDependencia);
	}

	/**
	 * Método responsável por retornar uma dependência baseada na chave pela qual
	 * foi configurada.
	 *
	 * @param classe A classe chave.
	 * @return A classe valor.
	 */
	public Class<?> getConfiguracao(Class<?> classe) {
		return this.mapaConfiguracaoDependencia.get(classe);
	}

	/**
	 * Verifica se a classe inserida está configurada como chave no mapa.
	 * 
	 * @param classe Objeto {@linkplain Class} para ser verificado nas chaves do
	 *               mapa.
	 * @return true, se estiver configurado<br>
	 *         false, se não estiver.
	 */
	public boolean configuracaoExiste(Class<?> classe) {
		return this.mapaConfiguracaoDependencia.containsKey(classe);
	}

	/**
	 * Obter a referência do mapa que armazena todas as configurações.
	 *
	 * @return Um objeto do tipo {@linkplain Map} com as configurações.
	 */
	public Map<Class<?>, Class<?>> getMapaConfiguracaoDependencia() {
		return this.mapaConfiguracaoDependencia;
	}

	/**
	 * Adiciona ao mapa de configurações as chaves e valores de outro mapa de
	 * configurações.
	 * 
	 * Esse mapa deve ser obtido pelo builder.
	 *
	 * @param builder O builder da classe {@linkplain ConfiguracaoIoCBuilder}
	 */
	public void adicionarConfiguracao(ConfiguracaoIoCBuilder builder) {
		this.mapaConfiguracaoDependencia.putAll(builder.mapaConfiguracaoDependencia);
	}

	/**
	 * A classe {@linkplain ConfiguracaoIoCBuilder} é responsável por encapsular os
	 * métodos de criação do mapa de configurações.
	 * 
	 * @author Emanoel
	 * @since 1.0.0
	 */
	public static class ConfiguracaoIoCBuilder {

		/**
		 * Objeto {@linkplain Map} utilizado para armazenar as configurações que
		 * posteriormente pode ser usado para implementar no mapa de configuração
		 * principal.
		 */
		private Map<Class<?>, Class<?>> mapaConfiguracaoDependencia;

		/**
		 * Construtor do Builder.
		 */
		public ConfiguracaoIoCBuilder() {
			this.mapaConfiguracaoDependencia = new HashMap<Class<?>, Class<?>>();
		}

		/**
		 * Método responsável por adicionar uma nova configuração no atributo
		 * {@linkplain mapaConfiguracaoDependencia}.
		 *
		 * @param classeChave O objeto do tipo {@linkplain Class} que representa a chave
		 *                    para obter a classe desejada.
		 * @param classeValor O objeto do tipo {@linkplain Class} que representa a
		 *                    classe desejada.
		 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá quando a classe
		 *                                                  valor não for implementação
		 *                                                  ou subclasse da classe
		 *                                                  chave.
		 * 
		 *                                                  exemplo: <br>
		 *                                                  classe chave => List.class
		 *                                                  <br>
		 *                                                  classe valor => String.class
		 */
		public void addConfiguracao(Class<?> classeChave, Class<?> classeValor)
				throws ConfiguracaoDependenciaInvalidaException {
			this.mapaConfiguracaoDependencia.put(classeChave, classeValor);
		}

		/**
		 * Obtenha o mapa de configuração do builder.
		 * 
		 * @return Mapa de configuração do builder.
		 */
		public Map<Class<?>, Class<?>> getMapaConfiguracaoDependencia() {
			return mapaConfiguracaoDependencia;
		}

		/**
		 * Método responsável por construir o objeto {@linkplain ConfiguracaoIoC}.
		 *
		 * @return O objeto {@linkplain ConfiguracaoIoC} configurado.
		 */
		public ConfiguracaoIoC build() {
			return new ConfiguracaoIoC(this.mapaConfiguracaoDependencia);
		}

	}

}
