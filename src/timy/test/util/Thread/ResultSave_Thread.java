package timy.test.util.Thread;

import java.util.List;

import timy.test.projo.Cement;
import timy.test.projo.Result;
import timy.test.util.JdbcTool;

public class ResultSave_Thread extends Thread {
	
	private List<Cement> in_list;
	private List<Cement> out_list;
	
	
	
	public ResultSave_Thread(List<Cement> in_list, List<Cement> out_list) {
		super();
		this.in_list = in_list;
		this.out_list = out_list;
	}

	@Override
	public void run() {
		if (in_list.size()>0||out_list.size()>0) {
			
			StringBuilder builder = new StringBuilder("insert into "+Result.getTabel()+"(ton, outtime, intime, batchport, cargoout, cargoin, cargotype, remark, outid, inid) values ");
//			Cement cement = in_list.get(0);Cement sortcement = out_list.get(0);
			builder.append("("+in_list.get(0).getTon()+",'"+out_list.get(0).getDealtime()+"','"+in_list.get(0).getDealtime()+"','"+in_list.get(0).getBatchport()+"',"+
					out_list.get(0).getCargo()+","+in_list.get(0).getCargo()+",'"+out_list.get(0).getCargotype()+"','non',"+out_list.get(0).getId()+","+in_list.get(0).getId()+")");
			for (int i = 1; i < out_list.size(); i++) {
				builder.append(",("+in_list.get(i).getTon()+",'"+out_list.get(i).getDealtime()+"','"+in_list.get(i).getDealtime()+"','"+in_list.get(i).getBatchport()+"',"+
						out_list.get(i).getCargo()+","+in_list.get(i).getCargo()+",'"+out_list.get(i).getCargotype()+"','non',"+out_list.get(i).getId()+","+in_list.get(i).getId()+")");
			}
			try {
				JdbcTool.update(builder.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("您输入的匹配list为空，请修正！！！！！！！！！！！！！！！！！！！！");
		}
		
		
	}

}
