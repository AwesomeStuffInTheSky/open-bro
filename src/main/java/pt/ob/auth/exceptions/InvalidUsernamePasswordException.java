package pt.ob.auth.exceptions;


public class InvalidUsernamePasswordException extends RuntimeException {


	private static final long serialVersionUID = 1L;


	public InvalidUsernamePasswordException() {}


	public InvalidUsernamePasswordException( String message ) {
		super( message );
	}

}
