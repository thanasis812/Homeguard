package com.hg.web.rest;

import com.hg.service.PaymentService;
import com.hg.service.dto.mydto.UserPaymentDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Payment}.
 */
@Hidden
@RestController
@RequestMapping("/api/payments")
public class PaymentResource {

    private final PaymentService paymentService;
    private final HGTokenService tokenService;

    public PaymentResource(PaymentService paymentService, HGTokenService tokenService) {
        this.paymentService = paymentService;
        this.tokenService = tokenService;
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("userPayments")
    public ResponseEntity<List<UserPaymentDTO>> getUserPaymentsByTenantId(HttpServletRequest request) {
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        List<UserPaymentDTO> paymentDTO = paymentService.findUserPayments(tenantId);

        return ResponseUtil.wrapOrNotFound(Optional.of(paymentDTO));
    }
}
