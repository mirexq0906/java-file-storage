package com.example.filestorage.repository.mapper;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.FileType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileRowMapper {

    public static File toRow(ResultSet rs, Integer rowNum) throws SQLException {
        File file = new File();
        file.setId(rs.getLong("id"));
        file.setName(rs.getString("name"));
        file.setParentId(rs.getLong("parent_id"));
        file.setType(FileType.valueOf(rs.getString("file_type")));
        file.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        file.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return file;
    }

}
