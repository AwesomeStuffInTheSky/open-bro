package pt.ob.auth.impl;


import java.time.LocalDateTime;

import pt.ob.auth.AuthenticationToken;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;
import pt.ob.util.validators.NotNullArgumentValidator;


public final class JWTAuthenticationToken implements AuthenticationToken {


	private final LocalDateTime issueTime;
	private final LocalDateTime expirationTime;
	private final String jwtString;


	public JWTAuthenticationToken( LocalDateTime issueTime, LocalDateTime expirationTime, String jwtString ) {
		NotNullArgumentValidator.INSTANCE.validate( issueTime, "issueTime", expirationTime, "expirationTime" );
		NotEmptyStringArgumentValidator.INSTANCE.validate( jwtString, "jwtString" );
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
