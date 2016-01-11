package pt.ob.auth.exceptions;


public final class InvalidUserIdException extends RuntimeException {


	private static final long serialVersionUID = 1L;


	public InvalidUserIdException() {}


	public InvalidUserIdException( String message ) {
		super( message );
	}

}
