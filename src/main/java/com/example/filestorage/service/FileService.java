package com.example.filestorage.service;

import com.example.filestorage.entity.File;
import com.example.filestorage.web.dto.FileDto;

import java.util.List;

public interface FileService {

    List<File> findFilesInFolder(Long id);

    File create(FileDto folderDto);

    void delete(Long id);

}
