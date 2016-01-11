package pt.ob.security.impl;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pt.ob.security.UsernameFormatValidator;
import pt.ob.util.validators.NotEmptyStringArgumentValidator;


public class EmailUsernameFormatValidator implements UsernameFormatValidator {


	@Override
	public boolean isValid( String username ) {
		NotEmptyStringArgumentValidator.INSTANCE.validate( username, "username" );
		try {
			// Try this
			InternetAddress.parse( username );
			
			//TODO The InternetAddress is shit
			if( username.contains( "@" ) == false ) {
				return false;
			}
			
			return true;
		}
		catch( AddressException ae ) {
			return false;
		}
	}

}
