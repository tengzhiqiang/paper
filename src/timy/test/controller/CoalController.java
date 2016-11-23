package timy.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.service.CoalService;
import timy.test.util.JdbcTool;
import timy.test.util.StrToDate;
import timy.test.util.Thread.ResultSave_Thread;
import timy.test.util.Thread.UpdateRemark;
import timy.test.util.Thread.delet_Thread;
import timy.test.util.Thread.strToDate_Thread;

public class CoalController {
//	private static String table = "t_coal";
	
	/**
	 * 第一步：处理无效数据
	 * @param last 最后一条数据
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public boolean dele_unusedata(String table, int last) {
		String sql = null;
		List<Coal> list = null;
		int start =0;int end =start + 5000;//540442
		int i = 0; int count = 0;
		List<Object> dele_Array = new ArrayList<Object>();
		List<String>list_dealtime = new ArrayList<String>();
		List<Integer>list_id = new ArrayList<Integer>();
	
		while (true) {
			
			sql = "select * from "+ table+" where id between ? and ? ";
			try {
//				list = JdbcTool.getResultSet(Coal.class, sql, start, end);
				list = CoalService.select(sql,start,end);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i +=list.size();
			
			count++;
			if (count>20) {
				try {
					count = 0;
					Thread.currentThread().sleep(1000*10);
					System.err.println("***********休息10s************");
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		//####################  删除无效数据  更新时间  ############################  @@@@@@@@@@@可以改进的地方sql语句使用stringbuilder来拼接
			
			for (Coal cement : list) {
				if (cement.getTon()< 5||cement.getBatchport()==null) {
					dele_Array.add(cement.getId());
					if (dele_Array.size() == 1000) {
						new Thread(new delet_Thread(dele_Array, 0, table)).start();
						dele_Array = new ArrayList<Object>();
						System.out.println("删除掉"+table+"的无效记录to ID="+cement.getId()+"完成删除1000条");
					}
//					CoalService.delet(cement.getId(),0);
				}else if(cement.getDate()==null){//
					try {
						String dealtime  = StrToDate.strToDate(cement.getOpetime(), cement.getTime());
						list_dealtime.add(dealtime);
						list_id.add((int)cement.getId());
						if (list_id.size()==500) {
							System.out.println("开始工作了，时间转换。");
							new strToDate_Thread(list_dealtime, list_id,table).start();
							list_dealtime = new ArrayList<String>();
							list_id = new ArrayList<Integer>();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}			
			if (list.size()==0&&end>last) {
				break;
			}
			start = end;end = end + 5000;
		}
		
//		如果在最后没有大于1000的话也要执行
		if (dele_Array.size()>0||list_id.size()>0) {
			if (dele_Array.size()>0) {
				new Thread(new delet_Thread(dele_Array, 0, table)).start();
			}
			if (list_id.size()>0) {
				new strToDate_Thread(list_dealtime, list_id,table).start();
			}
		}
		
		System.out.println("删除无效数据 结束！"+i);
		return true;
	}
	

	
	/**
	 * 第二步：查找对应记录并记录结果******用进港口记录查询出港口记录
	 * @return
	 * @throws Exception
	 */
	public void dealList(String table) throws Exception {
		boolean flag = true;
		String sql = null;
		String outsql = null;
		List<Coal> list = null;
		int start =880667;int end =start + 5000;//880668
//		int i = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		List<Cement> in_list = new ArrayList<Cement>();
		List<Cement> out_list = new ArrayList<Cement>();
//		删除用的
		List<Integer> list_id = new ArrayList<Integer>();
		List<String> list_remark = new ArrayList<String>();
		
		while (flag) {
//			System.out.println("******coal主线程休息6s*****");Thread.currentThread().sleep(6*1000);
			sql = "select * from "+ table +" where id between ? and ? and remark="+0+" and type="+1;
			list = JdbcTool.getResultSet(Coal.class, sql, start, end);
//			i +=list.size();
			if (list.size()==0 && end > 1464056) {
				break;
			}
			
			date = new Date();
			System.out.println("coal开始本次匹配="+df.format(date));
			
			
			for (Coal cement : list) {
				
	//			####################查找对应记录并记录结果#########################
				outsql = " select * from "+table+" where ton = ? and batchport= ? and type = ? "
						+ " and remark = ? and date > ? and id < 849206";
				List<Coal> cementlist = JdbcTool.getResultSet(Coal.class, outsql,cement.getTon(),cement.getBatchport(),0
						,0, cement.getDate());//查找对应的出港口信息
//				List<Coal> cementlist = CoalService.select( outsql,cement.getTon(),cement.getBatchport(),0
//						,0, cement.getDate());
					Coal sortcement = null;
					if (cementlist.size()>1) {
						sortcement = cementlist.get(cementlist.size()/2 - 1);//找到一个时间为近似中间的一个记录进行匹配
//						ResultService.saveResult(cement, sortcement);//保存记录结果
						in_list.add(cement);out_list.add(sortcement);
//						JdbcTool.updateremark((int)cement.getId(),(int)sortcement.getId(), 2, table);
						list_id.add((int)cement.getId());list_remark.add("2");
						list_id.add((int)sortcement.getId());list_remark.add("1");
					}else if (cementlist.size()==1) {
						sortcement = cementlist.get(0);
//						ResultService.saveResult(cement, sortcement);
						in_list.add(cement);out_list.add(sortcement);
//						JdbcTool.updateremark((int)cement.getId(),(int)sortcement.getId(), 1, table);
						list_id.add((int)cement.getId());list_remark.add("1");
						list_id.add((int)sortcement.getId());list_remark.add("1");
					}else if (cementlist.size()==0) {
//						JdbcTool.delet((int)cement.getId(), 9, table);
						list_id.add((int)cement.getId());list_remark.add("9");
					}
					if (out_list.size()==100) {
						new ResultSave_Thread(in_list, out_list).start();
						in_list = new ArrayList<Cement>();
						out_list = new ArrayList<Cement>();
						System.out.println("完成一次保存。100条result");
					}
					if (list_id.size()==100) {
						new UpdateRemark(list_id, list_remark, table);
						list_id = new ArrayList<Integer>();
						list_remark = new ArrayList<String>();
						System.out.println("完成一次删除？？？？？？100");

					}
				}
			date = new Date();
			System.out.println("coa##################l结束本次匹配="+df.format(date)+"**********完成匹配记录数="+end);
			start = end;end = end + 5000;
		}
//		运行结束后是否还有没有操作的数据
		if (out_list.size()>0) {
			new ResultSave_Thread(in_list, out_list).start();
			in_list = new ArrayList<Cement>();
			out_list = new ArrayList<Cement>();
		}
		if (list_id.size()>0) {
			new UpdateRemark(list_id, list_remark, table);
			list_id = new ArrayList<Integer>();
			list_remark = new ArrayList<String>();
		}
		System.out.println("用进港口记录查询出港口记录结束！"+df.format(date));
		
	}

}
