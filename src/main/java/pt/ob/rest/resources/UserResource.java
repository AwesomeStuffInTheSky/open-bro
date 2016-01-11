package pt.ob.rest.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ob.auth.User;
import pt.ob.auth.UserHandler;
import pt.ob.auth.exceptions.InvalidPasswordException;
import pt.ob.auth.exceptions.InvalidUsernameException;
import pt.ob.auth.exceptions.UsernameAlreadyExistsException;
import pt.ob.rest.entities.CredentialPairJson;
import pt.ob.rest.entities.UserJson;
import pt.ob.rest.response.PreparedResponse;
import pt.ob.util.ObjectConverter;
import pt.ob.util.converters.UserConverter;


@Path( "user" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class UserResource {


	private final UserHandler userHandler;
	private final ObjectConverter<User, UserJson> userConverter;


	public UserResource( UserHandler userHandler ) {
		this.userHandler = userHandler;
		this.userConverter = new UserConverter();
	}


	@PUT
	@Path( "create" )
	public Response createUser( CredentialPairJson credentialPair ) {
		try {
			User user = this.userHandler.createUser( credentialPair.getUsername(), credentialPair.getPassword() );
			return PreparedResponse.ok().entity( this.userConverter.convert( user ) ).build();
		}
		catch( InvalidUsernameException iue ) {
			return PreparedResponse.badRequest().error( "Invalid username format." ).build();
		}
		catch( InvalidPasswordException ipe ) {
			return PreparedResponse.badRequest().error( "Invalid password format." ).build();
		}
		catch( UsernameAlreadyExistsException uaee ) {
			return PreparedResponse.conflict().error( "Username already exists." ).build();
		}
	}
	
	
	@GET
	@Path( "details" )
	public Response listUsers() {
		return PreparedResponse.ok().entity( "{ \"troll\" : \"troll\" }" ).build();
	}

}
