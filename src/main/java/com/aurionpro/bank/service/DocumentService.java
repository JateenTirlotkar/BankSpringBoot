package com.aurionpro.bank.service;

import com.aurionpro.bank.entity.Document;
import com.aurionpro.bank.enums.DocumentType;
import com.aurionpro.bank.enums.KycStatus;
import com.aurionpro.bank.repo.DocumentRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private DocumentRepo documentRepo;

    public Document uploadDocument(MultipartFile file, DocumentType documentType, KycStatus kycStatus, Long customerId, Long accountId) throws IOException {
        // Upload file to Cloudinary
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Save document URL to database
        Document document = new Document();
        document.setDocumentUrl((String) uploadResult.get("url"));
        document.setDocumentType(documentType);
        document.setKycStatus(kycStatus);
        // Set customer and account if available
        // document.setCustomer(customerRepo.findById(customerId).orElse(null));
        // document.setAccount(accountRepo.findById(accountId).orElse(null));

        return documentRepo.save(document);
    }
}
