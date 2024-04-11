package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.repository.FileRepository;

import java.nio.channels.Pipe;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    public UploadFile findUploadFileItemId(Long id) {
        return fileRepository.findByItemId(id);
    }

}
