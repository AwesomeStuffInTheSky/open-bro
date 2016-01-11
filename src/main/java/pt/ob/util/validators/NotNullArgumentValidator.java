package pt.ob.util.validators;


import pt.ob.util.ArgumentValidator;


public enum NotNullArgumentValidator implements ArgumentValidator<Object> {

	INSTANCE;


	@Override
	public void validate( Object argument, String argumentName ) {
		if( argument == null )
			throw new IllegalArgumentException( "The ".concat( argumentName ).concat( " cannot be null." ) );
	}

}
