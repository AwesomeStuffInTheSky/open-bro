package pt.ob.data.repositories;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import pt.ob.data.entities.UserEntity;


public interface UserMongoRepository extends MongoRepository<UserEntity, ObjectId> {


	public UserEntity findByUserId( String userId );


	public UserEntity findByUsername( String username );


	default public boolean existsByUsername( String username ) {
		UserEntity user = this.findByUsername( username );
		return user == null ? false : true;
	}

}
