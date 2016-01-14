package pt.ob.rest.filters;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import pt.ob.auth.User;
import pt.ob.auth.UserHandler;
import pt.ob.auth.exceptions.InvalidUserIdException;
import pt.ob.data.exceptions.UserNotFoundException;
import pt.ob.rest.response.PreparedResponse;


@Priority( value = 100 )
public final class AuthenticationFilter implements ContainerRequestFilter {


	private final UserHandler userHandler;


	public AuthenticationFilter( UserHandler userHandler ) {
		this.userHandler = userHandler;
	}


	@Override
	public void filter( ContainerRequestContext requestContext ) throws IOException {
		String jwsToken = requestContext.getHeaderString( "Auth-Token" );

		// The token was not supplied
		if( jwsToken == null )
			return;
		else {
			// Transform the JWS into a JWT
			String jwtToken = jwsToken.substring( 0, jwsToken.lastIndexOf( "." ) + 1 );

			try {
				@SuppressWarnings( "rawtypes" )
				Jwt<Header, Claims> jwtClaims = Jwts.parser().parseClaimsJwt( jwtToken );

				LocalDateTime issueTime = LocalDateTime.ofInstant( jwtClaims.getBody().getIssuedAt().toInstant(),
						ZoneId.systemDefault() );
				if( LocalDateTime.now().isBefore( issueTime ) ) {
					Response response = PreparedResponse.unauthorized().error( "Auth-Token issue time is in the future." )
							.build();
					requestContext.abortWith( response );
					return;
				}
				else {
					String userId = jwtClaims.getBody().getSubject();
					User user = this.userHandler.getUserWithId( userId );

					// Jws<Claims> jwsClaims
					Jwts.parser().setSigningKey( user.getPasswordDigest().getBytes() ).parseClaimsJws( jwsToken );
					requestContext.setProperty( "userId", userId );
				}
			}
			catch( ExpiredJwtException eje ) {
				Response response = PreparedResponse.unauthorized().error( "Auth-Token expired." ).build();
				requestContext.abortWith( response );
				return;
			}
			catch( SignatureException se ) {
				Response response = PreparedResponse.unauthorized().error( "Auth-Token signature is invalid." ).build();
				requestContext.abortWith( response );
				return;
			}
			catch( UnsupportedJwtException uje ) {
				Response response = PreparedResponse.unauthorized().error( "Invalid Auth-Token format." ).build();
				requestContext.abortWith( response );
				return;
			}
			catch( InvalidUserIdException | UserNotFoundException e ) {
				// TODO This should be tested
				Response response = PreparedResponse.unauthorized().error( "Invalid userId in the Auth-Token." ).build();
				requestContext.abortWith( response );
				return;
			}
		}
	}

}
