package timy.test.thread;

import java.util.List;

import timy.test.controller.CementController;
import timy.test.controller.DifferentDate;
import timy.test.projo.Coal;
import timy.test.util.getLastId;



public class CoalThread extends Thread {
	
	private String table;
	
	
	public CoalThread(String table) {
		super();
		this.table = table;
	}


	@Override
	public void run() {
		
		List<Coal> list = getLastId.starLast(table);
		CementController cement = new CementController();
//		coal.dele_unusedata(table,end);
		try {
			if (list!=null) {
//				System.out.println("处理的表为："+table+"中间记录为："+list.get(0).getId()+"，最后一条记录为："+list.get(1).getId());
//				ramList.dealList(table, (int)list.get(0).getId(), (int)list.get(1).getId());
			   cement.dealList(list, table);
			   DifferentDate.strtodate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}