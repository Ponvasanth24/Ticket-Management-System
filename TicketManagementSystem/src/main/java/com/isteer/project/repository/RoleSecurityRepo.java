package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.isteer.project.entity.HttpMethod;
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
	@Autowired
	PlatformTransactionManager transactionManager;
	
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
	
	public int addRole(Roles role) {
		String insertRole = "INSERT INTO Roles(role_uuid, role) VALUES(:roleId, :role)";
		String addMethod = "INSERT INTO http_method_role_permission(method_id, role_id) VALUES (:methodId, :roleId)";
		int sts = 0;
		MapSqlParameterSource param =  new MapSqlParameterSource();
		param.addValue("roleId", role.getRoleId());
		param.addValue("role", role.getRole());
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			sts = namedParameterJdbcTemplate.update(insertRole, param);
			for (HttpMethod method : role.getMethods()) {
				String methodId = method.getMethodId();
				param.addValue("methodId", methodId);
				sts = namedParameterJdbcTemplate.update(addMethod, param);
			}
			transactionManager.commit(status);
			return sts;
		} catch (Exception e) {
			transactionManager.rollback(status);
			return 0;
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
