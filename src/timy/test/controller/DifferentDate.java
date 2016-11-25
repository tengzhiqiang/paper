package timy.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Result;
import timy.test.service.ResultService;
import timy.test.thread.UpdateTime;
import timy.test.util.getLastId;

public class DifferentDate {
	
	
	
//	获取每条记录的时间差
    public static void getDifferentData(List<Result> results) throws Exception {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	List<Double> list = new ArrayList<Double>();
	int index = 0;
	for (Result result : results) {
	    
	    float date = df.parse(result.getOuttime()).getTime()-df.parse(result.getIntime()).getTime();
	    
	    float minutes = date/(1000*60*60*24);
	    list.add((double) result.getId());
	    list.add((double) minutes);
	    if (list.size()==800) {
		Thread.currentThread().sleep(1000*2);
	    System.out.println("建立一个线程");
		new Thread(new UpdateTime(list)).start();
//		ResultService.updateMinute(list);
		list = new ArrayList<Double>();
	    }
	}
	if (list.size()>0) {
	    
	    ResultService.updateMinute(list);
	}
	
    }
    
    
    @SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
	int last = getLastId.lastId("t_result");
	int start = 0;int end = start;
	while (true) {
	    
//	    String sql = "select * from t_result where id between ? and ? and minutes>0 and remark <>10";
	    String sql = "select * from t_result where id between ? and ? and minutes<1 and remark <>10";
	    List<Result> list =ResultService.select(sql, start, end); 
	    if (list==null&&list.size()==0) {		
		}else {
			getDifferentData(list);
		}
	    start = end;
	    end =start +5000;
	    System.out.println(end);
	    if (list.size()==0&&start>last) {
		break;
	    }
	}
	
    }
    
    
    public static void  strtodate() throws Exception {
    	int last = getLastId.lastId("t_result");
    	int start = 0;int end = 0;
    	while (true) {
    	    
    	    String sql = "select * from t_result where id between ? and ? and minutes > 0";
    	    List<Result> list =ResultService.select(sql, start, end);
    	    getDifferentData(list);
    	    start = end;
    	    end =start +5000;
    	    if (list.size()==0&&start>last) {
    	    	System.out.println("结束时间转换");
    	    	break;
    	    }
    	}
	}
}
