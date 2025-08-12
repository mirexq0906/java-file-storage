package com.example.filestorage.web.mapper;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.web.dto.FileDto;
import com.example.filestorage.web.response.FileResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileMapper {

    public File toFile(FileDto fileDto) {
        File file = new File();
        file.setName(fileDto.getName());
        file.setParentId(fileDto.getParentId());
        file.setType(fileDto.getFileType());
        return file;
    }

    public List<FileResponse> toFileResponseList(List<File> files) {
        return files.stream().map(this::toFileResponse).toList();
    }

    public FileResponse toFileResponse(File file) {
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(file.getId());
        fileResponse.setName(file.getName());
        fileResponse.setParentId(file.getParentId());
        fileResponse.setFileType(file.getType());
        fileResponse.setCreatedAt(file.getCreatedAt().toString());
        fileResponse.setUpdatedAt(file.getUpdatedAt().toString());
        return fileResponse;
    }

}
