package utilities;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {
    
    @Test
    public void testDatokonvertering() throws Exception {
        String datoStreng = "Thu Jan 08 13:46:39 +0000 2015";

        LocalDateTime forventetDato = LocalDateTime.of(2015, 1, 8, 13, 46, 39);
        assertEquals(forventetDato, JsonParser.formatCreatedAt(datoStreng));
    }
}