package com.works.restcontrollers;

import com.google.gson.Gson;
import com.works.props.NewsData;
import com.works.props.PostsDatas;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NewsRestController {

    @GetMapping("/news")
    public Map<String, Object> news() {
        Map<String, Object> hm = new HashMap<>();

        String url = "https://newsapi.org/v2/everything?q=bitcoin&from=2021-11-01&sortBy=publishedAt&apiKey=38a9e086f10b445faabb4461c4aa71f8";
        RestTemplate template = new RestTemplate();
        String stData = template.getForObject(url, String.class);
        Gson gson = new Gson();
        NewsData data = gson.fromJson(stData, NewsData.class);

        hm.put("news", data.getArticles());

        return hm;
    }


    @GetMapping("/post")
    public Map<String, Object> post() {
        Map<String, Object> hm = new HashMap<>();

        String url = "https://jsonplaceholder.typicode.com/posts";
        RestTemplate template = new RestTemplate();
        String stData = template.getForObject(url, String.class);

        Gson gson = new Gson();
        List<PostsDatas> data = gson.fromJson(stData, List.class);

        hm.put("posts", data);

        return hm;
    }

}
