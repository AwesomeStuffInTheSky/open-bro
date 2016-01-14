package pt.ob.rest.filters;


import java.io.IOException;
import java.time.LocalDateTime;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import moks.ContainerRequestContextMock;
import pt.ob.auth.impl.handlers.UUIDUserHandler;
import pt.ob.data.entities.UserEntity;
import pt.ob.data.repositories.UserRepositoryImpl;
import pt.ob.data.repositories.mocks.UserMongoRepositoryMock;
import pt.ob.security.impl.BCryptPasswordDigester;
import pt.ob.security.impl.EmailUsernameFormatValidator;
import pt.ob.security.impl.WeakPasswordFormatValidator;


public final class AuthenticationFilterTest {
	
	
	/**
	 * header:
	 *    "typ": "JWT"
	 *    "alg": "HS512"
	 * body:
	 *    "sub": "252a75ad-0080-4ae4-a7fe-80752102386a"
	 *    "iat": 1452707769 - Wed Jan 13 2016 17:56:09 GMT+0000 (GMT Standard Time)
	 *    "exp": 1610560569 - Wed Jan 13 2021 17:56:09 GMT+0000 (GMT Standard Time)
	 * 
	 * secret: $2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC
	 */
	private static final String JWT_TOKEN_VALID = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNTJhNzVhZC0wMDgwLTRhZTQtYTdmZS04MDc1MjEwMjM4NmEiLCJpYXQiOjE0NTI3MDk4MzQsImV4cCI6MTYxMDU2MjYzNH0.LhdyaZ9j_f1li8e_oB8ZjuCsTCvh2WERbJnhkafx4V4CidQjQNQx3EKbZao7IjVfGaY-lw5VTbEiNz4ecKJSrA";

	/**
	 * header:
	 *    "typ": "JWT"
	 *    "alg": "HS512"
	 * body:
	 *    "sub": "252a75ad-0080-4ae4-a7fe-80752102386a"
	 *    "iat": 1452720389 - Wed Jan 13 2016 21:26:29 GMT+0000 (GMT Standard Time)
	 *    "exp": 1452720689 - Wed Jan 13 2016 21:31:29 GMT+0000 (GMT Standard Time)
	 * 
	 * secret: $2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC
	 */
	private static final String JWT_TOKEN_EXPIRED = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNTJhNzVhZC0wMDgwLTRhZTQtYTdmZS04MDc1MjEwMjM4NmEiLCJpYXQiOjE0NTI3MjAzODksImV4cCI6MTQ1MjcyMDY4OX0.O1TU2YBqrefuFYSn8Zhc784F-hOEgmZBtkSs8ym4kPb0UDxds_74zYQ_7d4C8nZLfQfeGJuslyJ18rNuJk-HqA";

	/**
	 * header:
	 *    "typ": "JWT"
	 *    "alg": "none"
	 * body:
	 *    "sub": "252a75ad-0080-4ae4-a7fe-80752102386a"
	 *    "iat": 1452767562 - Thu Jan 14 2016 10:32:42 GMT+0000 (GMT Standard Time)
	 *    "exp": 1610620362 - Thu Jan 14 2021 10:32:42 GMT+0000 (GMT Standard Time)
	 */
	private static final String JWT_TOKEN_UNSIGNED = "eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0.eyJzdWIiOiIyNTJhNzVhZC0wMDgwLTRhZTQtYTdmZS04MDc1MjEwMjM4NmEiLCJpYXQiOjE0NTI3Njc1NjIsImV4cCI6MTYxMDYyMDM2Mn0.";

	/**
	 * header:
	 *   "typ": "JWT"
	 *   "alg": "HS512"
	 * body:
	 *   "sub": "252a75ad-0080-4ae4-a7fe-80752102386a"
	 *   "iat": 1452768962 - Thu Jan 14 2016 10:56:02 GMT+0000 (GMT Standard Time)
	 *   "exp": 1610621762 - Thu Jan 14 2021 10:56:02 GMT+0000 (GMT Standard Time)
	 */
	private static final String JWT_TOKEN_INVALID_SIGNATURE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNTJhNzVhZC0wMDgwLTRhZTQtYTdmZS04MDc1MjEwMjM4NmEiLCJpYXQiOjE0NTI3Njg5NjIsImV4cCI6MTYxMDYyMTc2Mn0.cUr7YfGrnxEp2Ws_AcWSoSGNlWgshlOhWZ_EkUeFtFyuLlXJR4I4ZpM6Jz923tnnjanFklX1qB4I-PL6X9y4tw";

	/**
	 * header:
	 *    "typ": "JWT"
	 *    "alg": "HS512"
	 * body:
	 *    "sub": "252a75ad-0080-4ae4-a7fe-80752102386a"
	 *    "iat": 1610621974 - Thu Jan 14 2021 10:59:34 GMT+0000 (GMT Standard Time)
	 *    "exp": 1610622274 - Thu Jan 14 2021 11:04:34 GMT+0000 (GMT Standard Time)
	 * 
	 * secret: $2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC
	 */
	private static final String JWT_TOKEN_FUTURE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNTJhNzVhZC0wMDgwLTRhZTQtYTdmZS04MDc1MjEwMjM4NmEiLCJpYXQiOjE2MTA2MjE5NzQsImV4cCI6MTYxMDYyMjI3NH0.rQP9Yw0iGkM_hW23JiJscDEUZby6Mn2KTjvqU6LiYyLO5TfwrLy4SHgvf1hcYfC-HSaACWUplCdRMGW9GQws2w";
	
	// TODO Test using an inexistent user
	// TODO Test using some combination of problems
	// TODO Test using tokens with missing fields
	
	@Rule
	public final ExpectedException expectedException;

	private final UserMongoRepositoryMock userMongoRepository;
	private final AuthenticationFilter authenticationFilter;

	private ContainerRequestContextMock containerRequestContext;


	public AuthenticationFilterTest() {
		this.expectedException = ExpectedException.none();

		this.userMongoRepository = new UserMongoRepositoryMock();
		UserRepositoryImpl userRepository = new UserRepositoryImpl( userMongoRepository );
		EmailUsernameFormatValidator usernameFormatValidator = new EmailUsernameFormatValidator();
		WeakPasswordFormatValidator passwordFormatValidator = new WeakPasswordFormatValidator();
		BCryptPasswordDigester passwordDigester = new BCryptPasswordDigester( 10 );
		UUIDUserHandler userHandler = new UUIDUserHandler( userRepository, usernameFormatValidator, passwordFormatValidator,
				passwordDigester );
		this.authenticationFilter = new AuthenticationFilter( userHandler );
	}


	@Before
	public void before() {
		this.userMongoRepository.deleteAll();
		UserEntity user = new UserEntity( "252a75ad-0080-4ae4-a7fe-80752102386a", "john.doe@gmail.com",
				"$2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC", LocalDateTime.now(), true );
		this.userMongoRepository.save( user );

		this.containerRequestContext = new ContainerRequestContextMock();
	}


	@Test
	public void filterUsingAValidTokenTest() throws IOException {
		this.containerRequestContext.setAuthToken( JWT_TOKEN_VALID );
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 1.", 1, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNotNull( "The userId shouldn't be null.", userId );
		
		Assert.assertFalse( "The filter shouldn't have aborted the filter chain.", this.containerRequestContext.isAbort() );
		Assert.assertNull( "The abort response should be null.", this.containerRequestContext.getAbort() );

		Assert.assertTrue( "The userId type should be of String.", userId instanceof String );
		Assert.assertEquals( "The userId doesn't match the correct one.", "252a75ad-0080-4ae4-a7fe-80752102386a", userId );
	}


	@Test
	public void filterUsingAnExpiredTokenTest() throws IOException {
		this.containerRequestContext.setAuthToken( JWT_TOKEN_EXPIRED );
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 0.", 0, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNull( "The userId should be null.", userId );
		
		Assert.assertTrue( "The filter should have aborted the filter chain.", this.containerRequestContext.isAbort() );
		
		Response response = this.containerRequestContext.getAbort();
		Assert.assertNotNull( "The abort response shouldn't be null.", response );
		Assert.assertEquals( "The status should be a 401.", Status.UNAUTHORIZED, response.getStatusInfo() );
	}


	@Test
	public void filterWithoutTokenTest() throws IOException {
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 0.", 0, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNull( "The userId should be null.", userId );
		
		Assert.assertFalse( "The filter shouldn't have aborted the filter chain.", this.containerRequestContext.isAbort() );
		Assert.assertNull( "The abort response should be null.", this.containerRequestContext.getAbort() );
	}


	@Test
	public void filterUsingANotSignedTokenTest() throws IOException {
		this.containerRequestContext.setAuthToken( JWT_TOKEN_UNSIGNED );
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 0.", 0, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNull( "The userId should be null.", userId );
		
		Assert.assertTrue( "The filter should have aborted the filter chain.", this.containerRequestContext.isAbort() );
		
		Response response = this.containerRequestContext.getAbort();
		Assert.assertNotNull( "The abort response shouldn't be null.", response );
		Assert.assertEquals( "The status should be a 401.", Status.UNAUTHORIZED, response.getStatusInfo() );
	}


	@Test
	public void filterUsingATokenWithAnInvalidSignatureTest() throws IOException {
		this.containerRequestContext.setAuthToken( JWT_TOKEN_INVALID_SIGNATURE );
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 0.", 0, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNull( "The userId should be null.", userId );
		
		Assert.assertTrue( "The filter should have aborted the filter chain.", this.containerRequestContext.isAbort() );
		
		Response response = this.containerRequestContext.getAbort();
		Assert.assertNotNull( "The abort response shouldn't be null.", response );
		Assert.assertEquals( "The status should be a 401.", Status.UNAUTHORIZED, response.getStatusInfo() );
	}


	@Test
	public void filterUsingATokenWithFutureIssueTimeTest() throws IOException {
		this.containerRequestContext.setAuthToken( JWT_TOKEN_FUTURE );
		this.authenticationFilter.filter( this.containerRequestContext );

		int propertiesCount = this.containerRequestContext.getPropertyNames().size();
		Assert.assertEquals( "The properties count should be 0.", 0, propertiesCount );

		Object userId = this.containerRequestContext.getProperty( "userId" );
		Assert.assertNull( "The userId should be null.", userId );
		
		Assert.assertTrue( "The filter should have aborted the filter chain.", this.containerRequestContext.isAbort() );
		
		Response response = this.containerRequestContext.getAbort();
		Assert.assertNotNull( "The abort response shouldn't be null.", response );
		Assert.assertEquals( "The status should be a 401.", Status.UNAUTHORIZED, response.getStatusInfo() );
	}

}
