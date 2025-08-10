package com.example.filestorage.service.impl;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.excepton.CreateFileException;
import com.example.filestorage.excepton.EntityNotFoundException;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.web.dto.FileDto;
import com.example.filestorage.web.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public List<File> findFilesInFolder(Long id) {
        return fileRepository.findFilesInFolder(id);
    }

    @Override
    public File create(FileDto fileDto) {
        if (fileDto.getParentId() == null) {
            return createFile(fileDto);
        }

        File parentFile = fileRepository.findById(fileDto.getParentId()).orElseThrow(
                () -> new EntityNotFoundException("Parent file not found")
        );

        if (parentFile.getType() != FileType.FOLDER) {
            throw new CreateFileException("Parent file is not a folder");
        }

        return createFile(fileDto);
    }

    private File createFile(FileDto fileDto) {
        return fileRepository.create(fileMapper.toFile(fileDto));
    }

    @Override
    public void delete(Long id) {
        fileRepository.delete(id);
    }

}
