package piglin.swapswap.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(piglin.swapswap.global.annotation.Retry)")
    public Object retryIssueCoupon(ProceedingJoinPoint joinPoint) throws Throwable {

        int retryCount = 0;

        while (true) {
            log.info("retryCount: {}", retryCount);
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                if (e instanceof ObjectOptimisticLockingFailureException) {
                    log.error("트랜잭션 충돌이 발생하여 재시도합니다.");
                    retryCount++;
                    continue;
                }
                throw new RuntimeException(e);
            }
        }

    }
}
