package pt.ob.util.validators;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class NotEmptyStringArgumentValidatorTest {


	@Rule
	public final ExpectedException expectedException;

	private final NotEmptyStringArgumentValidator validator;


	public NotEmptyStringArgumentValidatorTest() {
		this.expectedException = ExpectedException.none();
		this.validator = NotEmptyStringArgumentValidator.INSTANCE;
	}


	@Test
	public void validateUsingAValidArgumentAndNameTest() {
		this.validator.validate( "someArgument", "someArgumentName" );
	}
	
	
	@Test
	public void validateUsingANullArgumentTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.validator.validate( null, "someArgumentName" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void validateUsingAEmptyStringTest() {
		this.expectedException.expect( IllegalArgumentException.class );
		this.validator.validate( "", "someArgumentName" );
		Assert.fail( "An exception should have been throwed." );
	}
	
	
	@Test
	public void validateUsingAEmptyStringWithSpacesTest() {
		this.validator.validate( "  ", "someArgumentName" );
		Assert.assertTrue( "Should reach this point.", true );
	}
	
	
	@Test
	public void validateUsingAEmptyStringWithTabsTest() {
		this.validator.validate( "\t\t", "someArgumentName" );
		Assert.assertTrue( "Should reach this point.", true );
	}
	
	
	@Test
	public void validateUsingAValidArgumentAndANullNameTest() {
		this.validator.validate( "someArgument", null );
		Assert.assertTrue( "Should reach this point.", true );
	}
	
	
	/**
	 *  This test is just to increase the coverage of JaCoCo
	 */
	@Test
	public void valueOfUsingAValidEnumPropertyTest() {
		NotEmptyStringArgumentValidator instance = NotEmptyStringArgumentValidator.valueOf( "INSTANCE" );
		Assert.assertNotNull( "Should not be null.", instance );
	}

}
