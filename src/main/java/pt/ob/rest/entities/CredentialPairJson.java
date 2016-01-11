package pt.ob.rest.entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import pt.ob.util.validators.NotNullArgumentValidator;


public class CredentialPairJson {


	@JsonProperty( "username" )
	private final String username;

	@JsonProperty( "password" )
	private final String password;


	@JsonCreator
	public CredentialPairJson( @JsonProperty( "username" ) String username, @JsonProperty( "password" ) String password ) {
		NotNullArgumentValidator.INSTANCE.validate( username, "username", password, "password" );
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

}
