package timy.test.util.Thread;

import java.util.List;

import timy.test.projo.Cement;
import timy.test.projo.Result;
import timy.test.service.ResultService;
import timy.test.util.JdbcTool;

public class saveResult_Thread extends Thread {
	
//	private Cement sortcement;
	private Cement cement;
	private List<Cement> cementlist;
	private String table;
	
	public saveResult_Thread(Cement cement, List<Cement> cementlist, String table) {

		this.cement = cement;
		this.cementlist = cementlist;
		this.table = table;
	}

	@Override
	public void run() {
		Cement sortcement = null;
		if (cementlist.size()>1) {
			sortcement = cementlist.get(cementlist.size()/2 - 1);
			save_Result(cement, sortcement);//保存记录结果
			JdbcTool.delet((int)cement.getId(), 2, table);
		}else if (cementlist.size() ==1) {
			sortcement = cementlist.get(0);
			save_Result(cement, sortcement);
			JdbcTool.delet((int)cement.getId(), 1, table);
		}else if (cementlist.size() == 0) {
			JdbcTool.delet((int)cement.getId(), 9, table);
		}

		
	}
	
	
	
	protected void save_Result(Cement cement, Cement sortcement) {
		//*********写入检索到的result结果********
		Result result = null;
		result = new Result();
		result.setInid(sortcement.getId());
		result.setOutid(cement.getId());
		result.setBatchport(cement.getBatchport());
		result.setCargoin(cement.getCargo());
		result.setCargoout(sortcement.getCargo());
		result.setCargotype(sortcement.getCargotype());
//		result.setIntime(cement.getDealtime());
//		result.setOuttime(sortcement.getDealtime());
		result.setTon(cement.getTon());
		result.setRemark("non");
		ResultService.update(result);
	
	}
}
