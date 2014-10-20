package fr.ippon.springmvc.test.unit.mock;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * @author afillatre@ippon.fr
 * @since 13/10/14
 */
public class MockViewResolver implements ViewResolver {

    private String viewName;
    private MockView view;

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        this.viewName = viewName;
        this.view = new MockView();
        return view;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return view.getModel();
    }
}
