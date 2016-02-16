package pt.ob.rest.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pt.asits.util.argument.assertions.Argument;


public final class UserJson {


	@JsonProperty( "id" )
	private final String id;

	@JsonProperty( value = "username" )
	private final String username;


	@JsonCreator
	public UserJson( @JsonProperty( "id" ) String id, @JsonProperty( "username" ) String username) {
		Argument.assertNotEmpty( id, "id" );
		Argument.assertNotEmpty( username, "username" );

		this.id = id;
		this.username = username;
	}


	public String getId() {
		return this.id;
	}


	public String getUsername() {
		return this.username;
	}

}
