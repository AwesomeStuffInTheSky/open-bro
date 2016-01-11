package pt.ob.dropwizard.app;


import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pt.ob.rest.filters.SecurityFilter;
import pt.ob.rest.resources.AuthenticationResource;
import pt.ob.rest.resources.UserResource;


public class DropwizardApplication extends Application<Configuration> {


	public static void main( String[] args ) throws Exception {
		new DropwizardApplication().run( args );
	}


	@Override
	public void initialize( Bootstrap<Configuration> bootstrap ) {}


	@Override
	public void run( Configuration configuration, Environment environment ) {
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.setConfigLocation( "pt.ob.spring.configs" );
		environment.servlets().addServletListeners( new ContextLoaderListener( webContext ) );
		webContext.refresh();
		
		environment.jersey().register( webContext.getBean( SecurityFilter.class ) );
		environment.jersey().register( webContext.getBean( UserResource.class ) );
		environment.jersey().register( webContext.getBean( AuthenticationResource.class ) );
	}

}
