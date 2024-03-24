package shoppingmall.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.item.*;
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
import java.util.UUID;

@Service
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

    public void saveBook(BookForm bookForm) throws IOException {
        UploadFile attachFile = storeFile(bookForm.getAttachFile());

        Item book = new Book(bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getQuantity(),
                bookForm.getIsbn(),
                bookForm.getAuthor());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), book);
        fileRepository.save(uploadFile);
        itemRepository.save(book);
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
        fileRepository.save(uploadFile);

        itemRepository.save(clothes);
    }

    public void saveFood(FoodForm foodForm) throws IOException {
        UploadFile attachFile = storeFile(foodForm.getAttachFile());
        Item food = new Food(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), food);
        fileRepository.save(uploadFile);
        itemRepository.save(food);
    }

    public void saveElectronics(ElectronicsForm electronicsForm) throws IOException {
        UploadFile attachFile = storeFile(electronicsForm.getAttachFile());
        Item electronics = new Electronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), electronics);
        fileRepository.save(uploadFile);
        itemRepository.save(electronics);
    }





    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    private UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        String fullPath = fileDir + storeFileName;

        // 파일 저장
        Path path = Paths.get(fullPath);
        Files.write(path, multipartFile.getBytes());
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);

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


}
