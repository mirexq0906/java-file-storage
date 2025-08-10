package com.example.filestorage.web.response;

import com.example.filestorage.entity.FileType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponse {

    private Long id;
    private String name;
    private Long parentId;
    private FileType fileType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
