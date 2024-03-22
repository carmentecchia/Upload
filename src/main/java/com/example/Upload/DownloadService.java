package com.example.Upload;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class DownloadService {
    @Value("{Upload}")
    private String upload;

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName+"."+extension;
        File finalFolder = new File(upload);
        if (!finalFolder.exists()) throw new IOException("ciao");
        if (!finalFolder.isDirectory()) throw new IOException("hello");

        File finalDestination = new File(upload + "\\"+completeFileName);
        if (finalDestination.exists()) throw new IOException("file conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }
    public byte[] download(String fileName) throws IOException{
        File fileFromRepo = new File(upload+"\\"+fileName);
        if (!fileFromRepo.exists()) throw new IOException("ciao");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepo));
    }
}
