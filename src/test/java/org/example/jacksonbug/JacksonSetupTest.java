package org.example.jacksonbug;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.jacksonbug.model.ThingWithStaticFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonSetupTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JacksonSetup.setupJacksonMapper();
    }

    @Test
    void canSerThingWithStaticFactory() throws JacksonException {
        final ThingWithStaticFactory u = ThingWithStaticFactory.of("test-user");

        assertJsonEquals(
                "test-user",
                objectMapper.writeValueAsString(u)
        );
    }

    @Test
    void canDeserThingWithStaticFactory() throws JacksonException {
        final String json = "{\"user\": \"test-user\", \"foo\": \"bar\"}";
        final Blah blah = objectMapper.readValue(
                json,
                Blah.class
        );
        final ThingWithStaticFactory expectedUser = ThingWithStaticFactory.of("test-user");
        assertEquals(expectedUser, blah.user);
    }

    static class Blah {

        @JsonProperty
        ThingWithStaticFactory user;

        @JsonProperty
        String foo;
    }

}
