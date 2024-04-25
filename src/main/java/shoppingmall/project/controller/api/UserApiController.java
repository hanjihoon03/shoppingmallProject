package shoppingmall.project.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.apiresponse.CustomErrorResponse;
import shoppingmall.project.domain.apidto.UserDto;
import shoppingmall.project.domain.apidto.UserLoginIdPwDto;
import shoppingmall.project.domain.apidto.save.FoodSaveApiDto;
import shoppingmall.project.domain.apidto.save.UserSaveDto;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.service.api.UserApiService;

import java.util.List;

@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserApiService userApiService;

    @Operation(
            summary = "Save User",
            description = "회원가입하는 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 회원가입이 되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSaveDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버의 상태가 올바르지 않습니다",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/signUp")
    public UserDto saveUser(@RequestBody UserSaveDto request) {
        UserDto userDto = userApiService.saveUser(request);

        return ResponseEntity.ok()
                .body(userDto).getBody();
    }

    @Operation(
            summary = "find User LoginId, Password",
            description = "user의 아이디와 패스워드를 찾는 API /api/users/userinfo?name={사용자 이름}&email={사용자 이메일}"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 아이디와 비밀번호를 찾았습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginIdPwDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이름이나 이메일이 틀렸습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @GetMapping("/api/users/userinfo")
    public ResponseEntity<UserLoginIdPwDto> findLoginIdAndPassword(
            @RequestParam String name,
            @RequestParam String email) {
        UserLoginIdPwDto loginIdPwDto = userApiService.findByNameAndEmail(name, email);
        return ResponseEntity.ok().body(loginIdPwDto);
    }


    @Operation(
            summary = "find User TierAndAge",
            description = "user의 tier와 입력한 age보다 큰 user 반환 tier: BRONZE(0.05), SILVER(0.07), GOLD(0.1), NORMAL(0) 중 선택해야한다." +
                    "API /api/users/tier/age?tier={tier}&email={나이}"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 데이터를 찾았습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "해당하는 tier가 없거나 age가 없습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @GetMapping("/api/users/tier/age")
    public List<UserDto> userTierAndAge(@RequestParam Tier tier, @RequestParam int age) {
        List<UserDto> userTierAndAge = userApiService.findUserTierAndAge(tier, age);
        return ResponseEntity.ok().body(userTierAndAge).getBody();
    }
}
