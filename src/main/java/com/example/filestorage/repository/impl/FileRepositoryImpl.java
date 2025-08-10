package com.example.filestorage.repository.impl;

import com.example.filestorage.entity.File;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.repository.mapper.FileRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<File> findFilesInFolder(Long id) {
        String query = """
                SELECT id, name, parent_id, file_type, created_at, updated_at
                FROM files
                WHERE parent_id = ?
                """;

        return jdbcTemplate.query(query, FileRowMapper::toRow, id);
    }

    @Override
    public Optional<File> findById(Long id) {
        String query = """
                SELECT id, name, parent_id, file_type, created_at, updated_at
                FROM files
                WHERE id = ?
                """;

        File file = jdbcTemplate.queryForObject(query, FileRowMapper::toRow, id);
        return Optional.ofNullable(file);
    }

    @Override
    public File create(File file) {
        String query = """
                INSERT INTO files (name, parent_id, file_type)
                VALUES (?, ?, ?)
                RETURNING id, name, parent_id, file_type, created_at, updated_at
                """;

        return jdbcTemplate.queryForObject(query, FileRowMapper::toRow, file.getName(), file.getParentId(), file.getType().name());
    }

    @Override
    public void delete(Long id) {
        String query = """
                DELETE FROM files
                WHERE id = ?
                """;

        jdbcTemplate.update(query, id);
    }

    @Override
    public int count() {
        String query = """
                SELECT count(*) FROM files
                """;

        return jdbcTemplate.queryForObject(query, Integer.class);
    }

}
