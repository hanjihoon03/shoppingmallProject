package shoppingmall.project.domain.apidto;

import lombok.Data;

/**
 * 실제 객체를 반환하지 않기 위한
 * item을 api에 필요한 dto로 변환
 */
@Data
public class ItemApiDto {
    private Long id;
    private String name;

    private int price;
    private int quantity;
    private String dtype;
}
