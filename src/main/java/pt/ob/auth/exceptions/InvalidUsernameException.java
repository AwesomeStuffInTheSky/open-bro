package pt.ob.auth.exceptions;


public class InvalidUsernameException extends RuntimeException {


	private static final long serialVersionUID = 1L;


	public InvalidUsernameException() {}


	public InvalidUsernameException( String message ) {
		super( message );
	}

}
