package org.datazup.webscraper;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;


public class WebScrapperJsoupExtractorTest extends BaseTest {



    Map<String,Object> runTest(String path, String url) throws IOException {
        Map<String,Object> def = getDefinition(path);

        WebScraperJsoupExtractorImpl impl = new WebScraperJsoupExtractorImpl(def);

        String html = getHtmlFromUrl(url);

        Map<String,Object> m = impl.run(html);

        return m;
    }

   /* @Test
    public void mainContainerExtractionTest() throws IOException {
        String path = "autopazar_def.json";
        String url = "http://autopazar.ba/community/search/?tag=reno+megane";

        Map m = runTest(path, url);
        Assert.assertNotNull(m);

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);
    }*/

   /* @Test
    public void singlePageExtractionTest() throws IOException {
        String url = "http://autopazar.ba/vozila/29983/reno-15dci-svi-tipovi-pumpa-delphi-170e-garancija";
        String path = "autopazar_single_def.json";

        Map m = runTest(path, url);

        // {"title":"Reno 1.5dci svi tipovi Pumpa delphi 170e garancija","properties":{"details":{"title":"Reno 1.5dci svi tipovi Pumpa delphi 170e garancija","price":"170 KM"}}}

        Assert.assertTrue(m.size()==2);
        Assert.assertTrue(m.containsKey("title"));
        Assert.assertTrue(m.containsKey("properties"));
        Map m1 = (Map) m.get("properties");
        Assert.assertNotNull(m1);
        Assert.assertTrue(m1.containsKey("details"));
        Map details = (Map) m1.get("details");
        Assert.assertNotNull(details);
        Assert.assertTrue(details.containsKey("title"));
        Assert.assertTrue(details.containsKey("price"));

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);
    }*/

    @Test
    public void multipleContainerSelectoreTest() throws IOException {

        String url = "https://www.wikipedia.org/";
        String path = "wikipedia_main_def.json";

        Map m = runTest(path, url);

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);
    }

    @Test
    public void multipleContainerObjectSelectorTest() throws IOException {
        String url = "https://en.wikipedia.org/wiki/Fred_Thompson";
        String path = "wikipedia_single_def.json";

        Map m = runTest(path, url);

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);

    }

    @Test
    public void singleDetailPageOlx() throws IOException {
        String url = "https://www.olx.ba/artikal/26060484/stan-cengic-vila-115-5m2-zgrada-mrvica/";
        String path = "olx_detail_page_def.json";

        Map m = runTest(path, url);

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);

    }

    @Test
    public void singleDetailPageAdditionalFieldsAsListOlx() throws IOException {
        String url = "https://www.olx.ba/artikal/26060484/stan-cengic-vila-115-5m2-zgrada-mrvica/";
        String path = "olx_detail_page_additionalFields_asList_def.json";

        Map m = runTest(path, url);

        String jsonM = getJsonFromObject(m);

        System.out.println(jsonM);

    }

}
