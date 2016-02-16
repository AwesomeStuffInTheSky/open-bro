package pt.ob.rest.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pt.asits.util.argument.assertions.Argument;


public class CredentialPairJson {


	@JsonProperty( "username" )
	private final String username;

	@JsonProperty( "password" )
	private final String password;


	@JsonCreator
	public CredentialPairJson( @JsonProperty( "username" ) String username, @JsonProperty( "password" ) String password) {
		Argument.assertNotEmpty( username, "username" );
		Argument.assertNotEmpty( password, "password" );

		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return this.username;
	}


	public String getPassword() {
		return this.password;
	}

}
