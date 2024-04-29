package shoppingmall.paging;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shoppingmall.project.ProjectApplication;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.repository.FileRepository;
import shoppingmall.project.repository.ItemRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = ProjectApplication.class )
@Slf4j
public class PagingTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void pageTest() {
        //given
        Pageable pageable = PageRequest.of(1,30);
        //when
        Page<BookAndFileDto> result = itemRepository.pagingBook(pageable);
        log.info("page.getTotalPages={}",result.getTotalPages());
        log.info("page.size={}",result.getSize());
        //then
        assertThat(result.getTotalPages()).isEqualTo(10);
        assertThat(result.getSize()).isEqualTo(30);
    }
}
