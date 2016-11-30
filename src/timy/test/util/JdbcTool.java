package timy.test.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import timy.test.projo.Result;

public class JdbcTool {
	
	public static void rease(ResultSet rs, Connection cnn, Statement statement) {
		
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if (statement != null) {
					statement.close();
					statement = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				
				try {
					if (cnn != null) {
						cnn.close();
						cnn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	

	public static Connection getConnection () throws Exception {
		
		String driverClass = null;
		String url = null;
		String user = null;
		String password  = null;
		Connection cnn = null;
		
		try {
			
			InputStream in = JdbcTool.class.getClassLoader().getResourceAsStream("JDBC.properties");
//			System.out.println("输出该路径="+JdbcTool.class.getClassLoader());
			
			Properties properties = new Properties();
			properties.load(in);
			
			driverClass = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
			Class.forName(driverClass);
			
			cnn = DriverManager.getConnection(url,user,password);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return cnn;
		
	}
	
	/**
	 * 通用的更新方法，更新，插入，保存
	 * @param sql
	 * @throws Exception 
	 */
	public static void update(String sql, Object...args) throws Exception {
		
		Connection cnn = null;
		PreparedStatement preparedStatement = null;
		try {
			cnn = getConnection();//使用静态方法  getConnection()   DruidUtil.getconnection()
//			statement = cnn.createStatement();
//			statement.executeUpdate(sql);
			
//			使用PreparedStatement
			preparedStatement = cnn.prepareStatement(sql);
			if (args.length>0) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i+1, args[i]);
				}
			}
			preparedStatement.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			JdbcTool.rease(null, cnn, preparedStatement);
		}
		
	}
	/**
	 * 获取list对象集合的通用方法
	 * @param clazz 类.class
	 * @param sql sql语句，是要有占位符的
	 * @param args 动态数组用于存放参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E>  getResultSet(Class<E> clazz, String sql , Object...args) {
		E tomy = null;
		Connection cnn = null;
		PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		List<Object>list = new ArrayList<Object>();
		try {
			
//			cnn= C3poDriver.driverConnection();
			cnn= getConnection();
			ps = cnn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			//获取一个结果集
			rs = ps.executeQuery();
			//得到一个resultsetmetdata对象
			
			while (rs.next()) {
				Map<String , Object> values = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				//利用反射机制创建对象
				tomy = clazz.newInstance();
				//通过解析sql语句解析获取的列名和值
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String filename = rsmd.getColumnLabel(i+1);
					if (filename.equals("id")) {
						int id = (int)rs.getInt("id");
						values.put(filename, id);
					}
					Object filevalue = rs.getObject(filename);
					ReflectionUtils.setFieldValue(tomy, filename, filevalue);
				}
				list.add( tomy);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcTool.rease(rs, cnn, ps);
		}
		return (List<E>) list;
		
	}
	
	/**
	 * 通用删除数据程序
	 * @param id  删除的id
	 * @param type 类型0删除 1、2、9 remark
	 * @param table  表名
	 */
	public static  void delet(int id,int type, String table) {
		String sql = null;
		if (type == 0) {
			sql = "delete from "+table+" where id = ?";
		}else if (type == 1) {
			sql = "update "+table+" set remark = 1 where id = ?";
		}else if (type == 2) {
			sql = "update "+table+" set remark = 2 where id = ?";
		}else if (type == 9) {
			sql = "update "+table+" set remark = 9 where id = ?";
		}
		
		try {
			JdbcTool.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateremark(int id,int second,int type, String table) {
//		String builder = " update "+table+" set remark = case id when "+id+" then "+type
//				+" when "+second+" then 1 end where id in ("+id+","+second+")";
		String sql = " update "+table+" set remark = case id when ? then ? when ? then ? end where id in (?,?)";
//		System.out.println(sql+""+table);
		try {
			JdbcTool.update(sql,id,type,second,1,id,second);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void update_remarks(String table, List<Integer> list) {
		StringBuilder builder = new StringBuilder("update "+table+" set remark = case id ");
		StringBuilder endBuilder = new StringBuilder(" end where id in (0");
		for (int i = 0; i < list.size(); i+=2) {
			builder.append(" when "+list.get(i)+" then "+list.get(i+1));
			endBuilder.append(","+list.get(i)+","+list.get(i+1));
		}
		endBuilder.append(")");
		builder.append(endBuilder);
		try {
			JdbcTool.update(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void savelist(List<Result>list, String table) {
	    StringBuilder builder = new StringBuilder("INSERT INTO t_result (ton, outtime, intime, batchport, cargoout,"
	    	+ "cargoin, cargotype, inid, outid, remark) VALUES");
	    Result result = list.get(0);
		builder.append("("+result.getTon()+",'"+result.getOuttime()+"','"+result.getIntime()+"','"+result.getBatchport()+"',"+result.getCargoout()
			+","+result.getCargoin()+",'"+result.getCargotype()+"',"+result.getInid()+","+result.getOutid()+",'"+result.getRemark()+"')");

	   for (int i = 1; i < list.size(); i++) {
	       result = list.get(i);
		builder.append(",("+result.getTon()+",'"+result.getOuttime()+"','"+result.getIntime()+"','"+result.getBatchport()+"',"+result.getCargoout()
			+","+result.getCargoin()+",'"+result.getCargotype()+"',"+result.getInid()+","+result.getOutid()+",'"+result.getRemark()+"')");
	    }
	    try {
		update(builder.toString());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

}
