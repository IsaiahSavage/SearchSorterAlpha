/**
* SearchFilter
* Description: filters Google search results by domain type using Jsoup.
* @author Isaiah Savage 2021
*/

package com.searchsorteralpha;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchFilter {
    
    private final List<String> allowedDomainTypes; // Whitelisted domain types
    private int numResults = 10; // Number of results to be searched
    private String query; // Query input by user

    
    public SearchFilter(String query, List<String> allowedDomainTypes, int numResults) {
        this.query = query;
        this.allowedDomainTypes = allowedDomainTypes;
        this.numResults = numResults;
    }
    
    private void parseQuery() throws Exception {
        System.out.println("Searching for: " + query);
        query = query.trim();
        query = URLEncoder
                .encode(query, StandardCharsets.UTF_8.toString());
        query = "https://www.google.com/search?q=" + query + "&num=" 
                + numResults;
    }
    
    private List<String> parseLinks(Document doc) throws Exception {
        List<String> result = new ArrayList<>();
        Elements links = doc.select("a > h3");
        for (Element link : links) {
            Elements parent = link.parent().getAllElements();
            String relHref = parent.attr("href");
            if(relHref.startsWith("/url?q=")) {
                relHref = relHref.replace("/url?q=", "");
            }
            String[] splitString = relHref.split("&sa=");
            if(splitString.length > 1) {
                relHref = splitString[0];
            }
            result.add(relHref);
        }
        return result;  
    }
    
    // Filters links based on domain type
    private List<String> filterLinks (List<String> links) {
        int initialLength = links.size();
        // i: regular counter | removed: num of removed items to account for index offset
        for (int i = 0, removed = 0; i < initialLength; i++) {
            boolean isAllowed = false;
            for (String s : allowedDomainTypes) {
                if (links.get(i - removed).contains("." + s)) {
                    isAllowed = true;
                    break;
                }
            }
            if (!isAllowed) {
                links.remove(i - removed);
                removed++;
            }
        }
        return links;
    }
    
    public List<String> search() throws Exception {
        parseQuery();
        Document doc = Jsoup.connect(query).get();
        List<String> results = parseLinks(doc);
        results = filterLinks(results);
        return results;
    }    
}
