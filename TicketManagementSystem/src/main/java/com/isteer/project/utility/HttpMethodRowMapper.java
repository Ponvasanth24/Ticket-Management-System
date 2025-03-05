package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.HttpMethod;

public class HttpMethodRowMapper implements RowMapper<HttpMethod>{

	@Override
	public HttpMethod mapRow(ResultSet rs, int rowNum) throws SQLException {
		HttpMethod httpMethod = new HttpMethod();
		httpMethod.setMethodId(rs.getString("method_uuid"));
		httpMethod.setMethodName(rs.getString("method"));
		return httpMethod;
	}

}
