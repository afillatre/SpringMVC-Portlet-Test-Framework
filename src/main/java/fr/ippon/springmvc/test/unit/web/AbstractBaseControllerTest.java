package fr.ippon.springmvc.test.unit.web;

import fr.ippon.springmvc.test.unit.mock.MockViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.DispatcherPortlet;

/**
 * Base class for portlet MVC tests. It contains the two "portlet" objects you'll want in your tests.<br/>
 *
 * @author afillatre@ippon.fr
 * @since 14/10/14
 */
public abstract class AbstractBaseControllerTest {

    @Autowired
    protected DispatcherPortlet dispatcherPortlet;
    @Autowired
    protected MockViewResolver viewResolver;
}
