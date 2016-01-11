package pt.ob.security;


public interface PasswordFormatValidator {


	public boolean isValid( String plainTextPassword, String username );

}
