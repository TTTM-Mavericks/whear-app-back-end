package com.tttm.Whear.App.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.*;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.request.UserRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TextFileReader {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    private final ClothesService clothesService;
    private final GenerateDataService generateDataService;
    private final PostService postService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            String run = null;
//            String run = "run";
            if (run != null) {
                readUsersFromFile();
//                readPostFromFile();
                readClothesFromFile();
            }
        } catch (IOException | CustomException e) {
            e.printStackTrace();
        }
    }


    private void readUsersFromFile() throws IOException, CustomException {
        List<UserRequest> userRequests = readUsersRequests("classpath:data/users.txt");
        for (UserRequest userRequest : userRequests) {
            authenticationService.register(userRequest);
            System.out.println(userRequests);
        }
    }

    private List<UserRequest> readUsersRequests(String filePath) throws IOException {
        List<UserRequest> userRequests = new ArrayList<>();
        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("--------------------------------------")) {
                    if (stringBuilder.length() > 0) {
                        userRequests.add(convertToUsersRequest(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                } else {
                    stringBuilder.append(line);
                }
            }
            // Add the last clothes request if there is any remaining data
            if (stringBuilder.length() > 0) {
                userRequests.add(convertToUsersRequest(stringBuilder.toString()));
            }
        }
        return userRequests;
    }

    private UserRequest convertToUsersRequest(String json) throws IOException {
        return objectMapper.readValue(json, UserRequest.class);
    }

    private void readClothesFromFile() throws IOException, CustomException {
        List<ClothesRequest> clothesRequests = readClothesRequests("classpath:data/clothes.txt");
        for (ClothesRequest clothesRequest : clothesRequests) {
            clothesService.createClothes(clothesRequest);
            System.out.println(clothesRequest);
        }
    }

    private List<ClothesRequest> readClothesRequests(String filePath) throws IOException {
        List<ClothesRequest> clothesRequests = new ArrayList<>();
        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("--------------------------------------")) {
                    if (stringBuilder.length() > 0) {
                        clothesRequests.add(convertToClothesRequest(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                } else {
                    stringBuilder.append(line);
                }
            }
            // Add the last clothes request if there is any remaining data
            if (stringBuilder.length() > 0) {
                clothesRequests.add(convertToClothesRequest(stringBuilder.toString()));
            }
        }
        return clothesRequests;
    }

    private ClothesRequest convertToClothesRequest(String json) throws IOException {
        return objectMapper.readValue(json, ClothesRequest.class);
    }

    private void readPostFromFile() throws IOException, CustomException {
        List<PostRequest> postRequests = readPostRequests("classpath:data/posts.txt");
        for (PostRequest postRequest : postRequests) {
            postService.createPost(postRequest);
            System.out.println(postRequests);
        }
    }

    private List<PostRequest> readPostRequests(String filePath) throws IOException {
        List<PostRequest> postRequests = new ArrayList<>();
        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("--------------------------------------")) {
                    if (stringBuilder.length() > 0) {
                        postRequests.add(convertToPostsRequest(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                } else {
                    stringBuilder.append(line);
                }
            }
            // Add the last clothes request if there is any remaining data
            if (stringBuilder.length() > 0) {
                postRequests.add(convertToPostsRequest(stringBuilder.toString()));
            }
        }
        return postRequests;
    }

    private PostRequest convertToPostsRequest(String json) throws IOException {
        return objectMapper.readValue(json, PostRequest.class);
    }
}