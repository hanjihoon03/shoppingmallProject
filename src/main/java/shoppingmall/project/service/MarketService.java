package shoppingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.project.repository.MarketRepository;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
}
