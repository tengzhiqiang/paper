package timy.test.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import timy.test.projo.Cement;
import timy.test.util.JdbcTool;

public class CementService {
	
	static String sql = null;
	/**
	 * 通用删除数据程序
	 * @param id  删除的id
	 * @param type 类型0删除 1、2、9 remark
	 * @param table  表名
	 */
	public static  void delet(int id,int type, String table) {
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
	
	public static void dele_array(List<Object> dele_Array, int type, String table) {
		StringBuilder sqlBuilder = new StringBuilder();
		if (type==0) {
			sqlBuilder.append("delete from "+table+" where id in ( 0");
			for (Object i : dele_Array) {
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
	 * @param cementid
	 * @param table 表名
	 * @return
	 * @throws Exception
	 */
	public static int update(String date, int cementid, String table) throws Exception{
		sql = "update "+table+" set dealtime = '"+date+"' where id = "+cementid;
		JdbcTool.update(sql);	
		return 1;
	}
	/**
	 * 使用反射代替，jdbctool.getResultSet
	 * @param select
	 * @return
	 * @throws Exception
	 */
    public static List<Cement> select(String select, Object...args) throws Exception {

	java.sql.Connection cnn = JdbcTool.getConnection();

//	Statement statement = cnn.createStatement();
//
//	ResultSet rs = statement.executeQuery(select);
	java.sql.PreparedStatement ps = cnn.prepareStatement(select);
	for (int i = 0; i < args.length; i++) {
		ps.setObject(i+1, args[i]);
	}
	ResultSet rs = ps.executeQuery();
	
	List<Cement> list = new ArrayList<Cement>();
	Cement cement = null;
	while (rs.next()) {
	    cement = new Cement();
	    cement.setId(rs.getInt("id"));
	    cement.setVessel(rs.getString("vessel"));
	    cement.setFlag(rs.getString("flag"));
	    cement.setRoute(rs.getString("route"));
	    cement.setTon(rs.getFloat("ton"));
	    cement.setNavigation(rs.getString("navigation"));
	    cement.setTime(rs.getString("time"));
	    cement.setOpetime(rs.getString("opetime"));
	    cement.setPort(rs.getString("port"));
	    cement.setBatchport(rs.getString("batchport"));
	    cement.setAgency(rs.getString("agency"));
	    cement.setCargo(rs.getFloat("cargo"));
	    cement.setCargotype(rs.getString("cargotype"));
	    cement.setType(rs.getString("type"));
	    cement.setRemark(rs.getString("remark"));
	    cement.setDealtime(rs.getString("dealtime"));
	    cement.setDate(rs.getDate("date"));
	    // System.out.println(rs.getDate("date"));

	    list.add(cement);
	}

	JdbcTool.rease(rs, cnn, ps);

	return list;

    }

}
