package timy.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timy.test.util.getLastId;

public class dealMain {

	public static void main(String[] args) {
		List<String> tables = new ArrayList<String>();
//		tables.add("t_building");
//		tables.add("t_cement");
//		tables.add("t_chemical");
		tables.add("t_coal");
		tables.add("t_grain");
		tables.add("t_iron");
		tables.add("t_mechanic");
		tables.add("t_metal");
		tables.add("t_nonmetallic");
		tables.add("t_oil");
		
		
		
		
	}
	
	
	public static void dealUnuser(String table,int end) {
		CoalController coalController = new CoalController();
		boolean set = true;
		if (set) {
			set = false;
			end = getLastId.lastId(table);
			if (end >0) {
				set = coalController.dele_unusedata(table,end);
			}else {
				System.out.println(table+"获取最后一条记录失败");
				new Throwable(table+"获取最后一条记录失败");
			}
		}
		System.out.println("结束的时间"+new Date());
	}

}
