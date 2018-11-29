package org.datazup.webscraper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseTest {

    public static String getJsonFromObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Map<String,Object> getMapFromJson(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        // false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
        try {
            Object o = mapper.readValue(jsonString, Map.class);
            return (Map<String, Object>) o;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected String loadDefinitionFromResources(String path){
        InputStream inputStream = WebScrapperJsoupExtractorTest.class.getClassLoader().getResourceAsStream(path);
        String result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));

        return result;
    }

    protected Map<String,Object> getDefinition(String path){

        String jsonDef = loadDefinitionFromResources(path);
        Map<String,Object> def = getMapFromJson(jsonDef);

        return def;
    }

    protected String getHtmlFromUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String html = doc.outerHtml();

        return html;
    }
}
