package fr.ippon.springmvc.test.unit.mock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.portlet.DispatcherPortlet;
import org.springframework.web.portlet.context.XmlPortletApplicationContext;
import org.springframework.web.servlet.ViewResolver;

/**
 * A Spring {@link org.springframework.test.context.ContextLoader} that establishes a mock Portlet environment
 * and {@link org.springframework.web.context.WebApplicationContext} so that Spring Portlet MVC stacks can be
 * tested from within JUnit.
 *
 * @author afillatre@ippon.fr
 * @since 13/10/14
 */
public class MockWebApplicationContextLoader extends AbstractContextLoader {
    /**
     * The configuration defined in the {@link MockWebApplication} annotation.
     */
    private MockWebApplication configuration;

    public ApplicationContext loadContext(String... locations) throws Exception {
        // Establish the portlet context and config based on the test class's MockWebApplication annotation.
        final MockPortletContext portletContext = new MockPortletContext(configuration.webapp(), new FileSystemResourceLoader());
        final MockPortletConfig portletConfig = new MockPortletConfig(portletContext, configuration.name());

        // Create a WebApplicationContext and initialize it with the xml and portlet configuration.
        final XmlPortletApplicationContext portletApplicationContext = new XmlPortletApplicationContext();
        portletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, portletApplicationContext);
        portletApplicationContext.setPortletConfig(portletConfig);
        portletApplicationContext.setConfigLocations(locations);

        // Create a DispatcherPortlet that uses the previously established WebApplicationContext.
        final DispatcherPortlet dispatcherPortlet = new DispatcherPortlet() {
            @Override
            protected WebApplicationContext createPortletApplicationContext(ApplicationContext parent) {
                return portletApplicationContext;
            }
        };

        final ViewResolver viewResolver = new MockViewResolver();

        // Add the DispatcherPortlet (and anything else you want) to the context.
        // Note: this doesn't happen until refresh is called below.
        portletApplicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                beanFactory.registerSingleton(DispatcherPortlet.class.getName(), dispatcherPortlet);
                // Register any other beans here, including a ViewResolver if you are using JSPs.
                beanFactory.registerSingleton(ViewResolver.class.getName(), viewResolver);
            }
        });

        // Have the context notify the portlet every time it is refreshed.
        portletApplicationContext.addApplicationListener(new SourceFilteringListener(portletApplicationContext,
                new ApplicationListener<ContextRefreshedEvent>() {
                    public void onApplicationEvent(ContextRefreshedEvent event) {
                        dispatcherPortlet.onApplicationEvent(event);
                    }
                }));

        // Prepare the context.
        portletApplicationContext.refresh();
        portletApplicationContext.registerShutdownHook();

        // Initialize the portlet.
        if (locations.length == 0) {
            dispatcherPortlet.setContextConfigLocation(getDefaultConfigurationFilePath("applicationContext.xml"));
        }
        dispatcherPortlet.init(portletConfig);

        return portletApplicationContext;
    }

    /**
     * One of these two methods will get called before {@link #loadContext(String...)}. We just use this chance to extract the configuration.
     */
    @Override
    protected String[] generateDefaultLocations(Class<?> clazz) {
            extractConfiguration(clazz);
        String defaultPortletConfigurationFileName = getDefaultPortletConfigurationFileName();
        return StringUtils.isNotBlank(defaultPortletConfigurationFileName) ?
                new String[] { getDefaultPortletConfigurationFileName()} :
                super.generateDefaultLocations(clazz);
    }

    /**
     * One of these two methods will get called before {@link #loadContext(String...)}. We just use this chance to extract the configuration.
     */
    @Override
    protected String[] modifyLocations(Class<?> clazz, String... locations) {
        extractConfiguration(clazz);
        return super.modifyLocations(clazz, locations);
    }

    private void extractConfiguration(Class<?> clazz) {
        configuration = AnnotationUtils.findAnnotation(clazz, MockWebApplication.class);
        if (configuration == null)
            throw new IllegalArgumentException("Test class " + clazz.getName() + " must be annotated @MockWebApplication.");
    }

    @Override
    protected String getResourceSuffix() {
        return "-portlet.xml";
    }

    @Override
    public ApplicationContext loadContext(MergedContextConfiguration mergedContextConfiguration) throws Exception {
        return loadContext(mergedContextConfiguration.getLocations());
    }

    private String getDefaultPortletConfigurationFileName() {
        String portletName = this.configuration.name();
        if (StringUtils.isBlank(portletName)) {
            return null;
        }

        String normalizedPortletName = portletName.trim().replaceAll("-", "").toLowerCase();
        return getDefaultConfigurationFilePath(normalizedPortletName + getResourceSuffix());
    }

    private String getDefaultConfigurationFilePath(String name) {
        return "file:" + this.configuration.webapp() + "/WEB-INF/" + name;
    }
}