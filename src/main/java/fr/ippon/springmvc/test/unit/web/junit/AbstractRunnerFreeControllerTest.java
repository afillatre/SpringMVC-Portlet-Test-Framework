package fr.ippon.springmvc.test.unit.web.junit;

import fr.ippon.springmvc.test.unit.web.AbstractBaseControllerTest;
import org.junit.Before;
import org.springframework.test.context.TestContextManager;

/**
 * Base class for portlet MVC tests. You can extend it or copy its code into your test class.<br/>
 *
 * This class is "runner-free", which means it provides the capability of registering the spring beans without
 * a specific loader. Thus, you can use this class with any runner you want.<br />
 * If you want to use any of :
 * <ul>
 * <li>{@link org.junit.internal.runners.JUnit4ClassRunner}</li>
 * <li>{@link org.junit.runners.BlockJUnit4ClassRunner}</li>
 * <li>{@link org.springframework.test.context.junit4.SpringJUnit4ClassRunner}</li>
 * </ul>
 * you may want to inherit from @link fr.ippon.springmvc.test.unit.web.junit.AbstractSpringRunnerControllerTest} instead
 *
 * @author afillatre@ippon.fr
 * @since 14/10/14
 */
public abstract class AbstractRunnerFreeControllerTest extends AbstractBaseControllerTest {

    @Before
    public void setUp() throws Exception {
        new TestContextManager(getClass()).prepareTestInstance(this);
    }
}
