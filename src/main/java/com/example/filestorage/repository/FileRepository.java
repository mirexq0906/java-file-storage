package com.example.filestorage.repository;

import com.example.filestorage.entity.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    List<File> findFilesInFolder(Long id);

    Optional<File> findById(Long id);

    File create(File file);

    void delete(Long id);

    int count();

}
