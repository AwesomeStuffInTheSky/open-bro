package pt.ob.security.impl;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pt.asits.util.argument.assertions.Argument;
import pt.ob.security.UsernameFormatValidator;


public final class EmailUsernameFormatValidator implements UsernameFormatValidator {


	@Override
	public boolean isValid( String username ) {
		Argument.assertNotEmpty( username, "username" );

		try {
			// Try this
			InternetAddress.parse( username );

			// TODO The InternetAddress is shit
			return username.contains( "@" );
		}
		catch( AddressException ae ) {
			return false;
		}
	}

}
