package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shoppingmall.project.repository.MarketRepository;

@Controller
@RequiredArgsConstructor
public class MarketController {

    private final MarketRepository marketRepository;
}
