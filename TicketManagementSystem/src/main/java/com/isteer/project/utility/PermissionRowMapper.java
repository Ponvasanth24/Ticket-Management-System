package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.Permission;

public class PermissionRowMapper implements RowMapper<Permission> {
    @Override
    public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
        Permission permission = new Permission();
        permission.setUrlId(rs.getString("url_uuid"));
        permission.setRoleId(rs.getString("role_uuid"));
        return permission;
    }
}
