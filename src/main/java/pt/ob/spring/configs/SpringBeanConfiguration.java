package pt.ob.spring.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pt.ob.auth.AuthenticationHandler;
import pt.ob.auth.UserHandler;
import pt.ob.auth.impl.handlers.JWTAuthenticationHandler;
import pt.ob.auth.impl.handlers.UUIDUserHandler;
import pt.ob.data.repositories.UserMongoRepository;
import pt.ob.data.repositories.UserRepository;
import pt.ob.data.repositories.UserRepositoryImpl;
import pt.ob.rest.filters.AuthenticationFilter;
import pt.ob.rest.filters.AuthorizationFilter;
import pt.ob.rest.resources.AuthenticationResource;
import pt.ob.rest.resources.UserResource;
import pt.ob.security.PasswordDigester;
import pt.ob.security.PasswordFormatValidator;
import pt.ob.security.UsernameFormatValidator;
import pt.ob.security.impl.BCryptPasswordDigester;
import pt.ob.security.impl.EmailUsernameFormatValidator;
import pt.ob.security.impl.WeakPasswordFormatValidator;


@Configuration
public class SpringBeanConfiguration {


	@Bean
	public AuthenticationFilter getAuthenticationFilter( UserHandler userHandler ) {
		return new AuthenticationFilter( userHandler );
	}
	
	
	@Bean
	public AuthorizationFilter getAuthorizationFilter() {
		return new AuthorizationFilter();
	}


	@Bean
	public UserRepository getUserRepository( UserMongoRepository userMongoRepository ) {
		return new UserRepositoryImpl( userMongoRepository );
	}


	@Bean
	public UserHandler getUserHandler( UserRepository userRepository, UsernameFormatValidator usernameFormatValidator,
			PasswordFormatValidator passwordFormatValidator, PasswordDigester passwordDigester ) {
		return new UUIDUserHandler( userRepository, usernameFormatValidator, passwordFormatValidator, passwordDigester );
	}


	@Bean
	public AuthenticationHandler getAuthenticationHandler( UserHandler userHandler, PasswordDigester passwordDigester ) {
		return new JWTAuthenticationHandler( userHandler, passwordDigester );
	}


	@Bean
	public UserResource getUserResource( UserHandler userHandler ) {
		return new UserResource( userHandler );
	}


	@Bean
	public AuthenticationResource getAuthenticationResource( AuthenticationHandler authenticationHandler ) {
		return new AuthenticationResource( authenticationHandler );
	}


	@Bean
	public PasswordDigester getPasswordDigester() {
		return new BCryptPasswordDigester( 10 ); // FIXME This value should not be fixed
	}


	@Bean
	// @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE )
	public PasswordFormatValidator getPasswordValidator() {
		// ConfigurableBeanFactory
		// WebApplicationContext
		return new WeakPasswordFormatValidator();
	}


	@Bean
	public UsernameFormatValidator getUsernameValidator() {
		return new EmailUsernameFormatValidator();
	}

}
