package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.isteer.project.entity.Roles;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.RoleNotFoundException;
import com.isteer.project.utility.RolesRowMapper;

@Component
public class RoleSecurityRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Roles> getAllRoles() {
		String sql = "SELECT role_uuid, role FROM Roles";
		 List<Roles> roles = jdbcTemplate.query(sql, new RolesRowMapper());
		 return roles;
	}
	
	public String getRoleId(String role) {
		String getRoleId = "SELECT role_id, role FROM roles WHERE role = :role";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("role", role);
		try {
		Roles fetchedRole = namedParameterJdbcTemplate.queryForObject(getRoleId, params, new RolesRowMapper());
		return fetchedRole.getRoleId();
		} catch (EmptyResultDataAccessException e) {
			throw new RoleNotFoundException(ResponseMessageEnum.ROLE_NOT_FOUND_EXCEPTION);
		}
	}
	
	public int addRole(String roleId, String newRole) {
		String insertRole = "INSERT INTO Roles(role_uuid, role) VALUES(:roleId, :role)";
		MapSqlParameterSource param =  new MapSqlParameterSource();
		param.addValue("roleId", roleId);
		param.addValue("role", newRole);
		try {
		int status = namedParameterJdbcTemplate.update(insertRole, param);
		return status;
		}
		catch (Exception e) {
			throw new DuplicateKeyException(ResponseMessageEnum.DUPLICATE_KEY_EXCEPTION.getResponseMessage());
		}
	}
	
	public int removeRole(String removeRole) {
		String roleToRemove = "DELETE FROM Roles WHERE role = :role";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("role", removeRole);
		int status = namedParameterJdbcTemplate.update(roleToRemove, param);
		return status;
	}
}
