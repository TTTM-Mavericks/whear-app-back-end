package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.*;
import com.tttm.Whear.App.enums.*;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.ClothesRepository;
import com.tttm.Whear.App.service.*;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.PostResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {

  private final ClothesRepository clothesRepository;
  private final ClothesImageService clothesImageService;
  private final ClothesSizeService clothesSizeService;
  private final ClothesColorService clothesColorService;
  private final ClothesSeasonService clothesSeasonService;
  private final PostService postService;

  @Override
  public ClothesResponse createClothes(ClothesRequest clothesRequest) throws CustomException {
    if (clothesRequest.getUserID() == null
        || clothesRequest.getUserID().isBlank()
        || clothesRequest.getUserID().isEmpty()) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    try {
      PostRequest postRequest = PostRequest.builder()
          .userID(clothesRequest.getUserID())
          .date(new Date())
          .hashtag(clothesRequest.getHashtag())
          .typeOfPosts(TypeOfPosts.CLOTHES)
          .status(StatusGeneral.ACTIVE)
          .build();
      PostResponse post = postService.createPost(postRequest);

      if (post == null) {
        throw new CustomException(ConstantMessage.CREATE_FAIL.getMessage());
      }

      Clothes clothes = Clothes.builder()
          .clothesID(post.getPostID())
          .posts(postService.getPostEntityByPostID(post.getPostID()))
          .nameOfProduct(clothesRequest.getNameOfProduct())
          .typeOfClothes(ClothesType.valueOf(clothesRequest.getTypeOfClothes().toUpperCase()))
          .shape(ShapeType.valueOf(clothesRequest.getShape().toUpperCase()))
          .description(clothesRequest.getDescription())
          .link(clothesRequest.getLink())
          .rating(clothesRequest.getRating())
          .materials(MaterialType.valueOf(clothesRequest.getMaterials().toUpperCase()))
          .build();

      clothesRepository.insertClothes(
          post.getPostID(), clothesRequest.getDescription(), clothesRequest.getLink(),
          clothesRequest.getMaterials(), clothesRequest.getNameOfProduct(),
          clothesRequest.getRating(), clothesRequest.getShape(),
              clothesRequest.getTypeOfClothes()
      );
      Clothes newClothes = clothesRepository.getClothesByClothesID(post.getPostID());
      if (newClothes == null) {
        throw new CustomException(ConstantMessage.CREATE_FAIL.getMessage());
      }

      List<String> clothesImages = clothesRequest.getClothesImages();
      if (clothesImages != null && !clothesImages.isEmpty() && clothesImages.size() > 0) {
        for (String image : clothesImages) {
          clothesImageService.createImage(post.getPostID(), image);
        }
      }

      List<String> clothesSizes = clothesRequest.getClothesSizes();
      if (clothesSizes != null && !clothesSizes.isEmpty() && clothesSizes.size() > 0) {
        for (String size : clothesSizes) {
          ClothesSize finded = clothesSizeService.findByName(post.getPostID(), size);
          if (finded == null) {
            clothesSizeService.createSize(post.getPostID(), size);
          }
        }
      }

      List<String> clothesSeasons = clothesRequest.getClothesSeasons();
      if (clothesSeasons != null && !clothesSeasons.isEmpty() && clothesSeasons.size() > 0) {
        for (String season : clothesSeasons) {
          ClothesSeason finded = clothesSeasonService.findByName(post.getPostID(), season);
          if (finded == null) {
            clothesSeasonService.createSeason(post.getPostID(), season);
          }
        }
      }

      List<String> clothesColors = clothesRequest.getClothesColors();
      if (clothesColors != null && !clothesColors.isEmpty() && clothesColors.size() > 0) {
        for (String color : clothesColors) {
          ClothesColor finded = clothesColorService.findByName(post.getPostID(), color);
          if (finded == null) {
            clothesColorService.createColor(post.getPostID(), color);
          }
        }
      }

      return mapToClothesResponse(newClothes);

    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public List<ClothesResponse> getAllClothes() {
    List<Clothes> clothesList = clothesRepository.findAll();
    List<ClothesResponse> responseList = null;
    if (clothesList != null) {
      if (responseList == null) {
        responseList = new ArrayList<>();
      }
      for (Clothes clothe : clothesList) {
        responseList.add(mapToClothesResponse(clothe));
      }
    }
    return responseList;
  }

  @Override
  public ClothesResponse getClothesByID(Integer clothesID) throws CustomException {
    if (clothesID == null || clothesID.toString().isEmpty() || clothesID.toString().isBlank()) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Clothes clothes = clothesRepository.getClothesByClothesID(clothesID);
    if (clothes == null) {
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    return mapToClothesResponse(clothes);
  }

  public ClothesResponse mapToClothesResponse(Clothes clothes) {

    List<String> clothesImages = clothesImageService
            .getAllImageOfClothes(clothes.getClothesID())
            .stream()
            .map(clothesImage -> clothesImage.getImageUrl().toString())
            .toList();

    List<String> clothesSizes = clothesSizeService
            .getAllSizeOfClothes(clothes.getClothesID())
            .stream()
            .map(clothesSize -> clothesSize.getClothesSizeKey().getSize().name())
            .toList();

    List<String> clothesColors = clothesColorService
            .getAllColorOfClothes(clothes.getClothesID())
            .stream()
            .map(clothesColor -> clothesColor.getClothesColorKey().getColor().name())
            .toList();

    List<String> clothesSeasons = clothesSeasonService
            .getAllSeasonOfClothes(clothes.getClothesID())
            .stream()
            .map(clothesSeason -> clothesSeason.getClothesSeasonKey().getSeason().name())
            .toList();

    ClothesResponse response = ClothesResponse.builder()
        .clothesID(clothes.getClothesID())
        .nameOfProduct(clothes.getNameOfProduct())
        .typeOfClothes(clothes.getTypeOfClothes().name())
        .shape(clothes.getShape().name())
        .description(clothes.getDescription())
        .link(clothes.getLink())
        .rating(clothes.getRating())
        .materials(clothes.getMaterials().name())
        .clothesSeasons(clothesSeasons)
        .clothesImages(clothesImages)
        .clothesSizes(clothesSizes)
        .clothesColors(clothesColors)
        .build();
    return response;
  }
}
