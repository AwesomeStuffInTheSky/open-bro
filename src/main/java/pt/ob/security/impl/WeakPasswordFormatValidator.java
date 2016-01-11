package pt.ob.security.impl;


import pt.ob.security.PasswordFormatValidator;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;


public class WeakPasswordFormatValidator implements PasswordFormatValidator {


	@Override
	public boolean isValid( String plainTextPassword, String username ) {
		NotEmptyStringArgumentValidator.INSTANCE.validate( plainTextPassword, "plainTextPassword", username, "username" );
		if( plainTextPassword == null )
			return false;
		return plainTextPassword.matches( "[a-zA-Z0-9]{8,16}" );
	}

}
