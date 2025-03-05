package com.isteer.project.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.isteer.project.entity.TicketManagementSystem;

public class TicketRowMapper implements RowMapper<TicketManagementSystem>{

	@Override
	public TicketManagementSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
		TicketManagementSystem tickets = new TicketManagementSystem();
		tickets.setTicketId(rs.getString("ticket_uuid"));
		tickets.setCreatedBy(rs.getString("created_by"));
		tickets.setTicketHeading(rs.getString("ticket_heading"));
		tickets.setTicketDescription(rs.getString("ticket_description"));
		tickets.setCreatedOn(Optional.ofNullable(rs.getTimestamp("created_on"))
	            .map(Timestamp::toLocalDateTime)
	            .orElse(null));
		tickets.setUpdatedOn(Optional.ofNullable(rs.getTimestamp("updated_on"))
	            .map(Timestamp::toLocalDateTime)
	            .orElse(null));
		tickets.setTicketStatus(rs.getString("ticket_status"));
		tickets.setAssignedTo(rs.getString("assigned_to"));
		return tickets;
	}

}
