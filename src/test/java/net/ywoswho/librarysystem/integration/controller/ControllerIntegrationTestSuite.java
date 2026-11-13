package net.ywoswho.librarysystem.integration.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Controller Integration Tests Suite")
@SelectClasses({
        AuthorControllerTest.class,
        BookControllerTest.class,
        GenreControllerTest.class
})
public class ControllerIntegrationTestSuite {}
