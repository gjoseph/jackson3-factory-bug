package org.example.jacksonbug;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.jacksonbug.model.ThingWithStaticFactory;

public class JacksonSetup {

    public static ObjectMapper setupJacksonMapper() {
        final SimpleModule m = new SimpleModule();

        m.setMixInAnnotation(ThingWithStaticFactory.class, ThingWithStaticFactoryJacksonSupport.class);

        return JsonMapper.builder()
                // this is enabled by default in Jackson 2.20.1
                // disabling it fails the test:
                // .disable(MapperFeature.AUTO_DETECT_CREATORS)

                // does it get replaced by one of these in Jackson 3?
                // .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)

                .enable(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)

                .addModule(m)
                .build();
    }

    abstract static class ThingWithStaticFactoryJacksonSupport {

        @JsonValue
        String id;
    }

}
