package pt.ob.data.repositories;


import pt.ob.data.entities.UserEntity;
import pt.ob.data.exceptions.EntityNotFoundException;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;
import pt.ob.util.validators.NotNullArgumentValidator;


public final class UserRepositoryImpl implements UserRepository {


	private final UserMongoRepository userMongoRepository;


	public UserRepositoryImpl( UserMongoRepository userMongoRepository ) {
		this.userMongoRepository = userMongoRepository;
	}


	@Override
	public UserEntity getUserWithUserId( String userId ) throws EntityNotFoundException {
		NotEmptyStringArgumentValidator.INSTANCE.validate( userId, "userId" );
		UserEntity user = this.userMongoRepository.findByUserId( userId );
		if( user == null )
			throw new EntityNotFoundException();
		else
			return user;
	}


	@Override
	public UserEntity getUserWithUsername( String username ) throws EntityNotFoundException {
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username" );
		UserEntity user = this.userMongoRepository.findByUsername( username );
		if( user == null )
			throw new EntityNotFoundException();
		else
			return user;
	}


	@Override
	public boolean existsWithUsername( String username ) {
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username" );
		return this.userMongoRepository.existsByUsername( username );
	}


	@Override
	public UserEntity save( UserEntity user ) {
		NotNullArgumentValidator.INSTANCE.validate( user, "user" );
		return this.userMongoRepository.save( user );
	}

}
