package net.ywoswho.librarysystem.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockJwt(roles = "USER")
public @interface WithMockRegularUser {}
