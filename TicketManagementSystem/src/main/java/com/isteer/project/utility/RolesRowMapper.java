package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.Roles;

public class RolesRowMapper implements RowMapper<Roles> {
    @Override
    public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
        Roles roles = new Roles();
        roles.setRoleId(rs.getString("role_uuid"));
        roles.setRole(rs.getString("role"));
        return roles;
    }
}
