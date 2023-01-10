package br.com.efigueredo.container.obtencao_configuracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.objetos.exception.ClasseIlegalParaIntanciaException;

@Tag("integracao")
class GerenteDeObtencaoDeClassesConfiguradasTest {
	
	private GerenteDeObtencaoDeClassesConfiguradas gerente;

	@BeforeEach
	void setUp() throws Exception {
		ConfiguracaoIoCBuilder builder = new ConfiguracaoIoCBuilder();
		builder.addConfiguracao(List.class, ArrayList.class);
		this.gerente = new GerenteDeObtencaoDeClassesConfiguradas(builder.build());
	}

	@Test
	void deveriaRetornarAPropriaClasse_SeElaNaoForInterface() throws ClasseIlegalParaIntanciaException {
		Class<?> classeConfigurada = this.gerente.getClasseConfigurada(String.class);
		assertEquals(classeConfigurada.getName(), String.class.getName());
	}
	
	@Test
	void deveriaRetornarAClasseArrayListConfigurada_QuandoForRequisitadoAClasseListConfigurada() throws ClasseIlegalParaIntanciaException {
		Class<?> classeConfigurada = this.gerente.getClasseConfigurada(List.class);
		assertEquals(classeConfigurada.getName(), ArrayList.class.getName());
	}
	
	@Test
	void deveriaLancarExcecao_QuandoAClasseForInterface_ENaoEstiverConfigurada() throws ClasseIlegalParaIntanciaException {
		assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.gerente.getClasseConfigurada(Map.class));
	}

}
