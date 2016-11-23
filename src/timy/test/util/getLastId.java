package timy.test.util;

import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Coal;
import timy.test.service.CoalService;

public class getLastId {

	/**
	 * 获取最后一个数据id
	 * @param table
	 * @return
	 */
	public static int lastId(String table) {
		
		String sql = "select id, remark from "+table+" where remark = 10";
		try {
			List<Integer> list = CoalService.getlast(sql);
			if (list.size()> 0) {
				return (int) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;	
	}
	
	/**
	 * 获取进港记录的开始ID和结束ID
	 * @param table
	 * @return
	 */
	public static List<Coal> starLast(String table) {
		List<Coal> list = new ArrayList<Coal>();
		
		String sql = "SELECT * FROM "+table+" WHERE remark = 5 OR remark = 10";
		list = CoalService.select(sql);
		if (list.size()> 0) {
			return list;
		}else {
			
			return null;
		}
	}
}
