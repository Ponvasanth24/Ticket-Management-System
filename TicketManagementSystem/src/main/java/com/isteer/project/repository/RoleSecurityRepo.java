package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.isteer.project.entity.Roles;
import com.isteer.project.utility.RolesRowMapper;

@Component
public class RoleSecurityRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Roles> getAllRoles() {
		String sql = "SELECT role_id, role FROM Roles";
		BeanPropertyRowMapper<Roles> rowMapper = new BeanPropertyRowMapper<>(Roles.class);
		 List<Roles> roles = jdbcTemplate.query(sql, rowMapper);
		 return roles;
	}
	
	public int getRoleId(String role) {
		String getRoleId = "SELECT role_id, role FROM roles WHERE role = :role";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("role", role);
		Roles fetchedRole = namedParameterJdbcTemplate.queryForObject(getRoleId, params, new RolesRowMapper());
		return fetchedRole.getRoleId();
	}
	
	public int addRole(String newRole) {
		String insertRole = "INSERT INTO Roles(role) VALUES(:role)";
		MapSqlParameterSource param =  new MapSqlParameterSource();
		param.addValue("role", newRole);
		int status = namedParameterJdbcTemplate.update(insertRole, param);
		return status;
	}
	
	public int removeRole(String removeRole) {
		String roleToRemove = "DELETE FROM Roles WHERE role = :role";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("role", removeRole);
		int status = namedParameterJdbcTemplate.update(roleToRemove, param);
		return status;
	}
}
