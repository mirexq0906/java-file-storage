package com.example.filestorage.web.dto;

import com.example.filestorage.entity.FileType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {

    private String name;
    private Long parentId;
    private MultipartFile file;
    private FileType fileType;

}
