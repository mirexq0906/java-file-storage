package com.example.filestorage.web.controller;

import com.example.filestorage.AbstractControllerTest;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Path;

public class FileControllerTest extends AbstractControllerTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void findFilesInFolder_shouldReturnFiles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/file")
                        .param("parentId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void createWithNoParentFolder_shouldReturnFolder() throws Exception {
        createFileAndAssert(FileType.FOLDER, null, 0);
    }

    @Test
    public void createWithParentFolder_shouldReturnFolder() throws Exception {
        createFileAndAssert(FileType.FOLDER, 1L, 1);
    }

    @Test
    public void createFile_shouldReturnFile() throws Exception {
        createFileAndAssert(FileType.FILE, 1L, 1);
    }

    private void createFileAndAssert(FileType fileType, Long parentId, long expectedParentId) throws Exception {
        int countFiles = fileRepository.count();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart(
                                        HttpMethod.POST,
                                        "/api/file"
                                )
                                .file(file)
                                .param("name", "test")
                                .param("parentId", String.valueOf(parentId))
                                .param("fileType", String.valueOf(fileType))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(
                        FileType.FOLDER == fileType ? "test" : file.getOriginalFilename()
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentId").value(expectedParentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileType").value(fileType.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").isString());

        Assertions.assertEquals(countFiles + 1, fileRepository.count());
    }

    @Test
    public void delete_shouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/file/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertEquals(0, fileRepository.count());
    }

}
