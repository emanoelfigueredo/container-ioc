package br.com.efigueredo.container.objetos.verificacao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.exception.ContainerIocException;
import br.com.efigueredo.container.exception.LoopDeInjecaoDependenciaException;
import br.com.efigueredo.container.objetos.verificacao.prototipos.ClasseInjecaoLoopPrototipo1;
import br.com.efigueredo.container.objetos.verificacao.prototipos.ClasseInjecaoLoopPrototipo2;
import br.com.efigueredo.container.objetos.verificacao.prototipos.ClasseInjecaoNormalPrototipo;
import br.com.efigueredo.container.objetos.verificacao.prototipos.ClasseInjecaoNormalPrototipo2;

@Tag("integrado")
class VerificacaoLoopInjecaoTest {

	private VerificacaoLoopInjecao verificacao;

	@BeforeEach
	void setUp() throws Exception {
		this.verificacao = new VerificacaoLoopInjecao();
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
	void deveriaLancarExcecaoLoopDeInjecao_QuandoForInseridoConstrutorQuePodeGerarLoopDeInjecao() throws ContainerIocException, NoSuchMethodException, SecurityException {
		Constructor<ClasseInjecaoLoopPrototipo1> construtorLoop = ClasseInjecaoLoopPrototipo1.class.getDeclaredConstructor(ClasseInjecaoLoopPrototipo2.class);
		assertThrows(LoopDeInjecaoDependenciaException.class, () -> this.verificacao.verificar(construtorLoop));
	}
	
	@Test
	void naoDeveriaLancarExcecao_QunadoForInseridoConstrutorQueNaoGeraLoopDeInjecao() throws ContainerIocException, NoSuchMethodException, SecurityException {
		Constructor<ClasseInjecaoNormalPrototipo> construtorSemLoop = ClasseInjecaoNormalPrototipo.class.getDeclaredConstructor(ClasseInjecaoNormalPrototipo2.class);
		assertDoesNotThrow(() -> this.verificacao.verificar(construtorSemLoop));
	}

}
