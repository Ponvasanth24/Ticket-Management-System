package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.isteer.project.entity.HttpMethodRolePermission;
import com.isteer.project.entity.Permission;
import com.isteer.project.utility.HttpMethodRolePermissionRowMapper;
import com.isteer.project.utility.PermissionRowMapper;

@Component
public class PermissionRepo {
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Permission> findByUrlPattern(String urlPattern) {
		String findByUrl = "SELECT url_pattern, role_id FROM permission WHERE url_pattern = :urlPattern";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("urlPattern", urlPattern);
		List<Permission> permittedRoles = namedParameterJdbcTemplate.query(findByUrl, params, new PermissionRowMapper());
		return permittedRoles;
	}
	
	public List<HttpMethodRolePermission> findHttpMethod(String httpMethod) {
		String findHttpMethod = "SELECT http_method, role_id FROM http_method_role_permission WHERE http_method = :httpMethod";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("httpMethod", httpMethod);
		List<HttpMethodRolePermission> permittedRoles = namedParameterJdbcTemplate.query(findHttpMethod, params, new HttpMethodRolePermissionRowMapper());
		return permittedRoles;
	}

	public int addUrl(String url, int roleId) {
		String addUrl = "INSERT INTO permission(url_pattern, role_id) VALUES(:urlPattern, :roleId)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("urlPattern", url);
		params.addValue("roleId", roleId);
		int status = namedParameterJdbcTemplate.update(addUrl, params);
		return status;
	}
	
	public int removeUrl(String url, int roleId) {
		String addUrl = "DELETE FROM permission WHERE url_pattern = :urlPattern AND role_id = :roleId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("urlPattern", url);
		params.addValue("roleId", roleId);
		int status = namedParameterJdbcTemplate.update(addUrl, params);
		return status;
	}

}
