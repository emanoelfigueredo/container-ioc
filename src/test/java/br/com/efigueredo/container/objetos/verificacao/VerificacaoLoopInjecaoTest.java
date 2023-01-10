package br.com.efigueredo.container.objetos.verificacao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import br.com.efigueredo.container.objetos.verificacao.exception.LoopDeInjecaoDependenciaException;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseInjecaoLoopPrototipo1;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseInjecaoLoopPrototipo2;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseInjecaoNormalPrototipo;
import br.com.efigueredo.container.prototipos_com_configuracoes.ClasseInjecaoNormalPrototipo2;

@Tag("integrado")
class VerificacaoLoopInjecaoTest {

	private VerificacaoLoopInjecao verificacao;

	void setUp(String pacotePrototipo) throws Exception {
		Reflections reflections = new Reflections(pacotePrototipo, new TypeAnnotationsScanner(), new SubTypesScanner(false));
		this.verificacao = new VerificacaoLoopInjecao(reflections);
		List<Class<? extends Object>> listaClasses = Arrays.asList(ClasseInjecaoNormalPrototipo.class, ClasseInjecaoNormalPrototipo2.class,
				ClasseInjecaoLoopPrototipo1.class, ClasseInjecaoLoopPrototipo2.class);
		this.verificacao.setTodasAsClassesDoProjeto(listaClasses);
	}
	
	/*
	 * Classes prototipos que não geram loop
	 * ClasseInjecaoNormalPrototipo
	 * ClasseInjecaoNormalPrototipo2
	 */

	/*
	 * Classes prototipos que irão gerar loop:
	 * ClasseInjecaoLoopPrototipo1
	 * ClasseInjecaoLoopPrototipo2
	 */
	
	@Test
	void deveriaLancarExcecaoLoopDeInjecao_QuandoForInseridoConstrutorQuePodeGerarLoopDeInjecao() throws Exception {
		this.setUp("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Constructor<ClasseInjecaoLoopPrototipo1> construtorLoop = ClasseInjecaoLoopPrototipo1.class.getDeclaredConstructor(ClasseInjecaoLoopPrototipo2.class);
		assertThrows(LoopDeInjecaoDependenciaException.class, () -> this.verificacao.verificar(construtorLoop));
	}
	
	@Test
	void naoDeveriaLancarExcecao_QunadoForInseridoConstrutorQueNaoGeraLoopDeInjecao() throws Exception {
		this.setUp("br.com.efigueredo.container.prototipos_sem_configuracoes");
		Constructor<ClasseInjecaoNormalPrototipo> construtorSemLoop = ClasseInjecaoNormalPrototipo.class.getDeclaredConstructor(ClasseInjecaoNormalPrototipo2.class);
		assertDoesNotThrow(() -> this.verificacao.verificar(construtorSemLoop));
	}

}
