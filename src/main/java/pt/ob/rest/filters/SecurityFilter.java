package pt.ob.rest.filters;


import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import pt.ob.rest.response.PreparedResponse;


public class SecurityFilter implements ContainerRequestFilter {


	@Override
	public void filter( ContainerRequestContext requestContext ) throws IOException {
		
		// TODO For now it does nothing of value, but it should check if the resource being called, needs
		// authorization, if yes, grab the auth token and check if the associated user has the required
		// authorization.
		
		String path = requestContext.getUriInfo().getPath();
		if( path.equals( "user/details" ) ) {
			String token = requestContext.getHeaderString( "Auth-Token" );
			if( token == null ) {
				Response response = PreparedResponse.unauthorized().error( "The authentication token is absent." ).build();
				requestContext.abortWith( response );
				return;
			}
		}
	}

}
