package shoppingmall.project.repository.custom;


import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;

import java.util.List;

public interface ItemRepositoryCustom{
    List<BookAndFileDto> findBookWithUploadFile();
    List<ClothesAndFileDto> findClothesWithUploadFile();
    List<ElectronicsAndFileDto> findElectronicsWithUploadFile();
    List<FoodAndFileDto> findFoodWithUploadFile();


}
