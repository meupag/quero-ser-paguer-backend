package br.com.pag.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MoneySerializer extends StdSerializer<BigDecimal> {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(MoneySerializer.class);

	public MoneySerializer() {
		super(BigDecimal.class);
	}

	
	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		String amount = "0.00";

		if(value != null) {
			amount = value.setScale(2, RoundingMode.CEILING).toString();
		} else {
			log.warn("Money value converter received a NULL !");
		}
		
		gen.writeString(amount);
		
	}

}
