package pt.ob.rest.response;


import javax.ws.rs.core.Response.Status;


public abstract class PreparedResponse {


	public static PreparedResponseBuilder ok() {
		return new PreparedResponseBuilder( Status.OK ); // 200
	}
	
	
	public static PreparedResponseBuilder badRequest() {
		return new PreparedResponseBuilder( Status.BAD_REQUEST ); // 400
	}


	public static PreparedResponseBuilder unauthorized() {
		return new PreparedResponseBuilder( Status.UNAUTHORIZED ); // 401
	}
	
	
	public static PreparedResponseBuilder forbidden() {
		return new PreparedResponseBuilder( Status.FORBIDDEN ); // 403
	}
	
	
	public static PreparedResponseBuilder conflict() {
		return new PreparedResponseBuilder( Status.CONFLICT ); // 409
	}

}
