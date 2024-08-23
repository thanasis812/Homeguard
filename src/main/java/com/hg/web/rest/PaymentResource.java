package com.hg.web.rest;

import com.hg.service.PaymentService;
import com.hg.service.UserPrincipalService;
import com.hg.service.dto.mydto.UserPaymentDTO;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.Payment}.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentResource {

    private final PaymentService paymentService;
    private final UserPrincipalService userPrincipalService;

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("userPayments")
    public ResponseEntity<List<UserPaymentDTO>> getUserPaymentsByTenantId() {
        Long tenantId = userPrincipalService.getTenantId();
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        List<UserPaymentDTO> paymentDTO = paymentService.findUserPayments(tenantId);

        return ResponseUtil.wrapOrNotFound(Optional.of(paymentDTO));
    }
}
