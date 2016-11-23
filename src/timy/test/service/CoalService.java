package timy.test.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Coal;
import timy.test.util.JdbcTool;

public class CoalService {
	
	static String sql = null;
	static String table = "t_coal";
	/**
	 * 
	 * @param id
	 * @param type 0删除   1已经读取过了 9未找到对应的相关记录
	 */
	public static void delet(int id,int type) {
		if (type == 0) {
			sql = "delete from "+table+" where id = "+id;
		}else if (type == 1) {
			sql = "update "+table+" set remark = 1 where id = "+id;
		}else if (type == 2) {
			sql = "update "+table+" set remark = 2 where id = "+id;
		}else if (type == 9) {
			sql = "update "+table+" set remark = 9 where id = "+id;
		}
		
		try {
			JdbcTool.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dele_array(int[] array, int type) {
		StringBuilder sqlBuilder = new StringBuilder();
		if (type==0) {
			sqlBuilder.append("delete from "+table+" where id in ( 0");
			for (int i : array) {
				sqlBuilder.append(","+i);
			}
			sqlBuilder.append(" )");
		}
		try {
			JdbcTool.update(sqlBuilder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新时间用的
	 * @param date
	 * @param Coalid
	 * @return
	 * @throws Exception
	 */
	public static int update(String date, int Coalid) throws Exception{
		sql = "update "+table+" set dealtime = '"+date+"' where id = "+Coalid;
		JdbcTool.update(sql);	
		return 1;
	}
	
    public static List<Coal> select(String select, Object... args) {

	Connection cnn = null;
	// Statement statement = cnn.createStatement();
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<Coal> list = null;
	try {
	    cnn = JdbcTool.getConnection();

	    preparedStatement = cnn.prepareStatement(select);
	    for (int i = 0; i < args.length; i++) {
		preparedStatement.setObject(i + 1, args[i]);
	    }
	    rs = preparedStatement.executeQuery();
	    list = new ArrayList<Coal>();
	    Coal coal = null;
	    while (rs.next()) {
		coal = new Coal();
		coal.setId(rs.getInt("id"));
		coal.setVessel(rs.getString("vessel"));
		coal.setFlag(rs.getString("flag"));
		coal.setRoute(rs.getString("route"));
		coal.setTon(rs.getFloat("ton"));
		coal.setNavigation(rs.getString("navigation"));
		coal.setTime(rs.getString("time"));
		coal.setOpetime(rs.getString("opetime"));
		coal.setPort(rs.getString("port"));
		coal.setBatchport(rs.getString("batchport"));
		coal.setAgency(rs.getString("agency"));
		coal.setCargo(rs.getFloat("cargo"));
		coal.setCargotype(rs.getString("cargotype"));
		coal.setType(rs.getString("type"));
		coal.setRemark(rs.getString("remark"));
		coal.setDealtime(rs.getString("dealtime"));
		coal.setDate(rs.getDate("date"));
		// System.out.println(rs.getDate("date"));

		list.add(coal);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {

	    JdbcTool.rease(rs, cnn, preparedStatement);
	}

	return list;

    }

	public static List<Integer> getlast(String sql) {
	    
	    @SuppressWarnings("unused")
	    class LastId{
		int id;
		String remark;
		public int getId() {
		    return id;
		}
		public void setId(int id) {
		    this.id = id;
		}
		public String getRemark() {
		    return remark;
		}
		public void setRemark(String remark) {
		    this.remark = remark;
		}
		
	    }
	    
	    Connection cnn = null;
	    java.sql.Statement statement = null;
	    ResultSet rs = null;
	    List<Integer> list = new ArrayList<Integer>();
	    
	    try {
		cnn = JdbcTool.getConnection();
		statement = cnn.createStatement();
		rs = statement.executeQuery(sql);
		
		LastId lastId = null;
		while (rs.next()) {
		    lastId = new LastId();
		    lastId.setId(rs.getInt("id"));
		    lastId.setRemark(rs.getString("remark"));
		    list.add(lastId.getId());
		}
		
	    } catch (SQLException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return list;
	}
	

}
