package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.request.InstantMatchingBoardDTO;
import com.capstone.timepay.controller.board.request.InstantMatchingRequestDTO;
import com.capstone.timepay.controller.board.reseponse.InstantMatchingResponseDTO;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.service.InstantMatchingService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/instant-matching")
@RequiredArgsConstructor
public class InstantMatchingController {

    private final InstantMatchingService instantMatchingService;


    @GetMapping("")
    @ApiOperation(value = "즉석 매칭 호출 API")
    public ResponseEntity<?> getEmail(Principal principal){
        InstantMatchingRequestDTO instantMatchingRequestDTO = new InstantMatchingRequestDTO(principal.getName());
        return ResponseEntity.ok(instantMatchingRequestDTO);
    }

    @PostMapping("")
    @ApiOperation(value = "즉석 매칭 게시판 작성 API")
    public ResponseEntity<?> instantMatchingAPI(@RequestBody InstantMatchingBoardDTO instantMatchingBoardDTO,
                                           Principal helper) throws Exception{
       InstantMatchingResponseDTO instantMatchingResponseDTO =
               instantMatchingService.instantMatching(instantMatchingBoardDTO, helper.getName());

        return ResponseEntity.ok(instantMatchingResponseDTO);
    }
}
