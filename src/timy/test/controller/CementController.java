package timy.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.service.CementService;
import timy.test.service.ResultService;
import timy.test.thread.SaveResults;
import timy.test.util.JdbcTool;
import timy.test.util.Thread.strToDate_Thread;

public class CementController {
	
//	private static String table = "t_cement";
	/**
	 * 第一步：处理无效数据
	 * @throws Exception
	 */
	public void dele_unusedata(String table) {
		String sql = null;
		List<Cement> list = null;
		int start =0;int end =start + 5000;
		int i = 0;
		List<Object> dele_Array = new ArrayList<Object>();
		List<String>list_dealtime = new ArrayList<String>();
		List<Integer>list_id = new ArrayList<Integer>();
		
		
		while (true) {
			sql = "select * from t_cement where id between ? and ? " ;
			try {
				list = JdbcTool.getResultSet(Cement.class, sql, start,end);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i +=list.size();
			if (list.size()==0) {
				break;
			}
		//####################  删除无效数据  更新时间  ############################  @@@@@@@@@@@可以改进的地方sql语句使用stringbuilder来拼接
			
			for (Cement cement : list) {
				if (cement.getTon()< 5||cement.getBatchport()==null) {
					System.out.println("删除掉记录ID="+cement.getId());
					dele_Array.add(cement.getId());
					if (dele_Array.size() == 1000) {
						CementService.dele_array(dele_Array, 0, table);
						dele_Array = new ArrayList<Object>();
						System.out.println("删除掉oil的无效记录to ID="+cement.getId()+"完成删除100条");
					}
				}else if (cement.getDate()==null){
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
			start = end;end = end + 5000;
		}
		System.out.println("该循环结束！"+i);
	}
	
	/**
	 * 第二步：查找对应记录并记录结果******用进港口记录查询出港口记录
	 * @return
	 * @throws Exception
	 */
	public void dealList_old(List<Coal> list_int,String table) throws Exception {
		boolean flag = true;
		String sql = null;
		String outsql = null;
		List<Cement> list = null;
		int start =(int) list_int.get(0).getId();int end =start + 5000;//1364841  1444715  1465019 1483337 1483897
		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		
		while (flag) {
			sql = "select * from "+ table+" where id between ? and ? and remark=0 and type=1";
//			list = JdbcTool.getResultSet(Cement.class, sql, start, end);
			list = CementService.select(sql, start, end);
			i +=list.size();
			if (list.size()==0&&end>list_int.get(1).getId()) {
				break;
			}
			
			date = new Date();
			System.out.println("开始本次匹配="+df.format(date));
			
			for (Cement cement : list) {
				
	//			####################查找对应记录并记录结果#########################
				outsql = " select * from "+table+" where ton = ? and batchport= ?  and type = ? "
						+ " and remark = ? and date > ? and id < 1364842";
				List<Cement> cementlist = CementService.select( outsql,cement.getTon(),cement.getBatchport(),0
						,0, cement.getDate());//查找对应的出港口信息
//				List<Cement> cementlist = JdbcTool.getResultSet(Cement.class, outsql,cement.getTon(),cement.getBatchport(),0
//					,0, cement.getDate());//查找对应的出港口信息
	
					Cement sortcement = null;
					if (cementlist.size()>1) {
						sortcement = cementlist.get(cementlist.size()/2 - 1);//找到一个时间为近似中间的一个记录进行匹配
						ResultService.saveResult(cement, sortcement);//保存记录结果
						JdbcTool.delet((int)cement.getId(), 2, table);
					}else if (cementlist.size()==1) {
						sortcement = cementlist.get(0);
						ResultService.saveResult(cement, sortcement);
						JdbcTool.delet((int)cement.getId(), 1, table);
					}else if (cementlist.size()==0) {
						JdbcTool.delet((int)cement.getId(), 9, table);
					}
				}
			date = new Date();
			start = end;end = end + 5000;
			System.out.println("结束本次匹配="+df.format(date)+"**********完成匹配记录数="+end);
//			System.out.println("******主线程休息10s*****");
//			Thread.sleep(10*1000);
		}
		System.out.println("该循环结束！"+i);
		
	}
	
	
	public void dealList(List<Coal> list_int,String table) throws Exception {
		boolean flag = true;
		String sql = null;
		String outsql = null;
		List<Cement> list = null;
		int start =(int) list_int.get(0).getId();int end =start + 5000;//1364841  1444715  1465019 1483337 1483897
		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		
		List<Cement>inList =new ArrayList<Cement>();
		List<Cement> outList= new ArrayList<Cement>();
		
		while (flag) {
			sql = "select * from "+ table+" where id between ? and ? and remark=0 and type=1";
//			list = JdbcTool.getResultSet(Cement.class, sql, start, end);
			list = CementService.select(sql, start, end);
			i +=list.size();
			if (list.size()==0&&end>list_int.get(1).getId()) {
				break;
			}
			
			date = new Date();
			System.out.println("开始本次匹配="+table+",开始时间="+df.format(date));
			int index = 0;
			for (Cement cement : list) {
				
	//			####################查找对应记录并记录结果#########################
				outsql = " select * from "+table+" where ton = ? and batchport= ?  and type = ? "
						+ " and remark = ? and date > ? order by date";
				List<Cement> cementlist = CementService.select( outsql,cement.getTon(),cement.getBatchport(),0
						,0, cement.getDate());//查找对应的出港口信息
//				List<Cement> cementlist = JdbcTool.getResultSet(Cement.class, outsql,cement.getTon(),cement.getBatchport(),0
//					,0, cement.getDate());//查找对应的出港口信息
	
        		Cement sortcement = null;
        		if (cementlist.size() > 1) {
        		    sortcement = cementlist.get(0);// 找到一个时间为近似中间的一个记录进行匹配
//                		     ResultService.saveResult(cement, sortcement);// 保存记录结果
        		    inList.add(cement);
        		    outList.add(sortcement);
        		    JdbcTool.delet((int) cement.getId(), 2, table);
        		    index++;
        		} else if (cementlist.size() == 1) {
        		    sortcement = cementlist.get(0);
//                		     ResultService.saveResult(cement, sortcement);
        		    inList.add(cement);
        		    outList.add(sortcement);
        		    JdbcTool.delet((int) cement.getId(), 1, table);
        		    index++;
        		} else if (cementlist.size() == 0) {
        		    JdbcTool.delet((int) cement.getId(), 9, table);
        		}
                if (inList.size()==400) {
					SaveResults saveResults = new SaveResults(inList, outList);
					saveResults.start();
					inList = new ArrayList<Cement>();
					outList = new ArrayList<Cement>();
				}		
			}
			 if (inList.size()>0) {
					SaveResults saveResults = new SaveResults(inList, outList);
					saveResults.start();
					inList = new ArrayList<Cement>();
					outList = new ArrayList<Cement>();
				}
			date = new Date();
			start = end;end = end + 10000;
			System.out.println("结束本次匹配="+table+",结束时间="+df.format(date)+"***完成匹配记录数="+ index+",匹配到end="+end);
//			System.out.println("******主线程休息10s*****");
//			Thread.sleep(10*1000);
		}
		System.out.println("该循环结束！"+i);
		
	}

}
