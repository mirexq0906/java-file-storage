package com.example.filestorage.unit.service;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.excepton.CreateFileException;
import com.example.filestorage.excepton.EntityNotFoundException;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.service.impl.FileServiceImpl;
import com.example.filestorage.web.dto.FileDto;
import com.example.filestorage.web.mapper.FileMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class FolderServiceTest {

    private FileService fileService;
    private FileRepository fileRepository;
    private FileMapper fileMapper;
    private FileDto fileDto;

    @BeforeEach
    public void setUp() {
        fileRepository = Mockito.mock(FileRepository.class);
        fileMapper = Mockito.mock(FileMapper.class);
        fileService = new FileServiceImpl(fileRepository, fileMapper);

        fileDto = new FileDto();
        fileDto.setName("test");
        fileDto.setParentId(1L);
        fileDto.setFileType(FileType.FOLDER);
    }

    @Test
    public void create_shouldReturnFile() {
        File file = new File();
        file.setType(FileType.FOLDER);

        Mockito.when(fileRepository.findById(fileDto.getParentId())).thenReturn(Optional.of(file));
        Mockito.when(fileMapper.toFile(fileDto)).thenReturn(file);
        Mockito.when(fileRepository.create(file)).thenReturn(file);

        fileService.create(fileDto);

        Mockito.verify(fileMapper, Mockito.times(1)).toFile(fileDto);
        Mockito.verify(fileRepository, Mockito.times(1)).create(file);
    }

    @Test
    public void create_shouldReturnEntityNotFoundException() {
        Mockito.when(fileRepository.findById(fileDto.getParentId())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> fileService.create(fileDto));
        Mockito.verify(fileRepository, Mockito.times(1)).findById(fileDto.getParentId());
    }

    @Test
    public void create_shouldReturnCreateFileException() {
        File file = new File();
        file.setType(FileType.FILE);

        Mockito.when(fileRepository.findById(fileDto.getParentId())).thenReturn(Optional.of(file));

        Assertions.assertThrows(CreateFileException.class, () -> fileService.create(fileDto));
        Mockito.verify(fileRepository, Mockito.times(1)).findById(fileDto.getParentId());
    }

}
