package timy.test.util.Thread;

import java.util.List;

import timy.test.util.JdbcTool;

public class delet_Thread extends Thread {
	
	private List<Object> list;
//	private int id=0;
	private int type ;
	private String table;
	
	public delet_Thread(List<Object> dele_Array, int type, String table) {
		this.list = dele_Array;
		this.type = type;
		this.table = table;
	}
	
	@Override
	public void run() {
		if (list.size() > 0) {
			StringBuilder sqlBuilder = new StringBuilder();
			if (type==0) {
				sqlBuilder.append("delete from "+table+" where id in ( 0");
				for (Object i : list) {
					sqlBuilder.append(","+i);
				}
				sqlBuilder.append(" )");
			}
			try {
				JdbcTool.update(sqlBuilder.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("对不起，您输入的list为空，删除失败！线程=delet_Thread");
		}
	}

}
