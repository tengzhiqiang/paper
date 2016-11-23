package timy.test.util;

import java.sql.Connection;
import java.sql.Statement;

public class jdbcStratment {
	
	public void sqlSteatment() throws Exception {
		
		Connection cnn = null;
		Statement statement =null;
		try {
//		1.准备执行的sql语句
			String sql = " insert into t_worker (name ,age ,position)"
					+ "values ('jhon','23','HR')";
//		执行插入
//		1)、调用connect。createStatement 方法创建statement对象
			jdbcConnection jdbcConnection = new jdbcConnection();
			cnn = jdbcConnection.simpleConnection();
			statement = cnn.createStatement();
			
//		2).调用statement对象的更新方法执行sql语句
			statement.executeUpdate(sql);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//		关闭statement
			
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (cnn!=null) {
					cnn.close();
				}	
			}
			
			
			
		}
	}
	/**
	 * 通用的更新方法，更新，插入，保存
	 * @param sql
	 * @throws Exception 
	 */
	public void update(String sql ) throws Exception {
		
		Connection cnn = null;
		Statement statement = null;
		try {
//			jdbcConnection jdbcConnection = new jdbcConnection();
			cnn = JdbcTool.getConnection();//使用静态方法
			statement = cnn.createStatement();
			
			statement.executeUpdate(sql);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
//			JdbcTool.rease(cnn, statement);
		}
		
	}

}
