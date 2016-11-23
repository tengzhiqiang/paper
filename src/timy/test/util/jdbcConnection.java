package timy.test.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;


public class jdbcConnection {

	public Connection JdbcConnect() throws Exception{
		//驱动，数据库的驱动
		String driverClass = null;
//		链接数据库的url
		String jdbcUrl = null;
//		数据库用户名
		String user =null;
//		数据库密码
		String password =null;
		
		InputStream in = getClass().getClassLoader().getResourceAsStream("JDBC.properties");
		Properties properties = new Properties();
		properties.load(in);
		jdbcUrl = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		driverClass = properties.getProperty("driver");
		
		Driver driver = (Driver) Class.forName(driverClass).newInstance();
		Properties info= new Properties();
		info.put("user", user);
		info.put("password", password);
		
		Connection connection = driver.connect(jdbcUrl, info);
		
		return connection;	
	}
	
	public Connection simpleConnection() throws Exception {
		String driverClass = "com.mysql.jdbc.Driver";
		String jdbcUrl  = "JDBC:mysql://127.0.0.1:3306/test";
		String user = "root";
		String password  = "root";
		
//		注册数据库驱动
		Class.forName(driverClass);
		
//		建立数据库链接
		Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
		
		return connection;
		
	}
}
