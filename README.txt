To reproduce the bug:

Build and run tests with Jackson 2.20.1:
```
./mvnw clean verify
```

Still on jackson 2, disable MapperFeature.AUTO_DETECT_CREATORS:
```
cat << EOF | git apply
diff --git a/src/main/java/org/example/jacksonbug/JacksonSetup.java b/src/main/java/org/example/jacksonbug/JacksonSetup.java
index 8b84abd..0f9fdd4 100644
--- a/src/main/java/org/example/jacksonbug/JacksonSetup.java
+++ b/src/main/java/org/example/jacksonbug/JacksonSetup.java
@@ -17,7 +17,7 @@ public class JacksonSetup {
         return JsonMapper.builder()
                 // this is enabled by default in Jackson 2.20.1
                 // disabling it fails the test:
-                // .disable(MapperFeature.AUTO_DETECT_CREATORS)
+                 .disable(MapperFeature.AUTO_DETECT_CREATORS)

                 // does it get replaced by one of these in Jackson 3?
                 // .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
EOF
./mvnw clean verify # the test now fails
git restore src/
```

Upgrade to Jackson 3:
```
./mvnw rewrite:run
git diff
```

Build and run tests with Jackson 3:
```
./mvnw clean verify
```
