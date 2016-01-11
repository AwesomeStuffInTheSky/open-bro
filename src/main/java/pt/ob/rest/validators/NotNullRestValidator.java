package pt.ob.rest.validators;


import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import pt.ob.rest.RestParameterValidator;
import pt.ob.rest.response.PreparedResponse;


public enum NotNullRestValidator implements RestParameterValidator<Object> {

	INSTANCE;


	@Override
	public void validatePathParameter( Object t, String pathParameterName ) throws ClientErrorException {
		if( t == null ) {
			Response response = PreparedResponse.badRequest()
					.error( "The path parameter ".concat( pathParameterName ).concat( " cannot be null." ) ).build();
			throw new BadRequestException( response );
		}
	}


	@Override
	public void validateQueryParameter( Object t, String queryParameterName ) throws ClientErrorException {
		if( t == null ) {
			Response response = PreparedResponse.badRequest()
					.error( "The query parameter ".concat( queryParameterName ).concat( " cannot be null." ) ).build();
			throw new BadRequestException( response );
		}
	}

}
