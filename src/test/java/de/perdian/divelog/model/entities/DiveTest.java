package de.perdian.divelog.model.entities;

import java.io.IOException;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DiveTest {

    @Test
    public void jsonSerialization() throws IOException {

        Dive dive = new Dive();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String jsonString = objectWriter.writeValueAsString(dive);
        MatcherAssert.assertThat(jsonString, Matchers.not(Matchers.containsString("\"id\" : ")));
        MatcherAssert.assertThat(jsonString, Matchers.not(Matchers.containsString("\"user\" : ")));

    }

}
