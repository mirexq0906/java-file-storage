package com.example.filestorage.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class File {

    private Long id;
    private String name;
    private Long parentId;
    private FileType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
