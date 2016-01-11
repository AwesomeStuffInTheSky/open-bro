package pt.ob.data.entities;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import pt.ob.util.validators.NotEmptyStringArgumentValidator;
import pt.ob.util.validators.NotNullArgumentValidator;


@Document( collection = "users" )
public final class UserEntity {


	@Id
	@Field( "id" )
	private ObjectId id;

	@Field( value = "userId" )
	@Indexed( name = "userIdUniqueIndex", unique = true )
	private String userId;

	@Field( value = "username" )
	@Indexed( name = "usernameUniqueIndex", unique = true )
	private String username;

	@Field( value = "passwordDigest" )
	private String passwordDigest;

	@Field( value = "timeCreated" )
	private LocalDateTime timeCreated;

	@Field( value = "enabled" )
	private boolean enabled;


	public UserEntity( String userId, String username, String passwordDigest, LocalDateTime timeCreated, boolean enabled ) {
		NotEmptyStringArgumentValidator.INSTANCE.validate( userId, "userId", username, "username", passwordDigest, "passwordDigest" );
		NotNullArgumentValidator.INSTANCE.validate( timeCreated, "timeCreated" );
		this.userId = userId;
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.timeCreated = timeCreated;
		this.enabled = enabled;
	}


	@PersistenceConstructor
	protected UserEntity( ObjectId id, String userId, String username, String passwordDigest, LocalDateTime timeCreated,
			boolean enabled ) {
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.timeCreated = timeCreated;
		this.enabled = enabled;
	}


	public ObjectId getId() {
		return this.id;
	}


	public String getUserId() {
		return this.userId;
	}


	public void setUserId( String userId ) {
		this.userId = userId;
	}


	public String getUsername() {
		return this.username;
	}


	public void setUsername( String username ) {
		this.username = username;
	}


	public String getPasswordDigest() {
		return this.passwordDigest;
	}


	public void setPasswordDigest( String passwordDigest ) {
		this.passwordDigest = passwordDigest;
	}


	public LocalDateTime getTimeCreated() {
		return timeCreated;
	}


	public void setTimeCreated( LocalDateTime timeCreated ) {
		this.timeCreated = timeCreated;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

}
