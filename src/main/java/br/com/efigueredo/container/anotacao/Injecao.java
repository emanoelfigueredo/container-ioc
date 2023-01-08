package br.com.efigueredo.container.anotacao;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <h4>Indica que o construtor deverá ter suas depêndencias injetadas pelo container
 * ioc.</h4><br>
 * 
 * Para que não haja exceções. Deve-se ter apenas um construtor anotado.
 * 
 * @author Emanoel
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(CONSTRUCTOR)
public @interface Injecao {

}
