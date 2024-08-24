package com.hg.web.rest;

import com.hg.service.PropertyService;
import com.hg.service.UserPrincipalService;
import com.hg.service.dto.mydto.*;
import io.swagger.v3.oas.annotations.Hidden;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.hg.domain.Review}.
 */
@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropertyService propertyService;
    private final UserPrincipalService userPrincipalService;

    /**
     * Processes a request to approve a credit card.
     *
     * @param creditInfo the credit card number to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved credit card number.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/credit-cards-request")
    public ResponseEntity<List<AdminCreditCardDTO>> creditCardApprove() throws URISyntaxException {
        log.debug("REST request to creditCardApprove for credit : {}", "creditInfo");
        AdminCreditCardDTO paymentDetails = new AdminCreditCardDTO();

        paymentDetails.setUserId(12345L);
        paymentDetails.setHouseId(67890L);
        paymentDetails.setFullname("John Doe");
        paymentDetails.setCardNumber(1234567890123456L);
        paymentDetails.setCvv(123);
        paymentDetails.setExpireDate("12/25");
        paymentDetails.setPhone("+1234567890");
        return ResponseEntity.ok().body(Collections.singletonList(paymentDetails));
    }

    /**
     * Processes a request to approve a house.
     *
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/new-houses-requests")
    public ResponseEntity<AdminHouseDTO> newHouseApprove() {
        Long tenantId = userPrincipalService.getTenantId();
        log.debug("REST request to newHouseApprove to with id : {}", tenantId);
        return ResponseEntity.ok().body(propertyService.newHouseApprove(tenantId));
    }

    @GetMapping("/private-agreements-request")
    public ResponseEntity<List<AdminPrivateAgreementsDTO>> getPrivateAgreementsRequest() {
        Long tenantId = userPrincipalService.getTenantId();
        log.debug("REST request to newHouseApprove to with id : {}", tenantId);

        AdminPrivateAgreementsDTO leaseAgreementDTO = new AdminPrivateAgreementsDTO();

        leaseAgreementDTO.setId(1L);
        leaseAgreementDTO.setHouseId(101L);
        leaseAgreementDTO.setNumber(123456L);
        leaseAgreementDTO.setOwnerName("John Doe");
        leaseAgreementDTO.setTenantName("Jane Smith");
        leaseAgreementDTO.setName("Lease Agreement for Unit 101");
        leaseAgreementDTO.setPrice(1500);
        leaseAgreementDTO.setGeneralTerms("The tenant agrees to pay rent on the 1st of every month.");
        leaseAgreementDTO.setLandlordTerms("The landlord agrees to provide maintenance services.");
        leaseAgreementDTO.setLanlordSignature(999L);
        leaseAgreementDTO.setTenantSignature(888L);

        return ResponseEntity.ok().body(Collections.singletonList(leaseAgreementDTO));
    }

    /**
     * Processes a request to approve a house.
     *
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/salary-certificates-request")
    public ResponseEntity<List<AdminSalaryCertificateDTO>> salaryCertificateApprove() throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", "id");
        return ResponseEntity.ok()
            .body(
                Collections.singletonList(
                    AdminSalaryCertificateDTO.builder()
                        .ofId(1L)
                        .ofUserId(101L)
                        .ofHouseId(201L)
                        .ofSalarySlips(Collections.singletonList("Sample Salary Slip".getBytes()))
                        .ofGuarantorSalarySlips(Collections.singletonList("Sample Guarantor Salary Slip".getBytes()))
                        .ofUserName("John Doe")
                        .ofGuarantorChecked(true)
                        .build()
                )
            );
    }

    /**
     * Processes a request to approve a house.
     *
     * @param id the ID of the house to be approved.
     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/taxid-certificates-and-ids-request")
    public ResponseEntity<List<AdminTaxIdCertificateAndIdDTO>> taxidCertificateApprove() throws URISyntaxException {
        log.debug("REST request to privateAgreementApprove to with id : {}", "id");
        AdminTaxIdCertificateAndIdDTO certificateAndId = new AdminTaxIdCertificateAndIdDTO();

        certificateAndId.setTaxidCertificateId(12345L);
        certificateAndId.setIdCertificateId(67890L);
        certificateAndId.setUserId(112233L);
        certificateAndId.setIdCertificateFront(new byte[] { 0x01, 0x02, 0x03 });
        certificateAndId.setIdCertificateBack(new byte[] { 0x04, 0x05, 0x06 });
        certificateAndId.setTaxidCertificate(new byte[] { 0x07, 0x08, 0x09 });
        certificateAndId.setTaxId(987654321L);
        certificateAndId.setPersonalId("XYZ123456");
        certificateAndId.setName("Alice");
        certificateAndId.setSurname("Johnson");
        certificateAndId.setGuarantorIdCertificateFront(new byte[] { 0x0A, 0x0B, 0x0C });
        certificateAndId.setGuarantorIdCertificateBack(new byte[] { 0x0D, 0x0E, 0x0F });
        certificateAndId.setGuarantorTaxidCertificate(new byte[] { 0x10, 0x11, 0x12 });
        certificateAndId.setGuarantorTaxId(123456789L);
        certificateAndId.setGuarantorPersonalId("ABC654321");
        certificateAndId.setGuarantorName("Bob");
        certificateAndId.setGuarantorSurname("Smith");
        return ResponseEntity.ok().body(Collections.singletonList(certificateAndId));
    }
    //    /**
    //     * Processes a request to approve a house.
    //     *
    //     * @param id the ID of the house to be approved.
    //     * @return a {@link ResponseEntity} containing a success message and the approved house ID.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @GetMapping("/taxisnet-document-approve")
    //    public ResponseEntity<String> taxisnetDocumentApprove() throws URISyntaxException {
    //        log.debug("REST request to privateAgreementApprove to with id : {}", "id");
    //        return ResponseEntity.ok().body("Taxis net with with ID " + "id" + " approved");
    //    }
}
