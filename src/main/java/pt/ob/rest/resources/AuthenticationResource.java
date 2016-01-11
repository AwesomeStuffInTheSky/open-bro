
package pt.ob.rest.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ob.auth.AuthenticationHandler;
import pt.ob.auth.AuthenticationToken;
import pt.ob.auth.exceptions.InvalidUsernamePasswordException;
import pt.ob.rest.entities.AuthenticationTokenJson;
import pt.ob.rest.entities.CredentialPairJson;
import pt.ob.rest.response.PreparedResponse;


@Path( "auth" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public final class AuthenticationResource {


	private final AuthenticationHandler authenticationHandler;


	public AuthenticationResource( AuthenticationHandler authenticationHandler ) {
		this.authenticationHandler = authenticationHandler;
	}


	@POST
	@Path( "login" )
	public Response login( CredentialPairJson credentialPair ) {
		try {
			AuthenticationToken token = this.authenticationHandler.login( credentialPair.getUsername(),
					credentialPair.getPassword() );
			// TODO
			AuthenticationTokenJson rr = new AuthenticationTokenJson( token.getAsString(), token.getExpirationTime() );
			return PreparedResponse.ok().entity( rr ).build();
		}
		catch( InvalidUsernamePasswordException iupe ) {
			return PreparedResponse.forbidden().error( "The username / password combination is invalid." ).build();
		}
	}

}
