Spring MVC Portlet Test Framework
=======================================

## Project's goal
This project provides some classes that will make it possible to test Spring MVC Portlet controllers

## Very quick start
In order to use this test framework
1. add the _springmvc-portlet-test-XX.jar_ in your project classpath
2. create a simple JUnit Test
3. inherit from _fr.ippon.springmvc.test.unit.web.junit.AbstractRunnerFreeControllerTest_ or _fr.ippon.springmvc.test.unit.web.junit.AbstractSpringRunnerControllerTest_
4. add the following annotations above your test class (see § Default configuration) :
```
@MockWebApplication(name="portlet-with-tests")
@ContextConfiguration(loader = MockWebApplicationContextLoader.class)
```
5. run your tests
 
## Default configuration
The `@ContextConfiguration` annotation normally takes an array of _locations_, the places where your spring configuration files are. By default, the Test framework assumes you use a Maven-type project, with configuration files located in the _src/main/webapp/WEB-INF_ folder, as shown below :

```
|-src
    |-main
        |-src
        |-webapp
            |-WEB-INF
                |-applicationContext.xml
                |-<portlet_name_with_spaces>-portlet.xml
                |-web.xml
```

The _<portlet_name_with_spaces>_ name comes from the value of the `@MockWebApplication` annotation

If you have a different configuration, then use the annotations like the following :
```
@MockWebApplication(name="portlet-with-tests")
@ContextConfiguration(locations = {
        "file:path_to_a_config_file",
        "classpath:path_to_another_config_file",
        etc.
    },
    loader = MockWebApplicationContextLoader.class
)
```
## Project example
You can look at the sources of the following project in order to see the test API in use : 