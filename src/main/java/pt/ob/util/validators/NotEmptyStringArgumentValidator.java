package pt.ob.util.validators;


import pt.ob.util.ArgumentValidator;


public enum NotEmptyStringArgumentValidator implements ArgumentValidator<String> {

	INSTANCE;


	@Override
	public void validate( String argument, String argumentName ) {
		if( argument == null || argument.isEmpty() )
			throw new IllegalArgumentException( "The ".concat( argumentName ).concat( " cannot be null or empty." ) );
	}

}
