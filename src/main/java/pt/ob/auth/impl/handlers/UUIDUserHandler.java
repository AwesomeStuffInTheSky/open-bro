package pt.ob.auth.impl.handlers;


import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import pt.ob.auth.User;
import pt.ob.auth.UserHandler;
import pt.ob.auth.exceptions.InvalidPasswordException;
import pt.ob.auth.exceptions.InvalidUserIdException;
import pt.ob.auth.exceptions.InvalidUsernameException;
import pt.ob.auth.exceptions.UsernameAlreadyExistsException;
import pt.ob.auth.impl.UUIDUser;
import pt.ob.data.entities.UserEntity;
import pt.ob.data.exceptions.UserNotFoundException;
import pt.ob.data.repositories.UserRepository;
import pt.ob.security.PasswordDigester;
import pt.ob.security.PasswordFormatValidator;
import pt.ob.security.UsernameFormatValidator;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;
import pt.ob.util.validators.NotNullArgumentValidator;


public final class UUIDUserHandler implements UserHandler {


	private final UserRepository userRepository;
	private final UsernameFormatValidator usernameFormatValidator;
	private final PasswordFormatValidator passwordFormatValidator;
	private final PasswordDigester passwordDigester;


	public UUIDUserHandler( UserRepository userRepository, UsernameFormatValidator usernameFormatValidator,
			PasswordFormatValidator passwordFormatValidator, PasswordDigester passwordDigester ) {
		NotNullArgumentValidator.INSTANCE.validate( userRepository, "userRepository", usernameFormatValidator,
				"usernameFormatValidator", passwordFormatValidator, "passwordFormatValidator" );
		NotNullArgumentValidator.INSTANCE.validate( passwordDigester, "passwordDigester" );
		this.userRepository = userRepository;
		this.usernameFormatValidator = usernameFormatValidator;
		this.passwordFormatValidator = passwordFormatValidator;
		this.passwordDigester = passwordDigester;
	}


	@Override
	public User createUser( String username, String password )
			throws InvalidUsernameException, InvalidPasswordException, UsernameAlreadyExistsException {
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username", password, "password" );

		boolean validUsernameFormat = this.usernameFormatValidator.isValid( username );
		if( validUsernameFormat == false )
			throw new InvalidUsernameException();
		boolean validPasswordFormat = this.passwordFormatValidator.isValid( password, username );
		if( validPasswordFormat == false )
			throw new InvalidPasswordException();

		String passwordDigest = this.passwordDigester.digest( password );

		// FIXME The userId shouldn't be created this way, because it should be unique across all users.
		UUID userId = UUID.randomUUID();

		UserEntity entity = new UserEntity( userId.toString(), username, passwordDigest, LocalDateTime.now(), true );
		entity = this.userRepository.save( entity );

		User user = new UUIDUser( UUID.fromString( entity.getUserId() ), entity.getUsername(), entity.getPasswordDigest() );
		return user;
	}


	@Override
	public User getUserWithUsername( String username ) throws UserNotFoundException {
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username" );

		try {
			UserEntity entity = this.userRepository.getUserWithUsername( username );
			UUIDUser user = new UUIDUser( UUID.fromString( entity.getUserId() ), entity.getUsername(),
					entity.getPasswordDigest() );
			return user;
		}
		catch( EntityNotFoundException enfe ) {
			throw new UserNotFoundException();
		}
	}


	@Override
	public User getUserWithId( String userId ) throws InvalidUserIdException, UserNotFoundException {
		NotEmptyStringArgumentValidator.INSTANCE.validate( userId, "userId" );

		try {
			UUID.fromString( userId );
		}
		catch( IllegalArgumentException iae ) {
			throw new InvalidUserIdException( "The userId is in an invalid UUID format." );
		}

		try {
			UserEntity entity = this.userRepository.getUserWithUserId( userId );
			UUIDUser user = new UUIDUser( UUID.fromString( entity.getUserId() ), entity.getUsername(),
					entity.getPasswordDigest() );
			return user;
		}
		catch( EntityNotFoundException enfe ) {
			throw new UserNotFoundException();
		}
	}

}
