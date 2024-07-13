package flab.gotable.controller;

import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.response.StoreDetailsResponseDto;
import flab.gotable.exception.ErrorCode;
import flab.gotable.exception.StoreNotFoundException;
import flab.gotable.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stores")
public class StoreAPIController {

    private final StoreService storeService;

    @GetMapping("/{id}")
    public ApiResponse<StoreDetailsResponseDto> getStoreDetail(@PathVariable("id") Long id) {
        if(!storeService.existById(id)) {
            throw new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND, ErrorCode.STORE_NOT_FOUND.getMessage());
        }

        StoreDetailsResponseDto storeDetails = storeService.getStoreDetail(id);

        return ApiResponse.ok(storeDetails, "식당 상세 조회 성공");
    }
}
