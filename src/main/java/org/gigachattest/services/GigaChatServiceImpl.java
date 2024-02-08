package org.gigachattest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GigaChatServiceImpl implements GigaChatService {
    @Value("${gigachat.clientSecret}")
    private String clientSecret;

    @Value("${gigachat.clientId}")
    private String clientId;

    @Value("${gigachat.scope}")
    private String scope;

    @Value("${gigachat.authUrl}")
    private String authUrl;

    @Value("${gigachat.modelUrl}")
    private String modelUrl;

    @Value("${gigachat.model}")
    private String model;

    @Value("${gigachat.genUrl}")
    private String genUrl;

    @Value("${gigachat.urlencoded}")
    private String urlencoded;

    @Value("${gigachat.json}")
    private String json;

    public String getAccessToken() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String authorization = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
        String rqUID = UUID.randomUUID().toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header("Authorization", "Basic " + authorization)
                .header("RqUID", rqUID)
                .header("Content-Type", urlencoded)
                .header("Accept", json)
                .POST(HttpRequest.BodyPublishers.ofString("scope=" + scope))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body().substring(response.body().indexOf("access_token=") + 18, response.body().length() - 29);
    }

    public String getModel() throws IOException, InterruptedException {
        String token = getAccessToken();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(modelUrl))
                .header("Accept", json)
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        int startIndex = body.indexOf(model);
        if (startIndex != -1) {
            model = body.substring(startIndex, startIndex + model.length());
        } else {
            throw new IOException();
        }

        return model;
    }

    public String getAnswer(String content) throws IOException, InterruptedException {
        String token = getAccessToken();
        String model = getModel();
        HttpClient client = HttpClient.newHttpClient();

        String jsonPayload = String.format("""
            {
              "model": "%s",
              "messages": [
                {
                  "role": "system",
                  "content": "Перескажи этот текст своими словами, сохраняя его основной смысл и структуру."
                },
                {
                  "role": "user",
                  "content": "%s"
                }
              ],
              "temperature": 0.7
            }""", model, content);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(genUrl))
                .header("Accept", json)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", json)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body().substring(response.body().indexOf("choices") + 33, response.body().length() - 224);
    }
}