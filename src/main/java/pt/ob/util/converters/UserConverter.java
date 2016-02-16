package pt.ob.util.converters;


import pt.asits.util.ObjectConverter;
import pt.asits.util.argument.assertions.Argument;
import pt.asits.util.exceptions.ConversionException;
import pt.ob.auth.User;
import pt.ob.rest.entities.UserJson;


public class UserConverter implements ObjectConverter<User, UserJson> {


	@Override
	public UserJson convert( User user ) throws ConversionException {
		Argument.assertNotNull( user, "user" );

		return new UserJson( user.getIdAsString(), user.getUsername() );
	}

}
