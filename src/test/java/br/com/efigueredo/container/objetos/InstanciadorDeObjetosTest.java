package br.com.efigueredo.container.objetos;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.efigueredo.container.construtor.prototipo.ClasseConstrutorPadraoPrivadoPrototipo;
import br.com.efigueredo.container.construtor.prototipo.ClasseConstrutorParametrosPrivadooPrototipo;
import br.com.efigueredo.container.construtor.prototipo.ClasseConstrutorParametrosPublicoPrototipo;
import br.com.efigueredo.container.exception.IntanciacaoObjetoInterrompidaException;
import br.com.efigueredo.container.prototipos.ClasseConstrutorPadraoPrototipo;

@Tag("unitario")
class InstanciadorDeObjetosTest {
	
	private InstanciadorDeObjetos instanciador;

	@BeforeEach
	void setUp() throws Exception {
		this.instanciador = new InstanciadorDeObjetos();
	}

	@Test
	void deveriaRetornarUmaIntancia_PeloConstrutorPadraoPublico() throws NoSuchMethodException, SecurityException, IntanciacaoObjetoInterrompidaException {
		Constructor<?> construtorPadrao = ClasseConstrutorPadraoPrototipo.class.getConstructor();
		Object instancia = this.instanciador.intanciarPorContrutorPadrao(construtorPadrao);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrototipo);
	}
	
	@Test
	void deveriaRetornarUmaIntancia_PeloConstrutorPadraoPrivado() throws NoSuchMethodException, SecurityException, IntanciacaoObjetoInterrompidaException {
		Constructor<?> construtorPadrao = ClasseConstrutorPadraoPrivadoPrototipo.class.getDeclaredConstructor();
		Object instancia = this.instanciador.intanciarPorContrutorPadrao(construtorPadrao);
		assertTrue(instancia instanceof ClasseConstrutorPadraoPrivadoPrototipo);
	}
	
	@Test
	public void deveriaRetornarUmaInstanciaDa_ClasseDeConstrutorComParametros_Publico() throws NoSuchMethodException, SecurityException, IntanciacaoObjetoInterrompidaException {
		Constructor<?> construtorPadrao = ClasseConstrutorParametrosPublicoPrototipo.class.getDeclaredConstructor(String.class);
		Object[] parametros = {"STRING PARAMETRO"};
		Object instancia = this.instanciador.instanciarPorContrutorComParametros(construtorPadrao, parametros);
		assertTrue(instancia instanceof ClasseConstrutorParametrosPublicoPrototipo);
		ClasseConstrutorParametrosPublicoPrototipo resultado = (ClasseConstrutorParametrosPublicoPrototipo) instancia;
		assertTrue(resultado.getArg1().equals("STRING PARAMETRO"));
	}
	
	@Test
	public void deveriaRetornarUmaInstanciaDa_ClasseDeConstrutorComParametros_Privado() throws NoSuchMethodException, SecurityException, IntanciacaoObjetoInterrompidaException {
		Constructor<?> construtorPadrao = ClasseConstrutorParametrosPrivadooPrototipo.class.getDeclaredConstructor(String.class);
		Object[] parametros = {"STRING PARAMETRO"};
		Object instancia = this.instanciador.instanciarPorContrutorComParametros(construtorPadrao, parametros);
		assertTrue(instancia instanceof ClasseConstrutorParametrosPrivadooPrototipo);
		ClasseConstrutorParametrosPrivadooPrototipo resultado = (ClasseConstrutorParametrosPrivadooPrototipo) instancia;
		assertTrue(resultado.getArg1().equals("STRING PARAMETRO"));
	}

}
