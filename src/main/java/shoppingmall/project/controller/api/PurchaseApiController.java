package shoppingmall.project.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.service.api.PurchaseApiService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseApiController {

    private final PurchaseApiService purchaseApiService;

    @GetMapping("/api/purchase/{id}")
    public ResponseEntity<List<UserPurchaseDto>> userPurchase(@PathVariable Long id) {
        List<UserPurchaseDto> userPurchaseDtos = purchaseApiService.purchaseList(id);

        return ResponseEntity.ok()
                .body(userPurchaseDtos);
    }
}
