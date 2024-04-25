admin 인터셉터를 추가했다.
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에서 사용자 정보를 가져옵니다.
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionConst.ADMIN);
        log.info("user={}", user.getLoginId());

        // 사용자 정보가 없거나 사용자가 관리자(admin)가 아닌 경우
        if (user == null || !user.getLoginId().equals("admin")) {
            response.sendRedirect("/home"); // 관리자가 아닌 경우 홈페이지로 리다이렉트
            return false; // 접근을 막음
        }

        return true; // 관리자인 경우 접근 허용
    }
}
admin에 따라 데이터를 추가하고 삭제하고 수정할 페이지를 분리했다.


API 명세를 위해 springdoc을 사용하기 위한 config와 설정, 컨트롤러와 로직을 작성했다.
자세한 springdoc 사용법은 아래에 링크에서 확인해주세요.

https://springdoc.org/#migrating-from-springfox


@OpenAPIDefinition(
        info = @Info(title = "Project API 명세",
        description = "SIMPLE API",
                version = "v1"
        ))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("API v1")  // 그룹 이름을 설정한다.
                .pathsToMatch(paths) // 그룹에 속하는 경로 패턴을 지정한다.
                .build();
    }
}

@Tag(name = "Item", description = "Item API")
@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemApiService itemApiService;

    @Operation(
            summary = "Item",
            description = "모든 아이템을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/item")
    public List<ItemApiDto> allItemSearch() {
        return itemApiService.findAllItem();
    }
    ...
    }
    
