package fr.ippon.springmvc.test.util;

import org.springframework.ui.ModelMap;
import org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.ViewRendererServlet;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

/**
 * This class contains utility methods in order to manipulate portlet MVC objects
 *
 * @author afillatre@ippon.fr
 * @since 15/10/14
 */
public class MVCUtil {

    /**
     * Gets the model from a render request
     * @param renderRequest the renderRequest used with he dispatcher
     * @return the Spring implicit model
     */
    public static ModelMap getModel(RenderRequest renderRequest) {
        return (ModelMap) renderRequest.getAttribute(ViewRendererServlet.MODEL_ATTRIBUTE);
    }

    /**
     * Gets the model from an action request. The model is only available if
     * {@link javax.portlet.ActionResponse#sendRedirect(String)} method has not been call.
     *
     * @param actionRequest the actionRequest used with he dispatcher
     * @return the Spring implicit model or null if {@link javax.portlet.ActionResponse#sendRedirect(String)} has already been called
     */
    public static ModelMap getModel(ActionRequest actionRequest) {
        return (ModelMap) actionRequest.getPortletSession().getAttribute(AnnotationMethodHandlerAdapter.IMPLICIT_MODEL_SESSION_ATTRIBUTE);
    }
}
