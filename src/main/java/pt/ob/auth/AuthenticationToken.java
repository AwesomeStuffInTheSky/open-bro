package pt.ob.auth;


import java.time.LocalDateTime;


public interface AuthenticationToken {


	public boolean isValid();


	public boolean isExpired();


	public LocalDateTime getExpirationTime();


	public String getAsString();

}
