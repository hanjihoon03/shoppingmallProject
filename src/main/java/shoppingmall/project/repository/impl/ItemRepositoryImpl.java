package shoppingmall.project.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.repository.custom.ItemRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QClothes.*;
import static shoppingmall.project.domain.item.QElectronics.*;
import static shoppingmall.project.domain.item.QFood.*;


@Repository

@Transactional
public class ItemRepositoryImpl implements ItemRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     *         List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();
     *         이 코드를 없애고 영속성 컨텍스트 안에 있을 uploadfiles를 사용해서 쿼리를 한 방만 나가게 수정해야함
     *         수정하면 initializeUploadFileNameBook 메서드 삭제 가능
     *         dto book 필드 먼저 설정 후 uploadFiles 필드 dto에 넣기 iter로 if(book.id = uploadfile.item.id)
     */
    @Override
    public List<BookAndFileDto> findBookWithUploadFile() {
        List<Book> books = queryFactory
                .selectFrom(book)
                .leftJoin(book.uploadFiles).fetchJoin()
                .fetch();

        List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();

        List<BookAndFileDto> dtos = new ArrayList<>();
        for (Book book : books) {
            BookAndFileDto dto = new BookAndFileDto(
                    book.getId(),
                    book.getIsbn(),
                    book.getAuthor(),
                    book.getName(),
                    book.getPrice(),
                    book.getQuantity(),
                    initializeUploadFileNameBook(book,uploadFiles), // uploadFileName 초기화 메소드 호출
                    initializeStoreFileNameBook(book,uploadFiles)  // storeFileName 초기화 메소드 호출
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<ClothesAndFileDto> findClothesWithUploadFile() {
        List<Clothes> clothe = queryFactory
                .selectFrom(clothes)
                .leftJoin(clothes.uploadFiles).fetchJoin()
                .fetch();

        List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();

        List<ClothesAndFileDto> dtos = new ArrayList<>();
        for (Clothes clothes : clothe) {
            ClothesAndFileDto dto = new ClothesAndFileDto(
                    clothes.getId(),
                    clothes.getName(),
                    clothes.getPrice(),
                    clothes.getQuantity(),
                    initializeUploadFileNameClothes(clothes,uploadFiles),
                    initializeStoreFileNameClothes(clothes,uploadFiles),
                    clothes.getClothesType(),
                    clothes.getBrand(),
                    clothes.getSize()
            );
            dtos.add(dto);

        }
        return dtos;
    }

    @Override
    public List<ElectronicsAndFileDto> findElectronicsWithUploadFile() {
        List<Electronics> electronic = queryFactory
                .selectFrom(electronics)
                .leftJoin(electronics.uploadFiles).fetchJoin()
                .fetch();

        List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();

        List<ElectronicsAndFileDto> dtos = new ArrayList<>();
        for (Electronics electronics : electronic) {
            ElectronicsAndFileDto dto = new ElectronicsAndFileDto(
                    electronics.getId(),
                    electronics.getName(),
                    electronics.getPrice(),
                    electronics.getQuantity(),
                    electronics.getBrand(),
                    initializeUploadFileNameElectronics(electronics,uploadFiles),
                    initializeStoreFileNameElectronics(electronics,uploadFiles)
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<FoodAndFileDto> findFoodWithUploadFile() {
        List<Food> foods = queryFactory
                .selectFrom(food)
                .leftJoin(food.uploadFiles).fetchJoin()
                .fetch();

        List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();

        List<FoodAndFileDto> dtos = new ArrayList<>();

        for (Food food : foods) {
            FoodAndFileDto dto = new FoodAndFileDto(
                    food.getId(),
                    food.getName(),
                    food.getPrice(),
                    food.getQuantity(),
                    food.getBrand(),
                    initializeUploadFileNameFood(food,uploadFiles),
                    initializeStoreFileNameFood(food,uploadFiles)
            );

            dtos.add(dto);
        }

        return dtos;
    }

    private String initializeUploadFileNameBook(Book book, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
            if (book.getId().equals(uploadFile.getId())) {
                return uploadFile.getUploadFileName();  // 첫 번째 파일명 반환
                 }
            }

            return null;
        }

        private String initializeStoreFileNameBook(Book book, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
                if (book.getId().equals(uploadFile.getId())) {
                    return uploadFile.getStoreFileName();  // 첫 번째 파일명 반환
                }
            }
            return null;
        }

        private String initializeUploadFileNameClothes(Clothes clothes, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
            if (clothes.getId().equals(uploadFile.getId())) {
                return uploadFile.getUploadFileName();  // 첫 번째 파일명 반환
                 }
            }

            return null;
        }

        private String initializeStoreFileNameClothes(Clothes clothes, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
                if (clothes.getId().equals(uploadFile.getId())) {
                    return uploadFile.getStoreFileName();  // 첫 번째 파일명 반환
                }
            }
            return null;
        }

        private String initializeUploadFileNameElectronics(Electronics electronics, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
            if (electronics.getId().equals(uploadFile.getId())) {
                return uploadFile.getUploadFileName();  // 첫 번째 파일명 반환
                 }
            }

            return null;
        }

        private String initializeStoreFileNameElectronics(Electronics electronics, List<UploadFile> uploadFiles){
            for (UploadFile uploadFile : uploadFiles) {
                if (electronics.getId().equals(uploadFile.getId())) {
                    return uploadFile.getStoreFileName();  // 첫 번째 파일명 반환
                }
            }
            return null;
        }

    private String initializeUploadFileNameFood(Food food, List<UploadFile> uploadFiles){
        for (UploadFile uploadFile : uploadFiles) {
            if (food.getId().equals(uploadFile.getId())) {
                return uploadFile.getUploadFileName();  // 첫 번째 파일명 반환
            }
        }

        return null;
    }

    private String initializeStoreFileNameFood(Food food, List<UploadFile> uploadFiles){
        for (UploadFile uploadFile : uploadFiles) {
            if (food.getId().equals(uploadFile.getId())) {
                return uploadFile.getStoreFileName();  // 첫 번째 파일명 반환
            }
        }
        return null;
    }
}
