package pt.ob.auth.impl.handlers;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pt.asits.util.argument.assertions.Argument;
import pt.ob.auth.AuthenticationHandler;
import pt.ob.auth.AuthenticationToken;
import pt.ob.auth.User;
import pt.ob.auth.UserHandler;
import pt.ob.auth.exceptions.InvalidUsernamePasswordException;
import pt.ob.auth.impl.JWTAuthenticationToken;
import pt.ob.data.exceptions.UserNotFoundException;
import pt.ob.security.PasswordDigester;


public final class JWTAuthenticationHandler implements AuthenticationHandler {


	private final UserHandler userHandler;
	private final PasswordDigester passwordDigester;


	public JWTAuthenticationHandler( UserHandler userHandler, PasswordDigester passwordDigester ) {
		this.userHandler = userHandler;
		this.passwordDigester = passwordDigester;
	}


	@Override
	public AuthenticationToken login( String username, String plainTextPassword ) {
		Argument.assertNotEmpty( username, "username" );
		Argument.assertNotEmpty( plainTextPassword, "plainTextPassword" );

		try {
			User user = this.userHandler.getUserWithUsername( username );
			boolean passwordMatches = this.passwordDigester.matches( plainTextPassword, user.getPasswordDigest() );
			if( passwordMatches == false )
				throw new InvalidUsernamePasswordException();

			JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam( "typ", "JWT" ).setSubject( user.getIdAsString() );

			LocalDateTime now = LocalDateTime.now();
			jwtBuilder.setIssuedAt( Date.from( now.atZone( ZoneId.systemDefault() ).toInstant() ) );

			LocalDateTime expiration = LocalDateTime.from( now );
			expiration = expiration.plusMinutes( 5 ); // FIXME Should not be fixed in here
			jwtBuilder.setExpiration( Date.from( expiration.atZone( ZoneId.systemDefault() ).toInstant() ) );

			jwtBuilder.signWith( SignatureAlgorithm.HS512, user.getPasswordDigest().getBytes() );
			return new JWTAuthenticationToken( now, expiration, jwtBuilder.compact() );
		}
		catch( UserNotFoundException unfe ) {
			throw new InvalidUsernamePasswordException();
		}
	}

}
