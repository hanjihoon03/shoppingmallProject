package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.repository.api.UploadApiRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadApiService {

    private final UploadApiRepository uploadApiRepository;

    public void deleteUploadFileFromItemId(Long itemId) {
        uploadApiRepository.deleteFromItemId(itemId);
    }

}
