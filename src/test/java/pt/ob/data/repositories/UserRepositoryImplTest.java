package pt.ob.data.repositories;


import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import pt.ob.data.entities.UserEntity;
import pt.ob.data.exceptions.EntityNotFoundException;
import pt.ob.data.repositories.mocks.UserMongoRepositoryMock;


public final class UserRepositoryImplTest {
	
	
	@Rule
	public final ExpectedException expectedException;
	
	private final UserMongoRepositoryMock userMongoRepositoryMock;
	private final UserRepositoryImpl userRepositoryImpl;
	
	
	public UserRepositoryImplTest() {
		this.expectedException = ExpectedException.none();
		this.userMongoRepositoryMock = new UserMongoRepositoryMock();
		this.userRepositoryImpl = new UserRepositoryImpl( userMongoRepositoryMock );
	}
	
	
	@Before
	public void before() {
		this.userMongoRepositoryMock.deleteAll();
		
		// Password "xptoxpto"
		UserEntity entity1 = new UserEntity( "02daf7df-2be0-4f7d-8d3b-814b249f4b67", "johndoe",
				"$2a$12$Ec81uyNLWtPve8Gop89unOfxbHXHIGHnvIwBWT9LlvMAz04R9bNS.", LocalDateTime.now(), true );
		// Password "test123"
		UserEntity entity2 = new UserEntity( "252a75ad-0080-4ae4-a7fe-80752102386a", "john.doe@gmail.com",
				"$2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC", LocalDateTime.now(), true );
		// Password "_!SecurePasswordFromHell!_$$$"
		UserEntity entity3 = new UserEntity( "94b9bf24-6a09-40f8-a7bf-69664aee9cc6", "jdoe",
				"$2a$12$bfL/jT3jFVOMrL.2ZJ2ED.DTXPM39aI5RsJJh9SthEtJLVPMEE4o6", LocalDateTime.now(), true );

		this.userMongoRepositoryMock.save( entity1 );
		this.userMongoRepositoryMock.save( entity2 );
		this.userMongoRepositoryMock.save( entity3 );
	}
	
	
	@Test
	public void getUserWithUserIdUsingAValidAndExistantIdTest() {
		UserEntity entity = this.userRepositoryImpl.getUserWithUserId( "02daf7df-2be0-4f7d-8d3b-814b249f4b67" );
		Assert.assertEquals( "02daf7df-2be0-4f7d-8d3b-814b249f4b67", entity.getUserId() );
		Assert.assertEquals( "johndoe", entity.getUsername() );
		Assert.assertEquals( "$2a$12$Ec81uyNLWtPve8Gop89unOfxbHXHIGHnvIwBWT9LlvMAz04R9bNS.", entity.getPasswordDigest() );
	}


	@Test
	public void getUserWithUserIdUsingAValidButNonExistantIdTest() {
		this.expectedException.expect( EntityNotFoundException.class );
		this.userRepositoryImpl.getUserWithUserId( "895637b0-0631-4509-b14a-3204701b7d03" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUserIdUsingANullArgumentTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.userRepositoryImpl.getUserWithUserId( null );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUserIdUsingAEmptyStringArgumentTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.userRepositoryImpl.getUserWithUserId( "" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUserIdUsingAnInvalidIdTest() {
		// At the datastore level, the userId is just a String, so it should treat it as such
		// and not as an UUID, for the invalid format shouldn't matter
		this.expectedException.expect( EntityNotFoundException.class );
		this.userRepositoryImpl.getUserWithUserId( "abc-123-bca" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUsernameUsingAValidExistantUsernameTest() {
		UserEntity entity = this.userRepositoryImpl.getUserWithUsername( "john.doe@gmail.com" );
		Assert.assertEquals( "252a75ad-0080-4ae4-a7fe-80752102386a", entity.getUserId() );
		Assert.assertEquals( "john.doe@gmail.com", entity.getUsername() );
		Assert.assertEquals( "$2a$12$5LX7hzjWXYGHBaZug0VyXe/W.rgYjzt522djwgznRH/tCrkVH3ALC", entity.getPasswordDigest() );
	}
	
	
	@Test
	public void getUserWithUsernameUsingANonExistantUsernameTest() {
		this.expectedException.expect( EntityNotFoundException.class );
		this.userRepositoryImpl.getUserWithUsername( "johnnotdoe" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUsernameUsingANullArgumentTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.userRepositoryImpl.getUserWithUsername( null );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void getUserWithUsernameUsingAnEmptyStringAsArgumentTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.userRepositoryImpl.getUserWithUsername( "" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void existsWithUsernameUsingAValidExistantUsernameTest() {
		boolean exists = this.userRepositoryImpl.existsWithUsername( "john.doe@gmail.com" );
		Assert.assertTrue( exists );
	}

}
