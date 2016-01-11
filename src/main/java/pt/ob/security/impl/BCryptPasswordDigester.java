package pt.ob.security.impl;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pt.ob.security.PasswordDigester;


public final class BCryptPasswordDigester implements PasswordDigester {


	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public BCryptPasswordDigester( int strength ) {
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder( strength );
	}


	@Override
	public String digest( String password ) {
		return this.bCryptPasswordEncoder.encode( password );
	}


	@Override
	public boolean matches( String password, String passwordDigest ) {
		return this.bCryptPasswordEncoder.matches( password, passwordDigest );
	}

}
