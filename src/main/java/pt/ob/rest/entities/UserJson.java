package pt.ob.rest.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pt.ob.util.validators.NotNullArgumentValidator;


public final class UserJson {


	@JsonProperty( "id" )
	private final String id;

	@JsonProperty( value = "username" )
	private final String username;


	@JsonCreator
	public UserJson( @JsonProperty( "id" ) String id, @JsonProperty( "username" ) String username ) {
		NotNullArgumentValidator.INSTANCE.validate( id, "id", username, "username" );
		this.id = id;
		this.username = username;
	}


	public String getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

}
