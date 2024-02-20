package com.tttm.Whear.App.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.ClothesService;
import com.tttm.Whear.App.service.GenerateDataService;
import com.tttm.Whear.App.utils.request.ClothesRequest;
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

    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
//            String run = null;
            String run = "run";
            if (run != null) {
                generateDataService.generateRandomCustomer();
                List<ClothesRequest> clothesRequests = readClothesRequests("classpath:data/clothes.txt");
                for (ClothesRequest clothesRequest : clothesRequests) {
                    clothesService.createClothes(clothesRequest);
                    System.out.println(clothesRequest);
                }
            }
        } catch (IOException | CustomException e) {
            e.printStackTrace();
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
}