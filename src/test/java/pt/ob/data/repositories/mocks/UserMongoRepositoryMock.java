package pt.ob.data.repositories.mocks;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import mockit.Deencapsulation;
import pt.ob.data.entities.UserEntity;
import pt.ob.data.repositories.UserMongoRepository;


public class UserMongoRepositoryMock implements UserMongoRepository {
	
	
	private final Map<ObjectId, UserEntity> entitiesById;
	private final Map<String, UserEntity> entitiesByUserId;
	private final Map<String, UserEntity> entitiesByUsername;
	
	
	public UserMongoRepositoryMock() {
		this.entitiesById = new HashMap<>();
		this.entitiesByUserId = new HashMap<>();
		this.entitiesByUsername = new HashMap<>();
	}


	@Override
	@SuppressWarnings( "all" )
	public <S extends UserEntity> List<S> save( Iterable<S> entites ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public List<UserEntity> findAll() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public List<UserEntity> findAll( Sort sort ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	@SuppressWarnings( "all" )
	public <S extends UserEntity> S insert( S entity ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	@SuppressWarnings( "all" )
	public <S extends UserEntity> List<S> insert( Iterable<S> entities ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public Page<UserEntity> findAll( Pageable pageable ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	@SuppressWarnings( "all" )
	public <S extends UserEntity> S save( S entity ) {
		ObjectId id = new ObjectId();
		Deencapsulation.setField( entity, "id", id );
		this.entitiesById.put( id, entity );
		this.entitiesByUserId.put( entity.getUserId(), entity );
		this.entitiesByUsername.put( entity.getUsername(), entity );
		return entity;
	}


	@Override
	public UserEntity findOne( ObjectId id ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public boolean exists( ObjectId id ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public Iterable<UserEntity> findAll( Iterable<ObjectId> ids ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public long count() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void delete( ObjectId id ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void delete( UserEntity entity ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void delete( Iterable<? extends UserEntity> entities ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void deleteAll() {
		this.entitiesById.clear();
		this.entitiesByUserId.clear();
	}


	@Override
	public UserEntity findByUserId( String userId ) {
		return this.entitiesByUserId.get( userId );
		
	}


	@Override
	public UserEntity findByUsername( String username ) {
		return this.entitiesByUsername.get( username );
	}

}
