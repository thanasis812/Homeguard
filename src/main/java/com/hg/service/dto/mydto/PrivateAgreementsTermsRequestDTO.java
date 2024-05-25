package com.hg.service.dto.mydto;

public class PrivateAgreementsTermsRequestDTO {

    UploadImageDTO signature;
    boolean agree;
    UploadImageDTO privateAgreementPdf;
    String landlordTerms;

    public UploadImageDTO getSignature() {
        return signature;
    }

    public void setSignature(UploadImageDTO signature) {
        this.signature = signature;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public UploadImageDTO getPrivateAgreementPdf() {
        return privateAgreementPdf;
    }

    public void setPrivateAgreementPdf(UploadImageDTO privateAgreementPdf) {
        this.privateAgreementPdf = privateAgreementPdf;
    }

    public String getLandlordTerms() {
        return landlordTerms;
    }

    public void setLandlordTerms(String landlordTerms) {
        this.landlordTerms = landlordTerms;
    }
}
