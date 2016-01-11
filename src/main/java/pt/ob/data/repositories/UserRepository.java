package pt.ob.data.repositories;


import pt.ob.data.entities.UserEntity;
import pt.ob.data.exceptions.EntityNotFoundException;


public interface UserRepository {


	public UserEntity getUserWithUserId( String userId ) throws EntityNotFoundException;


	public UserEntity getUserWithUsername( String username ) throws EntityNotFoundException;


	public boolean existsWithUsername( String username );


	public UserEntity save( UserEntity user );

}
