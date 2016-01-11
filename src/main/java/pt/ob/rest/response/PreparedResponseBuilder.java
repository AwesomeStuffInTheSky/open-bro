package pt.ob.rest.response;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;


public class PreparedResponseBuilder {


	private final Status status;
	private Object entity;
	private String[] errors;


	public PreparedResponseBuilder( Status status ) {
		this.status = status;
	}
	
	
	public PreparedResponseBuilder entity( Object entity ) {
		this.entity = entity;
		return this;
	}
	
	
	public PreparedResponseBuilder error( String ... errors ) {
		this.errors = errors;
		return this;
	}
	
	
	public Response build() throws IllegalStateException {
		if( this.entity != null && this.errors != null )
			throw new IllegalStateException( "Cannot build with both errors and entity set." );
		
		if( this.entity != null )
			return Response.status( this.status ).entity( this.entity ).build();
		else if( this.errors != null ) {
			JsonNodeFactory factory = JsonNodeFactory.instance;
			ArrayNode errors = factory.arrayNode();
			for( String error : this.errors ) {
				errors.add( factory.objectNode().set( "error", factory.textNode( error ) ) );
			}
			JsonNode json = factory.objectNode().set( "errors", errors );
			return Response.status( this.status ).entity( json ).build();
		}
		else {
			return Response.status( this.status ).build();
		}
	}

}
