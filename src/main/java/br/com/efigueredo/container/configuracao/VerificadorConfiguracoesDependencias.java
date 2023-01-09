package br.com.efigueredo.container.configuracao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;
import br.com.efigueredo.container.exception.HerancaConfiguracaoNaoIdentificadaException;

/**
 * <h4>Classe responsável por disponibilizar verificações para as classes e
 * métodos de configuração de dependências.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificadorConfiguracoesDependencias {

	/**
	 * Verifique se exista alguma classe valor que não seja implementação ou filha
	 * da classe chave.
	 *
	 * @param mapaConfiguracoesInseridas Mapa de configurações.
	 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá caso a configuração
	 *                                                  seja inválida. Podendo ser a
	 *                                                  classe valor não sendo uma
	 *                                                  interface ou filha da classe
	 *                                                  chave. Ou a classe valor
	 *                                                  sendo uma inteface.
	 */
	public void verificarImplementacaoOuHerancaEntreChaveValor(Map<Class<?>, Class<?>> mapaConfiguracoesInseridas)
			throws ConfiguracaoDependenciaInvalidaException {
		Set<Class<?>> chaves = mapaConfiguracoesInseridas.keySet();
		for (Class<?> classeChave : chaves) {
			Class<?> classeValor = mapaConfiguracoesInseridas.get(classeChave);
			try {
				classeValor.asSubclass(classeChave);
			} catch (ClassCastException e) {
				throw new ConfiguracaoDependenciaInvalidaException(
						"A classe " + classeValor.getName() + " não é uma implementação de " + classeChave.getName());
			}
		}
	}

	/**
	 * Verifique se exista alguma classe valor que seja inteface.
	 *
	 * @param mapaConfiguracoesInseridas Mapa de configurações.
	 * @throws ConfiguracaoDependenciaInvalidaException Ocorrerá caso a configuração
	 *                                                  seja inválida. Podendo ser a
	 *                                                  classe valor não sendo uma
	 *                                                  interface ou filha da classe
	 *                                                  chave. Ou a classe valor
	 *                                                  sendo uma inteface.
	 */
	public void verificarSeExisteClasseValorInterface(Map<Class<?>, Class<?>> mapaConfiguracoesInseridas)
			throws ConfiguracaoDependenciaInvalidaException {
		Collection<Class<?>> classesValores = mapaConfiguracoesInseridas.values();
		for (Class<?> classeValor : classesValores) {
			if (classeValor.isInterface()) {
				throw new ConfiguracaoDependenciaInvalidaException("A classe valor" + classeValor.getName()
						+ " é uma inteface. As classes na posição de valor das configurações não podem ser interfaces.");
			}
		}
	}

	/**
	 * Método responsável por verificar se as classes anotadas
	 * com @ConfiguracaoDependencia. são filhas da super classe abstrata
	 * {@linkplain ConfiguracaoDependenciaIoC}.
	 *
	 * @param classe A classe a ser verificada.
	 * @throws HerancaConfiguracaoNaoIdentificadaException Ocorrerá quando a classe
	 *                                                     não for filha da classe
	 *                                                     {@linkplain ConfiguracaoDependenciaIoC}.
	 */
	void verificarSeExtendeConfiguracaoDependenciaIoC(Class<?> classe)
			throws HerancaConfiguracaoNaoIdentificadaException {
		try {
			classe.asSubclass(ConfiguracaoDependenciaIoC.class);
		} catch (Exception e) {
			throw new HerancaConfiguracaoNaoIdentificadaException("A classe " + classe.getName()
					+ " não herda da classe "
					+ "ConfiguracaoDependenciaIoC. Extenda a classe indicada e implemente o método de configuração.");
		}
	}

}
