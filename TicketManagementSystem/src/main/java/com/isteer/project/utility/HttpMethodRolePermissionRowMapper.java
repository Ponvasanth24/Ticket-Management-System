package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.HttpMethodRolePermission;

public class HttpMethodRolePermissionRowMapper implements RowMapper<HttpMethodRolePermission> {
    @Override
    public HttpMethodRolePermission mapRow(ResultSet rs, int rowNum) throws SQLException {
    	HttpMethodRolePermission permission = new HttpMethodRolePermission();
        permission.setHttpMethod(rs.getString("http_method"));
        permission.setRoleId(rs.getInt("role_id"));
        return permission;
    }
}
