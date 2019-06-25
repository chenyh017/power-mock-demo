package com.cyh.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StringUtils.class })
public class StringUtilsTest {

	@Test
	public void isBlank() {
		//对一个类mock，所有的方法都要mock
		PowerMockito.mockStatic(StringUtils.class);
		Mockito.when(StringUtils.isBlank(Mockito.anyString())).thenReturn(true);
		Assert.assertTrue(StringUtils.isBlank("abc"));
		Mockito.when(StringUtils.null2Empty(Mockito.anyString())).thenReturn("123");
		System.out.println(StringUtils.null2Empty("abc"));
	}

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private JdbcWrapper wrapper;

	@Test
	public void jdbc() {
		Mockito.when(jdbcTemplate.queryForObject("sql", String.class)).thenReturn("123456");
		System.out.println(wrapper.query());
		
		List<String> list = new ArrayList<>();
		list.add("123");
		list.add("12356");
		Mockito.when(jdbcTemplate.query("sql",JdbcWrapper.FUNC)).thenReturn(list);
		System.out.println(wrapper.queryByRowMapper());
		
		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString())).thenReturn(null);
		System.out.println(wrapper.queryForList());
		
	}

}
