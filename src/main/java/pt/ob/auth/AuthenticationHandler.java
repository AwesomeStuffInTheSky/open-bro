package pt.ob.auth;


import pt.ob.auth.exceptions.InvalidUsernamePasswordException;


public interface AuthenticationHandler {


	public AuthenticationToken login( String username, String plainTextPassword ) throws InvalidUsernamePasswordException;

}
