package br.com.efigueredo.container.anotacao;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <h4>Anotação que indica que uma classe atuará como uma configuração de
 * depêndencia para o container ioc.</h4>
 * 
 * @author Emanoel
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ConfiguracaoDependencia {

}
