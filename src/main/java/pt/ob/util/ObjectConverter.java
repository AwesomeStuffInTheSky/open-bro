package pt.ob.util;


import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pt.ob.util.validators.NotNullArgumentValidator;


public interface ObjectConverter<T, K> {


	public K convert( T t ) throws ConversionException;


	default public Set<K> convertSet( Set<T> tSet ) throws ConversionException {
		NotNullArgumentValidator.INSTANCE.validate( tSet, "tSet" );
		Set<K> kSet = new LinkedHashSet<>();
		for( T t : tSet ) {
			if( t != null )
				kSet.add( this.convert( t ) );
		}
		return kSet;
	}
	
	
	default public List<K> convertList( List<T> tList ) throws ConversionException {
		NotNullArgumentValidator.INSTANCE.validate( tList, "tList" );
		List<K> kList = new LinkedList<>();
		for( T t : tList ) {
			if( t != null )
				kList.add( this.convert( t ) );
		}
		return kList;
	}

}
