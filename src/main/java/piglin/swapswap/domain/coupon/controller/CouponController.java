package piglin.swapswap.domain.coupon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import piglin.swapswap.domain.coupon.constant.CouponType;
import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.service.CouponService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public String createCouponForm(Model model) {

        model.addAttribute("couponCreateRequestDto", new CouponCreateRequestDto(
                null, null, 0, null, 0));
        model.addAttribute("couponType", CouponType.values());

        return "coupon/createCouponForm";
    }

    @PostMapping
    public String createCoupon(
            @Valid @ModelAttribute("couponCreateRequestDto") CouponCreateRequestDto couponCreateRequestDto,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "coupon/createCouponForm";
        }

        try {
            Long couponId = couponService.createCoupon(couponCreateRequestDto);
            redirectAttributes.addAttribute("couponId", couponId);
        } catch (Exception e) {
            bindingResult.reject("쿠폰 등록 중 에러가 발생하였습니다.");
            return "coupon/createCouponForm";
        }

        return "redirect:/coupon/{couponId}";
    }
}
