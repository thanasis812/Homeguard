package com.hg.service.impl;

import com.hg.domain.Payment;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.repository.PaymentRepository;
import com.hg.repository.RentalAgreementRepository;
import com.hg.repository.TenantRepository;
import com.hg.service.PaymentService;
import com.hg.service.dto.PaymentDTO;
import com.hg.service.dto.mydto.UserPaymentDTO;
import com.hg.service.mapper.PaymentMapper;
import com.hg.web.rest.errors.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.Payment}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;

    private final PaymentMapper paymentMapper;

    private final RentalAgreementRepository rentalAgreementRepository;

    public PaymentServiceImpl(
        PaymentRepository paymentRepository,
        TenantRepository tenantRepository,
        PaymentMapper paymentMapper,
        RentalAgreementRepository rentalAgreementRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.tenantRepository = tenantRepository;
        this.paymentMapper = paymentMapper;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) {
        log.debug("Request to update Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO) {
        log.debug("Request to partially update Payment : {}", paymentDTO);

        return paymentRepository
            .findById(paymentDTO.getId())
            .map(existingPayment -> {
                paymentMapper.partialUpdate(existingPayment, paymentDTO);

                return existingPayment;
            })
            .map(paymentRepository::save)
            .map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id).map(paymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPaymentDTO> findUserPayments(Long tenantId) {
        log.debug("Request to get getUserPayments List for tenant id : {}", tenantId);

        return rentalAgreementRepository
            .findByStatusAndTenant(
                RentalAgreementStatusEnum.ACTIVE,
                tenantRepository
                    .findById(tenantId)
                    .orElseThrow(() -> new NotFoundException(String.format("Can't find Tenant with Tenant id: %d", tenantId)))
            )
            .map(x -> paymentMapper.toUserPaymentDTOList(x.getPayments().stream().toList()))
            .orElse(Collections.emptyList());
    }
}
