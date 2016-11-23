package timy.test.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.service.CoalService;
import timy.test.service.ResultService;
import timy.test.util.JdbcTool;

public class ramList {
    // 把出港记录放在多个list中进行循环
    public static boolean dealList(String table, int mid_five, int last) {
	String sql = null;
	List<Coal> list = null;
	int start = mid_five;
	int end = start + 5000;// 1364841 1444715 1465019 1483337 1483897
	int i = 0;
	Map<Integer, List<Coal>> map = null;// 用来存放出港记录
	Cement sortcement = null;
	List<Coal> changList = null;
	List<Cement> cementlist = null;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = null;
	while (true) {
	    // 获取进港记录
	    sql = "select * from " + table + " where id between ? and ? and remark=0 and type=1";
	    list = CoalService.select(sql, start, end);
	    i += list.size();
	    if (list.size() == 0 && end > last) {
		break;
	    }

	    date = new Date();
	    System.out.println("开始本次匹配=" + df.format(date));

	    // 获取到所有的出港记录
	    map = getAllList(table, mid_five);
	    List<Integer> int_list = new ArrayList<Integer>();

	    for (Cement cement : list) {
		cementlist = new ArrayList<Cement>();
		// ####################查找对应记录并记录结果#########################
		for (Entry<Integer, List<Coal>> entry : map.entrySet()) {
		    if (entry.getValue() != null) {

			changList = entry.getValue();
			for (int j = 0; j < changList.size(); j++) {

			    if (changList.get(j).getBatchport().equals(cement.getBatchport())) {
				if (changList.get(j).getTon() == cement.getTon()) {
				    if (changList.get(j).getDate().after(cement.getDate())) {
					// System.out.println(changList.get(j).getId()+","+changList.get(j).getRemark());
					if (changList.get(j).getRemark() == "0") {
					    if (cementlist.size() == 0) {
						changList.get(j).setRemark("1");
					    }
					    cementlist.add(changList.get(j));
					}
				    }
				}
			    }
			}
		    }
		}

		if (cementlist.size() > 1) {
		    sortcement = cementlist.get(0);
		    ResultService.saveResult(cement, sortcement);// 保存记录结果
		    int_list.add((int) cement.getId());
		    int_list.add(2);
		    // JdbcTool.delet((int)cement.getId(), 2, table);
		} else if (cementlist.size() == 1) {
		    sortcement = cementlist.get(0);
		    ResultService.saveResult(cement, sortcement);
		    int_list.add((int) cement.getId());
		    int_list.add(1);
		    // JdbcTool.delet((int)cement.getId(), 1, table);
		} else if (cementlist.size() == 0) {
		    int_list.add((int) cement.getId());
		    int_list.add(9);
		    // JdbcTool.delet((int)cement.getId(), 9, table);
		}
		if (int_list.size() > 1000) {
		    System.out.println("执行一次更新remark");
		    JdbcTool.update_remarks(table, int_list);
		    int_list = new ArrayList<Integer>();
		}

	    }
	    if (int_list.size() > 0) {
		JdbcTool.update_remarks(table, int_list);
		int_list = new ArrayList<Integer>();
	    }
	    date = new Date();
	    start = end;
	    end = end + 5000;
	    System.out.println("结束本次匹配=" + df.format(date)
		    + "**********完成匹配记录数=" + end);
	}
	System.out.println("该循环结束！" + i);

	return true;

    }
	
	/**
	 * 获取出港所有记录
	 * @param table
	 * @return
	 */
	public static Map<Integer, List<Coal>> getAllList(String table,int mid_five) {
		Map<Integer, List<Coal>> map = new HashMap<Integer, List<Coal>>();
		List<Coal> list = null;int key = 0;int start =0;int add =300000;
		boolean flag = true;
		
		while (flag) {
			String sql = "select * from "+table+" where id between ? and ? and type = 0 and remark = 0";
			list = CoalService.select(sql, start,start+add);
			start+=add;
			System.out.println(flag);
			if (start > mid_five&&list.size()<1) {
				flag = false;
			}
			if (list.size()>0) {
				map.put(key, list);key++;
				System.out.println("存放一个list"+key+"中间标志位：="+mid_five+",,,end="+start);
			}
			list= new ArrayList<Coal>();
		}
		System.out.println("结束一次存放map");
		return map;
	}
	
}
