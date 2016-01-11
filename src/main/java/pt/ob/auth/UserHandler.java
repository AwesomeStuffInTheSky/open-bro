package pt.ob.auth;


import pt.ob.auth.exceptions.InvalidPasswordException;
import pt.ob.auth.exceptions.InvalidUserIdException;
import pt.ob.auth.exceptions.InvalidUsernameException;
import pt.ob.auth.exceptions.UsernameAlreadyExistsException;
import pt.ob.data.exceptions.UserNotFoundException;


public interface UserHandler {


	public User createUser( String username, String password )
			throws InvalidUsernameException, InvalidPasswordException, UsernameAlreadyExistsException;


	public User getUserWithUsername( String username ) throws UserNotFoundException;


	public User getUserWithId( String userId ) throws InvalidUserIdException, UserNotFoundException;

}
