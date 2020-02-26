
package com.ccpp.shared.core.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.inject.Scope;

import kotlin.annotation.AnnotationRetention;

/**
 * The FragmentScoped custom scoping annotation specifies that the lifespan of a dependency be
 * the same as that of a Fragment. This is used to annotate dependencies that behave like a
 * singleton within the lifespan of a Fragment
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FragmentScoped {
}
