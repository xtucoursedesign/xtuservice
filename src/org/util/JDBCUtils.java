package org.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtils {
	private static DataSource ds;
	private static ThreadLocal<Connection> th;
	
	static {
		Properties pro = new Properties();
		
		try {
			pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ds = DruidDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		th = new ThreadLocal<Connection>();
	}
	
	public static Connection getConnection() {
		Connection conn = th.get();
		if(conn == null) {
			try {
				conn = ds.getConnection();
				th.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public static void free() {
		Connection conn = th.get();
		if(conn != null) {
			try {
				conn.setAutoCommit(true);
				conn.close();
				th.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
