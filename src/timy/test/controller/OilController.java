package timy.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timy.test.projo.Oil;
import timy.test.service.OilService;
import timy.test.service.ResultService;
import timy.test.util.JdbcTool;
import timy.test.util.Thread.delet_Thread;
import timy.test.util.Thread.strToDate_Thread;

public class OilController {
	private static String table = "t_oil";
	
	/**
	 * 第一步：处理无效数据
	 * @throws Exception
	 */
	public void dele_unusedata() {
		String sql = null;
		List<Oil> list = null;
		int start =358724;int end =start + 5000;////开始358724
		int i = 0; 
		List<Object> dele_Array = new ArrayList<Object>();
		List<String>list_dealtime = new ArrayList<String>();
		List<Integer>list_id = new ArrayList<Integer>();
		
		while (true) {
			sql = "select * from "+ table+" where id between ? and ? and remark = 0 " ;
			try {
				list = JdbcTool.getResultSet(Oil.class, sql, start, end);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i +=list.size();
			
		//####################  删除无效数据  更新时间  ############################  @@@@@@@@@@@可以改进的地方sql语句使用stringbuilder来拼接
			
			for (Oil cement : list) {
				if (cement.getTon()< 2||cement.getBatchport()==null) {
					dele_Array.add(cement.getId());
					if (dele_Array.size() == 100) {
						new Thread(new delet_Thread(dele_Array, 0, table)).start();
						dele_Array = new ArrayList<Object>();
						System.out.println("删除掉oil的无效记录to ID="+cement.getId()+"完成删除100条");
					}
				}else if (cement.getDate()==null) {
					try {
						list_dealtime.add(cement.getDealtime());
						list_id.add((int)cement.getId());
						if (list_id.size()==100) {
							new strToDate_Thread(list_dealtime, list_id,table).start();
							list_dealtime = new ArrayList<String>();
							list_id = new ArrayList<Integer>();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			if (list.size()==0&&end>994211) {
				break;
			}
			
			start = end;end = end + 5000;
			System.out.println("处理数据到*************"+end);
		}
		System.out.println("删除无效数据结束！"+i);
	}
	

	/**
	 * 第二步：查找对应记录并记录结果******用进港口记录查询出港口记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void dealList() throws Exception {
		boolean flag = true;
		String sql = null;
		String outsql = null;
		List<Oil> list = null;
		int start =358724;int end =start + 5000;//360327
		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		
		while (flag) {
			System.out.println("******主线程休息6秒*****");Thread.currentThread().sleep(6*1000);
			sql = "select * from "+ table +" where id between ? and ? and remark="+5+" and type="+1;
			list = JdbcTool.getResultSet(Oil.class, sql, start, end);
			i +=list.size();
			if (list.size()==0&&end > 676467) {
				break;
			}
			
			date = new Date();
			System.out.println("oil开始本次匹配="+df.format(date));
			
			
			for (Oil cement : list) {
				
	//			####################查找对应记录并记录结果#########################
				outsql = " select * from "+table+" where ton = "+cement.getTon()+" and batchport= '"+cement.getBatchport()+"'"+" and type = "+0+""
						+ " and remark = "+5+" and date > "+cement.getDate();
				List<Oil> cementlist = OilService.select(outsql);//查找对应的出港口信息
	
					Oil sortcement = null;
					if (cementlist.size()>1) {
						sortcement = cementlist.get(cementlist.size()/2 - 1);//找到一个时间为近似中间的一个记录进行匹配
						ResultService.saveResult(cement, sortcement);//保存记录结果
						JdbcTool.delet((int)cement.getId(), 2, table);
					}else if (cementlist.size()==1) {
						sortcement = cementlist.get(0);
						ResultService.saveResult(cement, sortcement);
						JdbcTool.delet((int)cement.getId(), 1,table);
					}else if (cementlist.size()==0) {
						JdbcTool.delet((int)cement.getId(), 9, table);
					}
							
				}
			date = new Date();
			start = end;end = end + 5000;
			System.out.println("oil结束本次匹配="+df.format(date)+"**********完成匹配记录数="+end);
		}
		System.out.println("用进港口记录查询出港口记录结束！"+i);
		
	}
	

}
