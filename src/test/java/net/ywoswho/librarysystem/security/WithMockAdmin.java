package net.ywoswho.librarysystem.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockJwt(roles = "ADMIN")
public @interface WithMockAdmin {}
