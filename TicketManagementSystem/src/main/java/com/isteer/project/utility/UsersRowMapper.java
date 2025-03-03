package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.User;

public class UsersRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User users = new User();
		users.setUserId(rs.getString("user_uuid"));
		users.setUserName(rs.getString("user_name"));
		users.setPassword(rs.getString("password"));
		users.setFirstName(rs.getString("first_name"));
		users.setLastName(rs.getString("last_name"));
		users.setPhoneNumber(rs.getString("phone_number"));
		users.setEmail(rs.getString("email"));
		return users;
	}

}
