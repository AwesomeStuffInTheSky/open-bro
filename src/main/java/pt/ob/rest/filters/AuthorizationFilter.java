package pt.ob.rest.filters;


import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import pt.ob.rest.response.PreparedResponse;


@Priority( value = 200 )
public class AuthorizationFilter implements ContainerRequestFilter {


	@Override
	public void filter( ContainerRequestContext requestContext ) throws IOException {
		// TODO this is a dummy implementation
		String path = requestContext.getUriInfo().getPath();
		if( "user/details".equals( path ) ) {
			String userId = (String)requestContext.getProperty( "userId" );
			if( userId == null ) {
				Response response = PreparedResponse.unauthorized().error( "Authentication required." ).build();
				requestContext.abortWith( response );
				return;
			}
		}
	}

}
