package shoppingmall.project.domain.apidto;

import lombok.Data;

/**
 * item을 dto로 변환
 */
@Data
public class ItemApiDto {
    private Long id;
    private String name;

    private int price;
    private int quantity;
    private String dtype;
}
