/**
 * 
 */
package com.xinyunlian.jinfu.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @author congzhou
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SignatureIgnore {

}
