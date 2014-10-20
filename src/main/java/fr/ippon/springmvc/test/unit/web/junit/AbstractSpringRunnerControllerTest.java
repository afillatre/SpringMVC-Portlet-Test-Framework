package fr.ippon.springmvc.test.unit.web.junit;

import fr.ippon.springmvc.test.unit.web.AbstractBaseControllerTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for portlet MVC tests. You can extend it or copy its code into your test class.<br/>
 *
 * This class is uses the Spring JUnit runner to bootstrap its context. It prevents you from using another runner
 * alongside with this class. If you need you own runner (or another one), you may want to inherit from
 * {@link fr.ippon.springmvc.test.unit.web.junit.AbstractRunnerFreeControllerTest} class instead.
 *
 * @author afillatre@ippon.fr
 * @since 14/10/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSpringRunnerControllerTest extends AbstractBaseControllerTest {
}
