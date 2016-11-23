package timy.test.util.Thread;

import java.util.List;

import timy.test.util.JdbcTool;

public class strToDate_Thread extends Thread {
	
	private List<String>list_dealtime;
	private List<Integer>list_id ;
	private String table;
//	private int id;

	public strToDate_Thread(List<String> list_dealtime, List<Integer> list_id,String table) {
		super();
		this.list_dealtime = list_dealtime;
		this.list_id = list_id;
		this.table = table;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("@@@@@@@@@@@@@@@@开始工作了，时间转换@@@@@@@@@@@@@@@@@@@@@@@");
			StringBuilder sql_dealtime = new StringBuilder("UPDATE "+table+" SET dealtime = case id ");
			StringBuilder sql_date = new StringBuilder(" end , date = case id ");
			StringBuilder sql_date_end = new StringBuilder(" end where id in ( 0");
			for (int i = 0; i < list_id.size(); i++) {
				sql_dealtime.append(" when "+list_id.get(i)+" then '"+list_dealtime.get(i)+"'");
				sql_date.append(" when "+list_id.get(i)+" then ( STR_TO_DATE('"+list_dealtime.get(i)+"','%Y-%m-%d %T') )");
				sql_date_end.append(","+list_id.get(i));
			}
			sql_date_end.append(")");
			
			sql_dealtime.append(sql_date);sql_dealtime.append(sql_date_end);
//			System.out.println(sql_dealtime.toString());
			JdbcTool.update(sql_dealtime.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
