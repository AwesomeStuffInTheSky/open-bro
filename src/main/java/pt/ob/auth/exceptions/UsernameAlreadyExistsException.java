package pt.ob.auth.exceptions;


public class UsernameAlreadyExistsException extends RuntimeException {


	private static final long serialVersionUID = 1L;


	public UsernameAlreadyExistsException() {}


	public UsernameAlreadyExistsException( String message ) {
		super( message );
	}

}
