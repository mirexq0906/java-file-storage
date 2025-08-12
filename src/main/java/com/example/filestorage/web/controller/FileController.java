package com.example.filestorage.web.controller;

import com.example.filestorage.service.FileService;
import com.example.filestorage.web.dto.FileDto;
import com.example.filestorage.web.mapper.FileMapper;
import com.example.filestorage.web.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileMapper fileMapper;

    @GetMapping
    public List<FileResponse> findFilesInFolder(@RequestParam Long parentId) {
        return fileMapper.toFileResponseList(fileService.findFilesInFolder(parentId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponse create(@ModelAttribute FileDto fileDto) {
        return fileMapper.toFileResponse(fileService.create(fileDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }

}
