package pt.ob.rest.entities;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pt.asits.util.argument.assertions.Argument;
import pt.ob.util.json.LocalDateTimeJsonSerializer;


public final class AuthenticationTokenJson {


	@JsonProperty( "token" )
	private final String token;

	@JsonProperty( "expirationTime" )
	@JsonSerialize( using = LocalDateTimeJsonSerializer.class )
	private final LocalDateTime expirationTime;


	@JsonCreator
	public AuthenticationTokenJson( @JsonProperty( "token" ) String token,
			@JsonProperty( "expirationTime" ) LocalDateTime expirationTime) {
		Argument.assertNotEmpty( token, "token" );
		Argument.assertNotNull( expirationTime, "expirationTime" );

		this.token = token;
		this.expirationTime = expirationTime;
	}


	public String getToken() {
		return this.token;
	}


	public LocalDateTime getExpirationTime() {
		return this.expirationTime;
	}

}
