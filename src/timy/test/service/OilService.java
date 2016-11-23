package timy.test.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Oil;
import timy.test.util.JdbcTool;

public class OilService {
	
	private static String sql = null;
	private static String table = null;
	/**
	 * 
	 * @param id
	 * @param type 0删除   1已经读取过了 9未找到对应的相关记录
	 */
	public static void delet(int id,int type) {
		if (type == 0) {
			sql = "delete from "+table+" where id = "+id;
		}else if (type == 2) {
			sql = "update "+table+" set remark = 2 where id = "+id;
		}else if (type == 1) {
			sql = "update "+table+" set remark = 1 where id = "+id;
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
	 * @param oilid
	 * @return
	 * @throws Exception
	 */
	public static int update(String date, int oilid) throws Exception{
		sql = "update "+table+" set dealtime = '"+date+"' where id = "+oilid;
		JdbcTool.update(sql);	
		return 1;
	}
	
public static List<Oil> select(String select) throws Exception {
		
		Connection cnn = JdbcTool.getConnection();
		
		Statement statement = cnn.createStatement();
		
		ResultSet rs  = statement.executeQuery(select);
		List<Oil> list = new ArrayList<Oil>();
		Oil oil = null;
		while (rs.next()) {
			oil = new Oil();
			oil.setId(rs.getInt("id"));
			oil.setVessel(rs.getString("vessel"));
			oil.setFlag(rs.getString("flag"));
			oil.setRoute(rs.getString("route"));
			oil.setTon(rs.getFloat("ton"));
			oil.setNavigation(rs.getString("navigation"));
			oil.setTime(rs.getString("time"));
			oil.setOpetime(rs.getString("opetime"));
			oil.setPort(rs.getString("port"));
			oil.setBatchport(rs.getString("batchport"));
			oil.setAgency(rs.getString("agency"));
			oil.setCargo(rs.getFloat("cargo"));
			oil.setCargotype(rs.getString("cargotype"));
			oil.setType(rs.getString("type"));
			oil.setDealtime(rs.getString("dealtime"));
			oil.setDate(rs.getDate("date"));
//			System.out.println(rs.getDate("date"));
			
			list.add(oil);
		}
		
		JdbcTool.rease(rs, cnn, statement);
		
		return list;
		
	}
	

}
