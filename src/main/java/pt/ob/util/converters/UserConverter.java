package pt.ob.util.converters;


import pt.ob.auth.User;
import pt.ob.rest.entities.UserJson;
import pt.ob.util.ConversionException;
import pt.ob.util.ObjectConverter;
import pt.ob.util.validators.NotNullArgumentValidator;


public class UserConverter implements ObjectConverter<User, UserJson> {


	@Override
	public UserJson convert( User user ) throws ConversionException {
		NotNullArgumentValidator.INSTANCE.validate( user, "user" );
		return new UserJson( user.getIdAsString(), user.getUsername() );
	}

}
