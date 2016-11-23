package timy.test.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import timy.test.projo.Result;
import timy.test.service.ResultService;
import timy.test.util.getLastId;

public class DifferentDate {
//	获取每条记录的时间差
    public static void getDifferentData(List<Result> results) throws Exception {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	List<Double> list = new ArrayList<Double>();
	for (Result result : results) {
	    
	    float date = df.parse(result.getOuttime()).getTime()-df.parse(result.getIntime()).getTime();
	    
	    float minutes = date/(1000*60*60*24);
	    list.add((double) result.getId());
	    list.add((double) minutes);
	    if (list.size()==400) {
		ResultService.updateMinute(list);
		list = new ArrayList<Double>();
	    }
	}
	if (list.size()>0) {
	    
	    ResultService.updateMinute(list);
	}
	
    }
    
    
    public static void main(String[] args) throws Exception {
	int last = getLastId.lastId("t_result");
	int start = 0;int end = 0;
	while (true) {
	    
	    String sql = "select * from t_result where id between ? and ? and remark= 'non'";
	    List<Result> list =ResultService.select(sql, start, end);
	    getDifferentData(list);
	    start = end;
	    end =start +5000;
	    if (list.size()==0&&start>last) {
		break;
	    }
	}
	
    }
    
    
    public static void  strtodate() throws Exception {
    	int last = getLastId.lastId("t_result");
    	int start = 0;int end = 0;
    	while (true) {
    	    
    	    String sql = "select * from t_result where id between ? and ? and remark= 'non' and minutes > 0";
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
