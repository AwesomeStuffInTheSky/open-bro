package moks;


import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;


public class ContainerRequestContextMock implements ContainerRequestContext {
	
	
	private String authToken;
	private final Map<String, Object> properties;
	private Response abort;
	
	
	public ContainerRequestContextMock() {
		this.properties = new HashMap<>();
	}
	
	
	public void setAuthToken( String authToken ) {
		this.authToken = authToken;	
	}


	@Override
	public Object getProperty( String name ) {
		return this.properties.get( name );
	}


	@Override
	public Collection<String> getPropertyNames() {
		return new HashSet<>( this.properties.keySet() );
	}


	@Override
	public void setProperty( String name, Object object ) {
		this.properties.put( name, object );
	}


	@Override
	public void removeProperty( String name ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public UriInfo getUriInfo() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void setRequestUri( URI requestUri ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void setRequestUri( URI baseUri, URI requestUri ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public Request getRequest() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public String getMethod() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void setMethod( String method ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public MultivaluedMap<String, String> getHeaders() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public String getHeaderString( String name ) {
		if( "Auth-Token".equals( name ) )
			return this.authToken;
		else
			return null;
	}


	@Override
	public Date getDate() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public Locale getLanguage() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public int getLength() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public MediaType getMediaType() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public List<MediaType> getAcceptableMediaTypes() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public List<Locale> getAcceptableLanguages() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public Map<String, Cookie> getCookies() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public boolean hasEntity() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public InputStream getEntityStream() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void setEntityStream( InputStream input ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public SecurityContext getSecurityContext() {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void setSecurityContext( SecurityContext context ) {
		throw new UnsupportedOperationException( "Not implemented yet." );
	}


	@Override
	public void abortWith( Response response ) {
		this.abort = response;
	}
	
	
	public boolean isAbort() {
		return this.abort != null;
	}
	
	
	public Response getAbort() {
		return this.abort;
	}

}
