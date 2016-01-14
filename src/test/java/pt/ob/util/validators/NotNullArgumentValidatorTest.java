package pt.ob.util.validators;


import org.junit.Assert;
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
		Assert.assertTrue( "Should reach this point.", true );
	}
	
	
	@Test
	public void validateUsingANotNullPrimitiveArgumentAndNameTest() {
		int a = 42;
		this.validator.validate( a, "someArgumentName" );
		Assert.assertTrue( "Should reach this point.", true );
	}
	
	
	@Test
	public void validateUsingANullArgumentAndNameTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.validator.validate( null, "someArgumentName" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	/**
	 *  This test is just to increase the coverage of JaCoCo
	 */
	@Test
	public void valueOfUsingAValidEnumPropertyTest() {
		NotNullArgumentValidator instance = NotNullArgumentValidator.valueOf( "INSTANCE" );
		Assert.assertNotNull( "Should not be null.", instance );
	}

}
