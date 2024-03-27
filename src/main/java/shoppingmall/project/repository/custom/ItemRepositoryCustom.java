package shoppingmall.project.repository.custom;


import shoppingmall.project.domain.dto.*;

import java.util.List;

public interface ItemRepositoryCustom{
    List<BookAndFileDto> findBookWithUploadFile();
    List<ClothesAndFileDto> findClothesWithUploadFile();
    List<ElectronicsAndFileDto> findElectronicsWithUploadFile();
    List<FoodAndFileDto> findFoodWithUploadFile();


}
