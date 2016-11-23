package timy.test.util.Thread;

import java.util.List;

import timy.test.util.JdbcTool;

public class UpdateRemark extends Thread {

	private List<Integer>list_id;
	private List<String> list_remark;
	private String table;
	
	
	
	public UpdateRemark(List<Integer> list_id, List<String> list_remark,
			String table) {
		super();
		this.list_id = list_id;
		this.list_remark = list_remark;
		this.table = table;
	}



	@Override
	public void run() {
		
		try {
			StringBuilder builder = new StringBuilder("update "+table+" set remark = case id ");
			StringBuilder builder_end = new StringBuilder(" end , where id int (0");
			for (int i = 0; i < list_id.size(); i++) {
				builder.append(" when "+list_id.get(i)+" then '"+list_remark.get(i)+"'");
				builder_end.append(","+list_id.get(i));
			}
			builder_end.append(")");
			builder.append(builder_end);
			JdbcTool.update(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
