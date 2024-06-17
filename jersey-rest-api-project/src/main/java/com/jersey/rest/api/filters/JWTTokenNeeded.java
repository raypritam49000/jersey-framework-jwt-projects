package com.jersey.rest.api.filters;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@NameBinding
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface JWTTokenNeeded {

}