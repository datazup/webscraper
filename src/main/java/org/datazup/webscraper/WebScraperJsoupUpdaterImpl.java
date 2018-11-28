package org.datazup.webscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebScraperJsoupUpdaterImpl implements IWebScraperUpdater {

    private Map<String, Object> definition;

    public WebScraperJsoupUpdaterImpl(Map<String, Object> definition) {
        this.definition = definition;
    }

    @Override
    public String run(String inputData) {
        Document doc = Jsoup.parse(inputData);

        if (null != definition && definition.containsKey("selectors")) {
            List<Map<String, Object>> selectorList = (List<Map<String, Object>>) definition.get("selectors");
            if (null != selectorList) {
                for (Map<String, Object> selector : selectorList) {
                    process(doc, selector);
                }
            }
        }
        return doc.outerHtml();
    }

    private void process(Document doc, Map<String, Object> selector) {

        String type = (String) selector.get("type");
        String css = (String) selector.get("css");
        String value = (String)selector.get("value");
        String attr = (String)selector.get("attr");

        switch (type) {
            case "text":
                doc.select(css).first().text(value);
                break;
            case "prependText":
                doc.select(css).first().prependText(value);
                break;
            case "appendText":
                doc.select(css).first().appendText(value);
                break;
            case "removeText":
                Element el = doc.select(css).first();
                String tmpText = el.text();
                tmpText = tmpText.replaceAll(value, "");
                el.text(tmpText);
                break;
            case "html":
                doc.select(css).first().html(value);
                break;
            case "htmlAppend":
                doc.select(css).first().append(value);
                break;
            case "htmlPrepend":
                doc.select(css).first().prepend(value);
                break;
            case "htmlWrap":
                doc.select(css).first().wrap(value);
            case "attr":
                if (null!=value && !value.isEmpty()){
                    if (value.startsWith("+")){
                        doc.select(css).first().attr(attr, value.substring(1));
                    }else if (value.startsWith("-")){
                        Element tmpEl = doc.select(css).first();
                        String tmpAttr = tmpEl.attr(attr);
                        tmpAttr =tmpAttr.replaceAll(value.substring(1), "");
                        tmpEl.attr(attr, tmpAttr);
                    }else{
                        doc.select(css).first().attr(attr, value);
                    }
                }else {
                    doc.select(css).first().attr(attr, "");
                }
                break;
            case "class":
                if (null!=value && !value.isEmpty()) {
                    if (value.startsWith("+")) {
                        doc.select(css).first().addClass(value.substring(1));
                    }else if (value.startsWith("-")){
                        doc.select(css).first().removeClass(value.substring(1));
                    }else{
                        doc.select(css).first().attr("class", value);
                    }
                }else{
                    doc.select(css).attr(attr, "");
                }
                break;
            case "removeElement":
                doc.select(css).remove();
                break;
        }
    }
}
