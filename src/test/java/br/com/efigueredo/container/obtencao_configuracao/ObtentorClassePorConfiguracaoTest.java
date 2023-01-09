package br.com.efigueredo.container.obtencao_configuracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.configuracao.ConfiguracaoIoC.ConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;

class ObtentorClassePorConfiguracaoTest {
	
	private ObtentorClassePorConfiguracao obtentor;

	@BeforeEach
	void setUp() throws Exception {
		ConfiguracaoIoCBuilder builder = new ConfiguracaoIoCBuilder();
		builder.addConfiguracao(List.class, ArrayList.class);
		this.obtentor = new ObtentorClassePorConfiguracao(builder.build());
	}

	@Test
	void deveriaRetornar_AConfiguracaoArrayList_QuandoForRequisitadoList() throws ClasseIlegalParaIntanciaException {
		Class<?> classeObtida = this.obtentor.obterClasseConfigurada(List.class);
		assertEquals(classeObtida.getName(), ArrayList.class.getName());
	}
	
	@Test
	void deveriaLancarExcecao_QuandoNaoHouverConfiguracaoParaAClasseSolicitada() throws ClasseIlegalParaIntanciaException {
		assertThrows(ClasseIlegalParaIntanciaException.class, () -> this.obtentor.obterClasseConfigurada(Map.class));
	}

}
