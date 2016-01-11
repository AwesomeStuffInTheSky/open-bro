package pt.ob.util.validators;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class NotNullArgumentValidatorTest {


	@Rule
	public final ExpectedException expectedException;

	private final NotNullArgumentValidator validator;


	public NotNullArgumentValidatorTest() {
		this.expectedException = ExpectedException.none();
		this.validator = NotNullArgumentValidator.INSTANCE;
	}


	@Test
	public void validateUsingANotNullStringArgumentAndNameTest() {
		this.validator.validate( "someArgument", "someArgumentName" );
	}
	
	
	@Test
	public void validateUsingANotNullPrimitiveArgumentAndNameTest() {
		int a = 42;
		this.validator.validate( a, "someArgumentName" );
	}
	
	
	@Test
	public void validateUsingANullArgumentAndNameTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.validator.validate( null, "someArgumentName" );
	}
	
	
	// TODO This test is just to increase the coverage of JaCoCo
	@Test
	public void valueOfUsingAValidEnumPropertyTest() {
		NotNullArgumentValidator.valueOf( "INSTANCE" );
	}

}
