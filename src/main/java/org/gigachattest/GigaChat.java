package org.gigachattest;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class GigaChat {
    @Value("${gigachat.secret}")
    private final String auth;

    @Value("${gigachat.rqUID}")
    private final String rqUID;

    @Value("${gigachat.authUrl}")
    private String authUrl;

    @Value("${gigachat.genUrl}")
    private String genUrl;
    private String access_token;
    private final List<Communication> communication;

    public GigaChat(String auth, String rqUID) {
        this.auth = auth;
        this.rqUID = rqUID;
        this.getToken();
        this.communication = new ArrayList<>();
    }

    private void getToken() {
        String url = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.auth);
        headers.set("RqUID", this.rqUID);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("scope", "GIGACHAT_API_PERS");

        ResponseEntity<String> response = sendPostRequest(url, headers, data);
        this.access_token = Objects.requireNonNull(JsonPath.read(response.getBody(), "$.access_token"));
    }

    public String askQuestion(String question, double temperature) {
        String url = "https://gigachat.devices.sberbank.ru/api/vl/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.access_token);

        this.communication.add(new Communication("user", question));

        Map<String, Object> data = new HashMap<>();
        data.put("model", "GigaChat:latest");
        data.put("messages", this.communication);
        data.put("temperature", temperature);

        ResponseEntity<String> response = sendPostRequest(url, headers, data);
        String content = Objects.requireNonNull(JsonPath.read(response.getBody(), "$.choices[0].message.content"));

        this.communication.add(new Communication("assistant", content));
        return content;
    }

    public void reset() {
        this.communication.clear();
    }

    private ResponseEntity<String> sendPostRequest(String url, HttpHeaders headers, Object data) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }
}

