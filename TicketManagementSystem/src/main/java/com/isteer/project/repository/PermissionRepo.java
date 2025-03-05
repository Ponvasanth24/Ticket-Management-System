package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.isteer.project.entity.HttpMethod;
import com.isteer.project.entity.HttpMethodRolePermission;
import com.isteer.project.entity.Permission;
import com.isteer.project.entity.Roles;
import com.isteer.project.entity.Urls;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.HttpMethodNotFoundException;
import com.isteer.project.exception.UrlNotFoundException;
import com.isteer.project.utility.HttpMethodRolePermissionRowMapper;
import com.isteer.project.utility.HttpMethodRowMapper;
import com.isteer.project.utility.PermissionRowMapper;

@Component
public class PermissionRepo {
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	PlatformTransactionManager transactionManager;

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

	public int addUrl(Urls url) {
		String addUrl = "INSERT INTO urls(url, url_uuid) VALUES(:urlPattern, :urlId)";
		String addRoleForUrl = "INSERT INTO permission(url_id, role_id) VALUES (:urlId, :roleId)";
		int sts = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("urlPattern", url.getUrlPattern());
		params.addValue("urlId", url.getUrlid());
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			sts = namedParameterJdbcTemplate.update(addUrl, params);
			for (Roles role : url.getRoles()) {
				String roleId = role.getRoleId();
				params.addValue("roleId", roleId);
				sts = namedParameterJdbcTemplate.update(addRoleForUrl, params);
			}
			transactionManager.commit(status);
			return sts;
		} catch (Exception e) {
			transactionManager.rollback(status);
			return 0;
		}
	}
	
	public int removeUrl(String url, String roleId) {
		String addUrl = "DELETE FROM permission WHERE url_pattern = :urlPattern AND role_id = :roleId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("urlPattern", url);
		params.addValue("roleId", roleId);
		int status = namedParameterJdbcTemplate.update(addUrl, params);
		if(status == 0) {
			throw new UrlNotFoundException(ResponseMessageEnum.URL_NOT_FOUND_EXCEPTION);
		}
		return status;
	}

	public int addHttpMethod(HttpMethod method) {
		String addHttpMethod = "INSERT INTO http_methods(method, method_uuid) VALUES(:method, :methodId)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("method", method.getMethodName());
		params.addValue("methodId", method.getMethodId());
		int status = namedParameterJdbcTemplate.update(addHttpMethod, params);
		return status;
	}

	public int removeHttpMethod(HttpMethod method) {
		String removeHttpMethod = "DELETE FROM http_methods WHERE method = :method";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("method", method.getMethodId());
		int status = namedParameterJdbcTemplate.update(removeHttpMethod, params);
		if(status == 0)
			throw new HttpMethodNotFoundException(ResponseMessageEnum.HTTP_METHOD_NOT_FOUND_EXCEPTION);
		return status;
	}

	public List<HttpMethod> getAllMethods() {
		String allMethods = "SELECT method_uuid, method FROM http_methods";
		List<HttpMethod> methods = namedParameterJdbcTemplate.query(allMethods, new HttpMethodRowMapper());
		return methods;
	}

}
