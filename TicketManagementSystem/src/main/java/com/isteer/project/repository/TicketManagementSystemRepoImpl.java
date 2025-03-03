package com.isteer.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.isteer.project.entity.TicketManagementSystem;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.TicketIdNotFoundException;

@Component
public class TicketManagementSystemRepoImpl implements TicketManagementSystemRepo{

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private BeanPropertyRowMapper<TicketManagementSystem> rowMapper = new BeanPropertyRowMapper<>(TicketManagementSystem.class);
	
	@Override
	public int raiseTicket(TicketManagementSystem ticket) {
		String sql = "INSERT INTO Ticketing_System (ticket_id, created_by, ticket_heading,ticket_description, created_on, ticket_status, assigned_to) VALUES (:ticketId, :createdBy, :heading, :description, :createdOn, :status, :assignedTo)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ticketId", ticket.getTicketId());
		params.addValue("createdBy", ticket.getCreatedBy());
		params.addValue("heading", ticket.getTicketHeading());
		params.addValue("description", ticket.getTicketDescription());
		params.addValue("createdOn", ticket.getCreatedOn());
		params.addValue("status", ticket.getTicketStatus());
		params.addValue("assignedTo", ticket.getAssignedTo());
		int status = namedParameterJdbcTemplate.update(sql, params);
		return status;
	}

	@Override
	public List<TicketManagementSystem> getTicketsByUser(String createdBy) {
		String sql = "SELECT ticket_id, created_by, ticket_heading, ticket_description, created_on, updated_on, ticket_status, assigned_to FROM ticketing_system WHERE created_by = :createdBy";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("createdBy", createdBy);
		List<TicketManagementSystem> tickets = namedParameterJdbcTemplate.query(sql, params, rowMapper);
		return tickets;
	}
	
	@Override
	public TicketManagementSystem getTicketById(String ticketId) {
		String sql = "SELECT ticket_id, created_by, ticket_heading, ticket_description, created_on, updated_on, ticket_status, assigned_to FROM ticketing_system WHERE ticket_id = :ticketId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ticketId", ticketId);
		try {
		TicketManagementSystem ticket = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
		return ticket;
		}
		catch (EmptyResultDataAccessException e) {
			throw new TicketIdNotFoundException(ResponseMessageEnum.TICKET_ID_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public int assignTicketTo(String ticketId, String assignTo) {
		String updateTicket = "UPDATE ticketing_system SET ticket_status = :ticketStatus, assigned_to = :assignTo, updated_on = :updatedOn WHERE ticket_id = :ticketId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ticketId", ticketId);
		params.addValue("ticketStatus", "Assigned");
		params.addValue("assignTo", assignTo);
		params.addValue("updatedOn", LocalDateTime.now());
		int status = namedParameterJdbcTemplate.update(updateTicket, params);
		return status;
	}

	@Override
	public int updateTicketStatus(String ticketId, String ticketStatus) {
		String updateStatus = "UPDATE ticketing_system SET ticket_status = :ticketStatus, updated_on = :updatedOn WHERE ticket_id = :ticketId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ticketId", ticketId);
		params.addValue("ticketStatus", ticketStatus);
		params.addValue("updatedOn", LocalDateTime.now());
		int status = namedParameterJdbcTemplate.update(updateStatus, params);
		return status;
	}

	@Override
	public List<TicketManagementSystem> getAssignedTickets(String adminName) {
		String sql = "SELECT ticket_id, created_by, ticket_heading, ticket_description, created_on, updated_on, ticket_status, assigned_to FROM ticketing_system WHERE assigned_to = :adminName AND ticket_status = 'Assigned'";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("adminName", adminName);
		List<TicketManagementSystem> tickets = namedParameterJdbcTemplate.query(sql, params, rowMapper);
		return tickets;
	}

	@Override
	public List<TicketManagementSystem> getWorkingTickets(String adminName) {
		String sql = "SELECT ticket_id, created_by, ticket_heading, ticket_description, created_on, updated_on, ticket_status, assigned_to FROM ticketing_system WHERE assigned_to = :adminName AND ticket_status = 'working'";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("adminName", adminName);
		List<TicketManagementSystem> tickets = namedParameterJdbcTemplate.query(sql, params, rowMapper);
		return tickets;
	}

}
