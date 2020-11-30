package org.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.util.JDBCUtils;

public class BaseDao<T> {
	private Class<T> type;
	private QueryRunner qr = new QueryRunner();
	
	private BeanProcessor bean = new GenerousBeanProcessor();
	private RowProcessor processor = new BasicRowProcessor(bean);

	@SuppressWarnings("unchecked")
	public BaseDao() {
		@SuppressWarnings("rawtypes")
		Class<? extends BaseDao> clazz = this.getClass();
		Type genericSuperclass = clazz.getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType)genericSuperclass;
		Type[] t = pt.getActualTypeArguments();
		type = (Class<T>)t[0];
	}
	
	public int update(String sql, Object...params) {
		Connection conn = JDBCUtils.getConnection();
		int len = 0;
		try {
			len = qr.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return len;
	}
	
	public T getBean(String sql, Object...params) {
		Connection conn = JDBCUtils.getConnection();
		T t = null;
		try {
			t = qr.query(conn, sql, new BeanHandler<T>(type, processor), params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return t;
	}
	
	public List<T> getBeanList(String sql, Object...params){
		Connection conn = JDBCUtils.getConnection();
		List<T> list = null;
		
		try {
			list = qr.query(conn, sql, new BeanListHandler<T>(type, processor), params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return list;
	}
	
	public Object getSingleValue(String sql, Object...params ) {
		Object o = null;
		Connection conn = JDBCUtils.getConnection();
		try {
			qr.query(conn, sql, new ScalarHandler<T>(), params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return o;
	}
	
	public Map<String,?> getMap(String sql, Object... params){
		Map<String,?> map = null;
		
		Connection conn = JDBCUtils.getConnection();
		
		try {
			qr.query(conn, sql, new MapHandler(), params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return map;
	}
	
	public List<Map<String,?>> getMapList(String sql, Object...params ){
		Connection conn = JDBCUtils.getConnection();
		List<Map<String,?>> list = null;
		try {
			qr.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return list;
	}
	
	public void batchUpdate(String sql, Object[][] params){
		Connection conn = JDBCUtils.getConnection();
		try {
			qr.batch(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
