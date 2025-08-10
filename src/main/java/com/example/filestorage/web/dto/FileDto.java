package com.example.filestorage.web.dto;

import com.example.filestorage.entity.FileType;
import lombok.Data;

@Data
public class FileDto {

    private String name;
    private Long parentId;
    private FileType fileType;

}
