package pt.ob.util.json;


import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {


	@Override
	public void serialize( LocalDateTime value, JsonGenerator generator, SerializerProvider serializers )
			throws IOException, JsonProcessingException {
		if( value != null )
			generator.writeString( value.toString() );
	}

}
