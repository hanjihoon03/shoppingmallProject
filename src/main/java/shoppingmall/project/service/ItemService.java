package shoppingmall.project.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.*;

import shoppingmall.project.exception.FileStorageException;
import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.repository.FileRepository;
import shoppingmall.project.repository.ItemRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;


    @Value("${file.dir}")
    private String fileDir;

    public void saveItem(Item item){
        itemRepository.save(item);
    }
    @Transactional(readOnly = true)
    public Item findById(Long id) {
        Optional<Item> byId = itemRepository.findById(id);
        return byId.orElse(null);
    }

    //아이템보다 파일을 먼저 저장해서 파일의 아이템 아이디가 비어서 업데이트 쿼리가 한 방 더 나가는 문제를 아이템을 먼저 저장해서 해결
    public void saveBook(BookForm bookForm) throws IOException {

            UploadFile attachFile = storeFile(bookForm.getAttachFile());

            Item book = new Book(bookForm.getName(),
                    bookForm.getPrice(),
                    bookForm.getQuantity(),
                    bookForm.getIsbn(),
                    bookForm.getAuthor());
            UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), book);
            itemRepository.save(book);
            fileRepository.save(uploadFile);
    }

    public void saveClothes(ClothesForm clothesForm) throws IOException {
        UploadFile attachFile = storeFile(clothesForm.getAttachFile());
        Item clothes = new Clothes(
                clothesForm.getName(),
                clothesForm.getPrice(),
                clothesForm.getQuantity(),
                clothesForm.getClothesType(),
                clothesForm.getBrand(),
                clothesForm.getSize());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), clothes);

        itemRepository.save(clothes);
        fileRepository.save(uploadFile);
    }

    public void saveFood(FoodForm foodForm) throws IOException {
        UploadFile attachFile = storeFile(foodForm.getAttachFile());
        Item food = new Food(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), food);
        itemRepository.save(food);
        fileRepository.save(uploadFile);
    }

    public void saveElectronics(ElectronicsForm electronicsForm) throws IOException {
        UploadFile attachFile = storeFile(electronicsForm.getAttachFile());
        Item electronics = new Electronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), electronics);
        itemRepository.save(electronics);
        fileRepository.save(uploadFile);
    }

    @Transactional(readOnly = true)
    public List<BookAndFileDto> findAllBook() {
        return itemRepository.findBookWithUploadFile();
    }
    public Book findOneBook(Long id) {
        return itemRepository.findBook(id);
    }
    @Transactional(readOnly = true)
    public List<ClothesAndFileDto> findAllClothes() {
        return itemRepository.findClothesWithUploadFile();
    }
    public Clothes findOneClothes(Long id) {
        return itemRepository.findClothes(id);
    }
    @Transactional(readOnly = true)
    public List<ElectronicsAndFileDto> findAllElectronics() {
        return itemRepository.findElectronicsWithUploadFile();
    }
    public Electronics findOneElectronics(Long id) {
        return itemRepository.findElectronics(id);
    }
    @Transactional(readOnly = true)
    public List<FoodAndFileDto> findAllFood() {
        return itemRepository.findFoodWithUploadFile();
    }
    public Food findOneFood(Long id) {
        return itemRepository.findFood(id);
    }


    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public void updateBook(Long bookId, BookForm bookForm) throws IOException {
        Book book = itemRepository.findBook(bookId);
        book.updateBook(
                bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getQuantity(),
                bookForm.getIsbn(),
                bookForm.getAuthor());

        UploadFile attachFile = storeFile(bookForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(bookId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }
    public void updateClothes(Long clothesId, ClothesForm clothesForm) throws IOException {
        Clothes clothes = itemRepository.findClothes(clothesId);
        clothes.updateClothes(
                clothesForm.getName(),
                clothesForm.getPrice(),
                clothesForm.getQuantity(),
                clothesForm.getClothesType(),
                clothesForm.getBrand(),
                clothesForm.getSize()
        );
        UploadFile attachFile = storeFile(clothesForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(clothesId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }

    public void updateElectronics(Long electronicsId, ElectronicsForm electronicsForm) throws IOException {
        Electronics electronics = itemRepository.findElectronics(electronicsId);
        electronics.updateElectronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand()
        );

        UploadFile attachFile = storeFile(electronicsForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(electronicsId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }
    public void updateFood(Long foodId, FoodForm foodForm) throws IOException {

        Food food = itemRepository.findFood(foodId);
        food.updateFood(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand()
        );
        UploadFile attachFile = storeFile(foodForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(foodId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }



    private UploadFile storeFile(MultipartFile multipartFile) throws FileStorageException {
        try {

            if (multipartFile == null || multipartFile.isEmpty()) {
                return null;
            }

            String originalFilename = multipartFile.getOriginalFilename();


            if (originalFilename == null) {
                throw new IllegalArgumentException("Original filename is null.");
            }

            String storeFileName = createStoreFileName(originalFilename);
            String fullPath = fileDir + storeFileName;

            Path path = Paths.get(fullPath);


            byte[] fileBytes = multipartFile.getBytes();
            if (fileBytes == null) {
                throw new IllegalStateException("Byte array is null.");
            }

            Files.write(path, fileBytes);
            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return new UploadFile(originalFilename, storeFileName);
        } catch (IOException e) {
            log.error("Error occurred while storing file: {}", e.getMessage());
            throw new FileStorageException("Failed to store file.", e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // 명시적인 null 체크 예외를 잡아서 로그에 기록
            log.error("Error occurred: {}", e.getMessage());
            return null;
        }

    }

    /**
     * 서버 내부에서 관리하는 파일명은 유일한 이름을 생성
     */
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    /**
     *확장자를 별도로 추출해서 서버 내부에서 관리하는 파일명에도 붙여준다.
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void clear() {
        itemRepository.deleteAll();
    }
    public void deleteItemByItemId(Long id) {
        itemRepository.deleteById(id);
    }
    public void deleteFileByItemId(Long id) {
        fileRepository.deleteFromItemId(id);
    }


}
