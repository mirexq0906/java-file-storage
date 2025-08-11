package com.example.filestorage.web.controller;

import com.example.filestorage.AbstractControllerTest;
import com.example.filestorage.entity.FileType;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.web.dto.FileDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    public void createWithNoParentFile_shouldReturnFile() throws Exception {
        createFileAndAssert(null, 0);
    }

    @Test
    public void createWithParentFile_shouldReturnFile() throws Exception {
        createFileAndAssert(1L, 1);
    }

    private void createFileAndAssert(Long parentId, long expectedParentId) throws Exception {
        int countFiles = fileRepository.count();
        FileDto fileDto = new FileDto();
        fileDto.setName("test");
        fileDto.setParentId(parentId);
        fileDto.setFileType(FileType.FOLDER);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/file")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentId").value(expectedParentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileType").value(FileType.FOLDER.name()))
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
