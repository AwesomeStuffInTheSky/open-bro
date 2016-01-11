package pt.ob.auth.impl;


import java.util.UUID;

import pt.ob.auth.User;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;
import pt.ob.util.validators.NotNullArgumentValidator;


public final class UUIDUser implements User {


	private final UUID id;
	private final String username;
	private final String passwordDigest;


	public UUIDUser( UUID id, String username, String passwordDigest ) {
		NotNullArgumentValidator.INSTANCE.validate( id, "id" );
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username", passwordDigest, "passwordDigest" );
		this.id = id;
		this.username = username;
		this.passwordDigest = passwordDigest;
	}


	public UUID getId() {
		return this.id;
	}


	public String getIdAsString() {
		return this.id.toString();
	}


	public String getUsername() {
		return this.username;
	}


	public String getPasswordDigest() {
		return passwordDigest;
	}

}
