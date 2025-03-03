package com.isteer.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.isteer.project.entity.Roles;
import com.isteer.project.entity.User;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.UserNameNotFoundException;
import com.isteer.project.utility.RolesRowMapper;


@Component
public class UserSecurityRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	PlatformTransactionManager transactionManager;
	
	public User findByUserName(String userName) {
		String userQuery = "SELECT user_id, user_name, password FROM users WHERE user_name = :userName";
		String roleQuery = "SELECT r.role_id, r.role FROM roles r INNER JOIN user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userName", userName);
		User user = null;
		try {
		user = namedParameterJdbcTemplate.queryForObject(userQuery, params, new BeanPropertyRowMapper<>(User.class));
		} catch(EmptyResultDataAccessException e) {
			return user;
		}
		params.addValue("userId", user.getUserId());
		List<Roles> roles = namedParameterJdbcTemplate.query(roleQuery, params, new RolesRowMapper());
		user.setRoles(roles);
		return user;
	}

	public int registerUser(User user) {
		String insertUser = "INSERT INTO Users (user_uuid,user_name, password, first_name, last_name, phone_number, email) VALUES (:userId, :userName, :password, :firstName, :lastName, :phoneNumber, :email)";
		String assignUserRole = "INSERT INTO user_role (user_id, role_id) VALUES (:userId, :roleId)";
		int sts = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userId", user.getUserId());
		params.addValue("userName", user.getUserName());
		params.addValue("password", user.getPassword());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("phoneNumber", user.getPhoneNumber());
		params.addValue("email", user.getEmail());
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			sts = namedParameterJdbcTemplate.update(insertUser, params);
			for (Roles role : user.getRoles()) {
				String roleId = role.getRoleId();
				params.addValue("roleId", roleId);
				sts = namedParameterJdbcTemplate.update(assignUserRole, params);
			}
			transactionManager.commit(status);
			return sts;
		} catch (Exception e) {
			transactionManager.rollback(status);
			return 0;
		}
	}

	public int elevateRole(String userName, String roleId) {
		String finduserId = "SELECT user_id from Users WHERE user_name = :userName";
		String elevateRole = "INSERT INTO user_role (user_id, role_id) VALUES (:userId, :roleId)";
		int sts = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userName", userName);
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			System.out.println(".................");
			String userId = namedParameterJdbcTemplate.queryForObject(finduserId, params, String.class);
			System.out.println(userId.equals(null));
			params.addValue("userId", userId);
			params.addValue("roleId", roleId);
			sts = namedParameterJdbcTemplate.update(elevateRole, params);
			System.out.println(sts);
			transactionManager.commit(status);
			return sts;
		} catch (EmptyResultDataAccessException e) {
			transactionManager.rollback(status);
			throw new UserNameNotFoundException(ResponseMessageEnum.USERNAME_NOT_FOUND_EXCEPTION);
		} catch (DuplicateKeyException e) {
			transactionManager.rollback(status);
			throw new DuplicateKeyException(ResponseMessageEnum.DUPLICATE_KEY_EXCEPTION.getResponseMessage());
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new RuntimeException(ResponseMessageEnum.DUPLICATE_KEY_EXCEPTION.getResponseMessage());
		}
	}
	
	public int deleteRole(String userName, String roleId) {
		String finduserId = "SELECT user_id from Users WHERE user_name = :userName";
		String deleteRole = "DELETE FROM user_role WHERE role_id = :roleId AND user_id = :userId";
		int sts = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userName", userName);
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			String userId = namedParameterJdbcTemplate.queryForObject(finduserId, params, String.class);
			params.addValue("userId", userId);
			params.addValue("roleId", roleId);
			sts = namedParameterJdbcTemplate.update(deleteRole, params);
			transactionManager.commit(status);
			return sts;
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			throw new UserNameNotFoundException(ResponseMessageEnum.USERNAME_NOT_FOUND_EXCEPTION);
		}
	}
}
