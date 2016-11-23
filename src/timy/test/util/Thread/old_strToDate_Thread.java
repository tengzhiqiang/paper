package timy.test.util.Thread;

import timy.test.util.JdbcTool;
import timy.test.util.StrToDate;

public class old_strToDate_Thread extends Thread {
	
	private String opetime;
	private String time;
	private int id;

	public old_strToDate_Thread(String opetime, String time,int id) {
		this.id = id;
		this.time = time;
		this.opetime = opetime ;
	}
	
	
	@Override
	public void run() {
		try {
			String dealtime = StrToDate.strToDate(opetime, time);//更新时间
			String updatesql = "UPDATE t_oil SET date = "
					+ "( STR_TO_DATE('"+dealtime+"','%Y-%m-%d %T') ) , dealtime='"+dealtime+"', remark=5 WHERE id = "+id;
			JdbcTool.update(updatesql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
