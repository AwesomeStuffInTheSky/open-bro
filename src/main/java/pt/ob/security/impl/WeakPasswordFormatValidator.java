package pt.ob.security.impl;


import pt.asits.util.argument.assertions.Argument;
import pt.ob.security.PasswordFormatValidator;


public final class WeakPasswordFormatValidator implements PasswordFormatValidator {


	@Override
	public boolean isValid( String plainTextPassword, String username ) {
		Argument.assertNotEmpty( plainTextPassword, "plainTextPassword" );
		Argument.assertNotEmpty( username, "username" );

		if( plainTextPassword == null )
			return false;
		return plainTextPassword.matches( "[a-zA-Z0-9]{8,16}" );
	}

}
