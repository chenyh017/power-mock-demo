package com.cyh.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcWrapper {

	private JdbcTemplate jdbcTemplate;

	public static final RowMapper<String> FUNC = (rs, index) -> "";

	public String query() {
		return jdbcTemplate.queryForObject("sql", String.class);
	}

	public List<String> queryByRowMapper() {
		return jdbcTemplate.query("sql", FUNC);
	}

	public List<Map<String,Object>> queryForList(){
		return jdbcTemplate.queryForList("sql");
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void print() {
		System.out.println("abcd");
	}

}
