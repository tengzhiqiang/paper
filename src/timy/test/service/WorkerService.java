package timy.test.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Worker;
import timy.test.util.JdbcTool;

public class WorkerService {
	
	static String sql = null;
	public static void delet(int id) {
		sql = "delet from "+Worker.getTabel()+"where id = "+id;
		try {
			JdbcTool.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int update(Worker worker) {
		
		if (worker.getId() > 0) {
			sql = "update "+Worker.getTabel()+"set age = "+worker.getAge()+"name = "+worker.getName()+""
					+ "position="+worker.getPosition()+"time="+worker.getTime();
		}else if (worker.getId() == 0) {
			sql = "insert into "+Worker.getTabel()+"(age, name, position) values ("+worker.getAge()+","+"'"+worker.getName()+"'"
					+ ",'"+worker.getPosition()+"time="+worker.getTime()+"')";
		}
		try {
			JdbcTool.update(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		return 1;
		
	}
	
	public static List<Worker> select(String select) throws Exception {
		
		Connection cnn = JdbcTool.getConnection();
		
		Statement statement = cnn.createStatement();
		
		ResultSet rs  = statement.executeQuery(select);
		List<Worker> list = new ArrayList<Worker>();
		Worker worker = null;
		while (rs.next()) {
			worker = new Worker();
			worker.setId(rs.getInt("id"));
			worker.setAge(rs.getInt("age"));
			worker.setName(rs.getString("name"));
			worker.setPosition(rs.getString("position"));
			worker.setTime(rs.getDate("time"));
			list.add(worker);
		}
		
		JdbcTool.rease(rs, cnn, statement);
		
		return list;
		
	}
	

}
