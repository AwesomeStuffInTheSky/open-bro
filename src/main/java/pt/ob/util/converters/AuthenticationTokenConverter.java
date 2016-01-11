package pt.ob.util.converters;


import pt.ob.auth.AuthenticationToken;
import pt.ob.rest.entities.AuthenticationTokenJson;
import pt.ob.util.ConversionException;
import pt.ob.util.ObjectConverter;
import pt.ob.util.validators.NotNullArgumentValidator;


public class AuthenticationTokenConverter implements ObjectConverter<AuthenticationToken, AuthenticationTokenJson> {


	@Override
	public AuthenticationTokenJson convert( AuthenticationToken token ) throws ConversionException {
		NotNullArgumentValidator.INSTANCE.validate( token, "token" );
		try {
			return new AuthenticationTokenJson( token.getAsString(), token.getExpirationTime() );
		}
		catch( Exception e ) {
			throw new ConversionException( "There was an exception in converting the object.", e );
		}
	}

}
