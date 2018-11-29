package org.datazup.webscraper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class WebScrapperJsoupUpdaterTest extends BaseTest {

    String runTest(String path, String url) throws IOException {
        path = "update/"+path+".json";
        Map<String,Object> def = getDefinition(path);

        IWebScraperUpdater impl = new WebScraperJsoupUpdaterImpl(def);

        String html = getHtmlFromUrl(url);

        String m = impl.run(html);

        return m;
    }

    Map<String,Object> getScraped(String path, String content) throws IOException {
        path = "update/scraper."+path+".json";

        Map<String,Object> def = getDefinition(path);

        WebScraperJsoupExtractorImpl impl = new WebScraperJsoupExtractorImpl(def);

        Map<String,Object> m = impl.run(content);

        return m;
    }

    @Test
    public void titleChangeExampleComTest() throws IOException {
        String url = "example.com";
        String updateHtml = runTest(url, "https://"+url);

        Assert.assertTrue(null!=updateHtml);
        Assert.assertTrue(!updateHtml.isEmpty());

        Map<String,Object> scrapedMap = getScraped(url, updateHtml);
        Assert.assertTrue(null!=scrapedMap);
        Assert.assertTrue(scrapedMap.size()>0);

        Assert.assertTrue(scrapedMap.get("title").equals("Custom Title"));
        Assert.assertTrue(scrapedMap.get("domainName").equals("Custom Domain"));
        Assert.assertTrue(scrapedMap.get("linkHref").equals("http://datazup.com"));
        Assert.assertTrue(scrapedMap.get("linkMore").equals("Datazup More"));


    }
}
