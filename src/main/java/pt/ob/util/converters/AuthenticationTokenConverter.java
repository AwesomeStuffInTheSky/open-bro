package pt.ob.util.converters;


import pt.asits.util.ObjectConverter;
import pt.asits.util.argument.assertions.Argument;
import pt.asits.util.exceptions.ConversionException;
import pt.ob.auth.AuthenticationToken;
import pt.ob.rest.entities.AuthenticationTokenJson;


public class AuthenticationTokenConverter implements ObjectConverter<AuthenticationToken, AuthenticationTokenJson> {


	@Override
	public AuthenticationTokenJson convert( AuthenticationToken token ) throws ConversionException {
		Argument.assertNotNull( token, "token" );

		try {
			return new AuthenticationTokenJson( token.getAsString(), token.getExpirationTime() );
		}
		catch( Exception e ) {
			throw new ConversionException( "There was an exception in converting the object.", e );
		}
	}

}
