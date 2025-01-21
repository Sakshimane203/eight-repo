package com.example.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private static final Map<String, String> DOCUMENTATION_URLS = new HashMap<>();

    static {
        DOCUMENTATION_URLS.put("segment", "https://segment.com/docs");
        DOCUMENTATION_URLS.put("mparticle", "https://docs.mparticle.com");
        DOCUMENTATION_URLS.put("lytics", "https://docs.lytics.com");
        DOCUMENTATION_URLS.put("zeotap", "https://docs.zeotap.com/home/en-us/");
    }

    @PostMapping("/ask")
    public Map<String, Object> askChatbot(@RequestBody Map<String, String> request) {
        String platform = request.get("platform").toLowerCase();
        String question = request.get("question");

        Map<String, Object> response = new HashMap<>();
        if (!DOCUMENTATION_URLS.containsKey(platform)) {
            response.put("error", "Platform not supported.");
            return response;
        }

        String url = DOCUMENTATION_URLS.get(platform);
        try {
            // Fetch and parse the documentation
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            // Search for the query
            List<Map<String, String>> results = new ArrayList<>();
            for (Element link : links) {
                if (link.text().toLowerCase().contains(question.toLowerCase())) {
                    Map<String, String> result = new HashMap<>();
                    result.put("title", link.text());
                    result.put("url", link.absUrl("href"));
                    results.add(result);
                }
            }

            if (results.isEmpty()) {
                response.put("message", "No results found.");
            } else {
                response.put("results", results);
            }
        } catch (IOException e) {
            response.put("error", "Failed to fetch documentation.");
        }

        return response;
    }
}