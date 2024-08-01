package flab.gotable.controller;

import flab.gotable.dto.ApiResponse;
import flab.gotable.dto.request.ReservationRequestDto;
import flab.gotable.dto.response.ReservationResponseDto;
import flab.gotable.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservations")
public class ReservationAPIController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<ReservationResponseDto> createReserve(@RequestBody ReservationRequestDto reservationRequestDto) {

        ReservationResponseDto reservationDetails = reservationService.reserve(reservationRequestDto);

        return ApiResponse.ok(reservationDetails, "예약 성공");
    }
}
