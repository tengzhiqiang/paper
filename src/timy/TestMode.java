package timy;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.projo.Oil;
import timy.test.service.CoalService;
import timy.test.util.Thread.ResultSave_Thread;

public class TestMode {
	
	
	
	@Test
	public void test_resultSet(){
		String sql = "select * from t_oil where id = ?";
		List<Oil> list = timy.test.util.JdbcTool.getResultSet(Oil.class, sql, 358731);
//		System.out.println(list.get(0).toString());
		List<Cement> cementlist = new ArrayList<Cement>();
		List< Cement >sortlist = new ArrayList<Cement>();
		cementlist.add(list.get(0));sortlist.add(list.get(0));
		new ResultSave_Thread(cementlist, sortlist).start();
		
		
	}

	@Test
	public void test_coalNull(){
		String sql = "select * from t_coal where id = "+569259;
		
		try {
			List<Coal> list = CoalService.select(sql);
			System.out.println(list.get(0));
			System.out.println("********************************");
			System.out.println("batchport="+list.get(0).getBatchport()==null+"@@@@@@@@@@@");
			String batch = list.get(0).getBatchport();
			System.out.println(batch.equals(list.get(0).getBatchport()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test//sql.date和util.date的时间转换,并计算时间差
	public void test_changedate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = null;
		str = sdf.format(new Date());
		Date date = null;
		date = sdf.parse(str);
		Timestamp time = new Timestamp(date.getTime());
		
		
		str = "2016-10-21 20:08:22";
		date = sdf.parse(str);
		Timestamp time_second = new Timestamp(date.getTime());
		
		long minute = time.getHours()*60+time.getMinutes()-(time_second.getHours()*60+time_second.getMinutes());
//		Worker worker = new Worker();
//		worker.setTime(time);
//		WorkerService.update(worker);
		System.out.println("相差分钟数="+minute);
	}

}
