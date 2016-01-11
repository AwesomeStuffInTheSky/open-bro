package pt.ob.rest.resources;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pt.ob.auth.impl.handlers.UUIDUserHandler;
import pt.ob.data.repositories.UserRepositoryImpl;
import pt.ob.data.repositories.mocks.UserMongoRepositoryMock;
import pt.ob.rest.entities.CredentialPairJson;
import pt.ob.rest.entities.UserJson;
import pt.ob.security.impl.BCryptPasswordDigester;
import pt.ob.security.impl.EmailUsernameFormatValidator;
import pt.ob.security.impl.WeakPasswordFormatValidator;


public final class UserResourceTest {
	
	
	@Rule
	public final ExpectedException expectedException;

	private final UserMongoRepositoryMock userMongoRepository;
	private final UserRepositoryImpl userRepository;
	private final UserResource userResource;


	public UserResourceTest() {
		this.expectedException = ExpectedException.none();
		this.userMongoRepository = new UserMongoRepositoryMock();
		this.userRepository = new UserRepositoryImpl( userMongoRepository );
		EmailUsernameFormatValidator usernameFormatValidator = new EmailUsernameFormatValidator();
		WeakPasswordFormatValidator passwordFormatValidator = new WeakPasswordFormatValidator();
		BCryptPasswordDigester passwordDigester = new BCryptPasswordDigester( 10 );

		UUIDUserHandler userHandler = new UUIDUserHandler( userRepository, usernameFormatValidator, passwordFormatValidator,
				passwordDigester );
		this.userResource = new UserResource( userHandler );
	}
	
	
	@Before
	public void before() {
		this.userMongoRepository.deleteAll();
	}


	@Test
	public void createUserUsingAValidUsernameAndPasswordTest() {
		CredentialPairJson credentialPair = new CredentialPairJson( "john.doe@gmail.com", "test1234" );
		Response response = this.userResource.createUser( credentialPair );
		Assert.assertNotNull( response );
		Assert.assertEquals( Status.OK, response.getStatusInfo() );
		// Check the returned entity
		Assert.assertNotNull( response.getEntity() );
		Assert.assertEquals( UserJson.class, response.getEntity().getClass() );
	}
	
	
	@Test
	public void createUserUsingAnInvalidUsernameAndAValidPasswordTest() {
		CredentialPairJson credentialPair = new CredentialPairJson( "john.doe.gmail.com", "test1234" );
		Response response = this.userResource.createUser( credentialPair );
		Assert.assertNotNull( response );
		Assert.assertEquals( Status.BAD_REQUEST, response.getStatusInfo() );
		
		System.out.println( response.getEntity().getClass() );
	}

}
