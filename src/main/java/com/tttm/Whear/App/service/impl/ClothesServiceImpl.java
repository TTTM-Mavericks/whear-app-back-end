package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Clothes;
import com.tttm.Whear.App.entity.ClothesColor;
import com.tttm.Whear.App.entity.ClothesImage;
import com.tttm.Whear.App.entity.ClothesSize;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.ClothesRepository;
import com.tttm.Whear.App.service.ClothesColorService;
import com.tttm.Whear.App.service.ClothesImageService;
import com.tttm.Whear.App.service.ClothesService;
import com.tttm.Whear.App.service.ClothesSizeService;
import com.tttm.Whear.App.service.PostService;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.PostResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClothesServiceImpl implements ClothesService {

  private final ClothesRepository clothesRepository;
  private final ClothesImageService clothesImageService;
  private final ClothesSizeService clothesSizeService;
  private final ClothesColorService clothesColorService;
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
          .typeOfClothes(clothesRequest.getTypeOfClothes())
          .shape(clothesRequest.getShape())
          .seasons(clothesRequest.getSeasons())
          .description(clothesRequest.getDescription())
          .link(clothesRequest.getLink())
          .rating(clothesRequest.getRating())
          .materials(clothesRequest.getMaterials())
          .build();
      clothesRepository.insertClothes(
          post.getPostID(), clothesRequest.getDescription(), clothesRequest.getLink(),
          clothesRequest.getMaterials(), clothesRequest.getNameOfProduct(),
          clothesRequest.getRating(), clothesRequest.getSeasons().toString(),
          clothesRequest.getShape(), clothesRequest.getTypeOfClothes()
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

    List<ClothesImage> clothesImages = clothesImageService.getAllImageOfClothes(
        clothes.getClothesID());

    List<ClothesSize> clothesSizes = clothesSizeService.getAllSizeOfClothes(clothes.getClothesID());

    List<ClothesColor> clothesColors = clothesColorService.getAllColorOfClothes(
        clothes.getClothesID());

    ClothesResponse response = ClothesResponse.builder()
        .clothesID(clothes.getClothesID())
        .nameOfProduct(clothes.getNameOfProduct())
        .typeOfClothes(clothes.getTypeOfClothes())
        .shape(clothes.getShape())
        .seasons(clothes.getSeasons())
        .description(clothes.getDescription())
        .link(clothes.getLink())
        .rating(clothes.getRating())
        .materials(clothes.getMaterials())
        .clothesImages(clothesImages)
        .clothesSizes(clothesSizes)
        .clothesColors(clothesColors)
        .build();
    return response;
  }
}
