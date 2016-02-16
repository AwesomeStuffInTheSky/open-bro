package pt.ob.data.repositories;


import pt.asits.util.argument.assertions.Argument;
import pt.ob.data.entities.UserEntity;
import pt.ob.data.exceptions.EntityNotFoundException;


public final class UserRepositoryImpl implements UserRepository {


	private final UserMongoRepository userMongoRepository;


	public UserRepositoryImpl( UserMongoRepository userMongoRepository ) {
		this.userMongoRepository = userMongoRepository;
	}


	@Override
	public UserEntity getUserWithUserId( String userId ) throws EntityNotFoundException {
		Argument.assertNotEmpty( userId, "userId" );
		UserEntity user = this.userMongoRepository.findByUserId( userId );
		if( user == null )
			throw new EntityNotFoundException();
		else
			return user;
	}


	@Override
	public UserEntity getUserWithUsername( String username ) throws EntityNotFoundException {
		Argument.assertNotEmpty( username, "username" );
		UserEntity user = this.userMongoRepository.findByUsername( username );
		if( user == null )
			throw new EntityNotFoundException();
		else
			return user;
	}


	@Override
	public boolean existsWithUsername( String username ) {
		Argument.assertNotEmpty( username, "username" );
		return this.userMongoRepository.existsByUsername( username );
	}


	@Override
	public UserEntity save( UserEntity user ) {
		Argument.assertNotNull( user, "user" );
		return this.userMongoRepository.save( user );
	}

}
