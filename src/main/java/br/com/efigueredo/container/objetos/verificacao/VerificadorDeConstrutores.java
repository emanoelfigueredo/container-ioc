package br.com.efigueredo.container.objetos.verificacao;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import br.com.efigueredo.container.exception.ContainerIocException;

/**
 * <h4>Classe responsável por executar as verificações para os
 * construtores.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
public class VerificadorDeConstrutores {

	/** Lista de verificações */
	private List<VerificacaoConstrutor> verificacoes;

	/**
	 * Construtor.
	 */
	public VerificadorDeConstrutores() {
		this.verificacoes = new ArrayList<VerificacaoConstrutor>();
	}

	/**
	 * Método responsável por adicionar uma configuração à lista de verificações.
	 *
	 * @param verificacao Objeto {@linkplain VerificacaoConstrutor} que representa a
	 *                    configuração.
	 */
	public void adicionarConfiguracao(VerificacaoConstrutor verificacao) {
		this.verificacoes.add(verificacao);
	}

	/**
	 * Método responsável por executar a verificação de todas os objetos da lista de
	 * verificações.
	 *
	 * @param construtor O construtor que será verificado.
	 * @throws ContainerIocException Ocorrerá caso haja um loop de injeção ou uma
	 *                               inversão de controle inválida possa ocorrer.
	 */
	public void verificar(Constructor<?> construtor) throws ContainerIocException {
		for (VerificacaoConstrutor verificacao : this.verificacoes) {
			verificacao.verificar(construtor);
		}
	}

}
