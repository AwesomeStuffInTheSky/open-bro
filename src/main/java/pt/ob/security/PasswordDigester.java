package pt.ob.security;


public interface PasswordDigester {


	public String digest( String password );


	public boolean matches( String password, String digest );

}
