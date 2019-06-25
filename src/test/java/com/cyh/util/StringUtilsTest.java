package com.cyh.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Test
	public void jdbc() {
		Mockito.when(jdbcTemplate.queryForObject("sql", Long.class)).thenReturn(2l);
		System.out.println(jdbcTemplate.queryForObject("sql", Long.class));
	}

}
