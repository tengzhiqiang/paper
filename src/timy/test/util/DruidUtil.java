package timy.test.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DruidUtil {
	
	private DruidUtil(){}
	
	 private static class PrivateSingleton{
	        //保证对象的唯一性。
	        static final PrivateSingleton instance = new PrivateSingleton();
	    }

	
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	private static Connection cnn = null;
	
	
	public static java.sql.Connection getconnection() {
		
		// 驱动，数据库的驱动
		String driverClass = null;
		// 链接数据库的url
		String url = null;
		// 数据库用户名
		String user = null;
		// 数据库密码
		String password = null;

		try {
			InputStream in = JdbcTool.class.getClassLoader().getResourceAsStream(
					"JDBC.properties");
			Properties properties = new Properties();
			properties.load(in);
			driverClass = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
			ds.setDriverClass(driverClass);
			ds.setJdbcUrl(url);
			ds.setUser(user);
			ds.setPassword(password);
			
			ds.setInitialPoolSize(5);
			ds.setMaxPoolSize(30);
			ds.setMinPoolSize(2);
			
			cnn = ds.getConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return cnn;
		
	}

}
