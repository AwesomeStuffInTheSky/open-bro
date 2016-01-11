package pt.ob.util;


public interface ArgumentValidator<T> {


	public void validate( T argument, String argumentName );


	default public void validate( T argument1, String argumentName1, T argument2, String argumentName2 ) {
		validate( argument1, argumentName1 );
		validate( argument2, argumentName2 );
	}


	default public void validate( T argument1, String argumentName1, T argument2, String argumentName2, T argument3,
			String argumentName3 ) {
		validate( argument1, argumentName1 );
		validate( argument2, argumentName2 );
		validate( argument3, argumentName3 );
	}

}
