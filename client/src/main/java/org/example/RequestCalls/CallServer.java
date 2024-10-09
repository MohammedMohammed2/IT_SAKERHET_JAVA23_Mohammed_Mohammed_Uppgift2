package org.example.RequestCalls;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class CallServer {
    static String baseUrl = "http://localhost:8080";
    private static  HttpClient httpClient;

    public static void registerUser(String email, String password, String role) throws URISyntaxException {

        Transcript transcript = new Transcript();
        transcript.setEmail(email);
        transcript.setPassword(password);
        transcript.setRole(role);
        Gson gson = new Gson();
        String jsonTrascript = gson.toJson(transcript);


        System.out.println(jsonTrascript);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(baseUrl+"/register"))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonTrascript))
                .build();

         httpClient = HttpClient.newHttpClient();
        try {
            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean loginUser(String email, String password, String role) {

        Transcript getTranscript = new Transcript();
        getTranscript.setEmail(email);
        getTranscript.setPassword(password);
        getTranscript.setRole(role);
        Gson gson = new Gson();
        String jsonGetTrascript = gson.toJson(getTranscript);

        httpClient = HttpClient.newHttpClient();

        HttpResponse<String> res;
        try {
            HttpRequest loginUser = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/login"))
                    .header("Content-Type", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.ofString(jsonGetTrascript))
                    .build();

            res = httpClient.send(loginUser, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {

            throw new RuntimeException(e);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }

        if (res == null) {
            return false;
        } else {
            return true;
        }
    }
    public static void sendMessageCall(String message){
        Transcript transcript = new Transcript();
        transcript.setMessage(message);
        Gson gson = new Gson();
        String jsonMessageTrascript = gson.toJson(transcript);


        System.out.println(jsonMessageTrascript);

        try {
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl+"/Message"))
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonMessageTrascript))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        httpClient = HttpClient.newHttpClient();
    }
}
