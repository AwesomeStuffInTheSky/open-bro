package pt.ob.auth.impl;


import java.time.LocalDateTime;

import pt.asits.util.argument.assertions.Argument;
import pt.ob.auth.AuthenticationToken;


public final class JWTAuthenticationToken implements AuthenticationToken {


	private final LocalDateTime issueTime;
	private final LocalDateTime expirationTime;
	private final String jwtString;


	public JWTAuthenticationToken( LocalDateTime issueTime, LocalDateTime expirationTime, String jwtString ) {
		Argument.assertNotNull( issueTime, "issueTime" );
		Argument.assertNotNull( expirationTime, "expirationTime" );
		Argument.assertNotEmpty( jwtString, "jwtString" );
		this.issueTime = issueTime;
		this.expirationTime = expirationTime;
		this.jwtString = jwtString;
	}


	@Override
	public boolean isValid() {
		return this.expirationTime.isBefore( LocalDateTime.now() ) == false;
	}


	@Override
	public boolean isExpired() {
		return this.expirationTime.isBefore( LocalDateTime.now() );
	}


	public LocalDateTime getIssueTime() {
		return this.issueTime;
	}


	@Override
	public LocalDateTime getExpirationTime() {
		return this.expirationTime;
	}


	@Override
	public String getAsString() {
		return this.jwtString;
	}

}
