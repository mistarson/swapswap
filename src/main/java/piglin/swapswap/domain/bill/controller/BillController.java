package piglin.swapswap.domain.bill.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import piglin.swapswap.domain.bill.dto.request.BillCreateRequestDto;
import piglin.swapswap.domain.bill.service.BillService;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.annotation.AuthMember;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;
/*    @ResponseBody
    @PostMapping
    public ResponseEntity<?> createBill(@Valid @RequestBody BillCreateRequestDto requestDto,
            @AuthMember Member member) {

        Long billId = billService.createBill(member, requestDto);

        return ResponseEntity.ok().body(billId);
    }*/
}

