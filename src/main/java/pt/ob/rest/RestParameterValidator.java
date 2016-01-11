package pt.ob.rest;


import javax.ws.rs.ClientErrorException;


public interface RestParameterValidator<T> {


	public void validatePathParameter( T t, String pathParameterName ) throws ClientErrorException;


	public void validateQueryParameter( T t, String queryParameterName ) throws ClientErrorException;

}
