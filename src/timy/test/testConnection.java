package timy.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mchange.v2.c3p0.impl.NewPooledConnection;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.projo.Oil;
import timy.test.projo.Worker;
import timy.test.service.CementService;
import timy.test.service.CoalService;
import timy.test.service.OilService;
import timy.test.service.WorkerService;
import timy.test.util.JdbcTool;
import timy.test.util.jdbcStratment;

public class testConnection {
	
	
	@Test//测试获取list的时间
    public void getlists() {
	String strs = "abbabba";
	String strb = "bb";
	System.out.println(strs.replace("a", "A"));
	
    }
	
	@Test
	public void test_list_object(){
		class students{
			private int age;
			private String name;
			private List<Integer>list;
			
			public students(int age, String name) {
				this.age = age;
				this.name = name;
			}
			
			
			public void setAge(int age) {
				this.age = age;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getName() {
				return name;
			}
			public void setList(List<Integer> list) {
				this.list = list;
			}
			public List<Integer> getList() {
				return list;
			}


			@Override
			public String toString() {
				return "students [age=" + age + ", name=" + name + ", list="
						+ list + "]";
			}
		}
		
		List<students> list = new ArrayList<students>();
		List<Integer> int_list = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			int_list.add(i);
		}
		list.add(new students(10, "timy"));
		list.add(new students(21, "peeny"));
		for (students students : list) {
			if (students.getName().equals("timy")) {
				students.setAge(200);
				students.setList(int_list);
			}
		}
		for (int i = 0; i < list.get(0).getList().size(); i++) {
			if (i/2==0) {
				list.get(i).getList().set(i, 999);
			}
		}
		for (int i = 0; i < int_list.size(); i++) {
			System.out.println(int_list.get(i));;
		}
	}
	
	@Test
	public void test_updatemore() throws Exception{
//		StringBuilder sql = new StringBuilder(" update t_worker set age = case id");
//		StringBuilder sql_end = new StringBuilder(" end where id in ( 0");
//		List<Object>list_id = new ArrayList<Object>();
//		list_id.add(10);list_id.add(11);list_id.add(12);list_id.add(13);
//		List<Object> list_age = new ArrayList<Object>();
//		list_age.add(7);list_age.add(8);list_age.add(8);list_age.add(8);
//		for (int i = 0; i < list_age.size(); i++) {
//			sql.append(" when "+list_id.get(i)+" then "+list_age.get(i));
//			sql_end.append(","+list_id.get(i));
//		}
//		sql_end.append(")");sql.append(sql_end);
//		JdbcTool.update(sql.toString());
//		System.out.println(sql.toString());
		
		
		String dealtime1 = "2016-12-30 22:33:44";//23
		String dealtime2 = "2016-10-30 20:30:44";//24
		List<String> list_dealtime = new ArrayList<String>();list_dealtime.add(dealtime1);list_dealtime.add(dealtime2);
		List<Object> list_date_id = new ArrayList<Object>();list_date_id.add(358724);list_date_id.add(358725);
		StringBuilder sql_dealtime = new StringBuilder("UPDATE t_oil SET dealtime = case id ");
		StringBuilder sql_date = new StringBuilder(" end , date = case id ");
		StringBuilder sql_date_end = new StringBuilder(" end where id in ( 0");
		for (int i = 0; i < list_date_id.size(); i++) {
			sql_dealtime.append(" when "+list_date_id.get(i)+" then '"+list_dealtime.get(i)+"'");
			sql_date.append(" when "+list_date_id.get(i)+" then ( STR_TO_DATE('"+list_dealtime.get(i)+"','%Y-%m-%d %T') )");
			sql_date_end.append(","+list_date_id.get(i));
		}
		sql_date_end.append(")");
		
		sql_dealtime.append(sql_date);sql_dealtime.append(sql_date_end);
		System.out.println(sql_dealtime.toString());
		JdbcTool.update(sql_dealtime.toString());
		
		
	}
	
	
	@Test 
	public void test_unuseport() throws Exception{
//		String sql = "select * from t_oil where id = 458638 ";
//		List<Cement> list = CementService.select(sql);
//		System.out.println(list.get(0).getBatchport()==null);
//		System.out.println(list.get(0).getBatchport().isEmpty());
		
		
		
		int end = 0;int start = 0;
		List<Oil> list = null;
		int [] array = new int[100];int index = 0;
//		PreparedStatement preparedStatement = null;
//		Connection cnn = null;
		while (true) {
			if (end<994211) {
				end = start + 10000;
				String sql = "select * from t_oil where id between "+start+" and "+end;
				list = OilService.select(sql);
				for (Oil oil : list) {
					if (oil.getBatchport()==null) {
						array[index] = (int)oil.getId();index++;
						if (array.length == 100) {
//							OilService.dele_array(array, 0);
							index =0;
							System.out.println("删除掉coal的无效记录to ID="+oil.getId()+"完成删除50条");
						}
					}
				}
			}
			start = end;
		}
		
	}
	
	@Test//@@@@@@@@@@@@@@失     败@@@@@@@@@@@@@@@@
	public void test_sort(){
		Worker worker = new Worker();
		worker.setAge(12);
		worker.setId(1);
		
		Worker worker2 = new Worker();
		worker2.setAge(11);
		worker2.setId(2);

		
		List<Worker> list = new ArrayList<Worker>();
		
		list.add(worker);
		list.add(worker2);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		System.out.println("转换位置***************8");
		Worker mid = new Worker();
		mid = list.get(0);
		list.add(0, list.get(1));
		mid.setId(5);
		list.add(1,mid);
		for (int j = 0; j < list.size(); j++) {
			System.out.println(list.get(j)+"hashcode="+list.get(j).hashCode());
		}
	}
	
	
	
	@Test
	public void test_insert() throws Exception{
//		String str= "网管";
//		String sql =" INSERT INTO t_worker (name) VALUES ('"+str+"')";
//		JdbcTool.update(sql);
		
		String sql = "select * from t_worker";
		List<Worker>list = WorkerService.select(sql);
		System.out.println(list.get(0).toString());
		
	}
	
	@Test
	public void test_batchport() throws Exception {
		Cement cement = new Cement();
//		String sql = null;
//		List<Cement> list = null;
//		sql = "select * from "+ Cement.getTabel()+" where id ="+1 ;
//		list = CementService.select(sql);

		System.out.println("看看batchport是什么输出："+cement.getTabel());
	}
	
	@Test                       //删除了ton小于10的数据，
	public void test_resulttime() throws Exception {
		String sql = null; String updatesql = null;
		List<Cement> list = null;
		int start =0;int end =start + 5000;
		int count = 0;
		while (true) {
			
//			sql = "select * from "+ Cement.getTabel()+" where id between "+start+" and "+end ;
//			list = CementService.select(sql);
			
			if (list.size()==0||end > 488830) {
				break;
			}
			
			for (Cement cement : list) {
//				if (cement.getTon()< 10||cement.getPort()==null) {
				if (cement.getBatchport()==" "||cement.getBatchport()==null) {
					System.out.println("删除掉记录ID="+cement.getId());
//					CementService.delet(cement.getId(),0);
					count++;
//					list.remove(cement);
				}
//				else {
//					//更新时间
//				String dealtime = StrToDate.strToDate(cement.getOpetime(), cement.getTime());
////				CementService.update(dealtime, cement.getId());
////				处理dealtime，转换成日期格式
//				updatesql = "UPDATE t_cement SET date = "
//						+ "( STR_TO_DATE('"+dealtime+"','%Y-%m-%d %T') ) and dealtime="+dealtime+" WHERE id = "+cement.getId();
//				JdbcTool.update(updatesql);
//				}
				
				
			}
			System.out.println("已经完成了="+end+"=条记录");
			start = end;
			end =start + 5000;
		}
		
		System.out.println("#################运行结束了,删除了="+count+"############");

	}
	
//	478008
	@Test
	public void test_cement_sql() throws Exception {
//		String sql = "select * from "+Cement.getTabel() + " where id = 498008";
//		List< Cement> list = CementService.select(sql);
//		System.out.println(list.get(0) );

	}
	
	
	
	/**
	 * cement 时间转换
	 * @throws Exception
	 */
	@Test
	public void strToDate() throws Exception{
		String str  = "2012-12-12 04:33:33";
		String opetime = "20120314";
		String time = "14-3? -12 07.00.00.000000 靠";
		
		
		opetime = opetime.substring(0,4)+"-"+opetime.substring(4, 6)+"-"+opetime.substring(6,8);
		time = time.substring(9, 18).replace(".", ":");
		str = opetime+time;
		System.out.println(str);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = dateFormat.parse(str);
		
		System.out.println(dateFormat.format(date));
		
//		opetime=201-0-1   time= 07.00.  [x, y )
//		System.out.println("opetime="+opetime+"time="+time);
		
//		cement时间转换
		
//		String sql = " select * from "+Cement.getTabel()+"where id < 10";
//		List<Cement> list = CementService.select(sql);
//		for (Cement cement : list) {
//			str = cement.getOpetime()
//			
//		}
		
	}
	
	
	@Test
	public void test_worker() throws Exception {
		
				
		String sql = null;
//		保存
		Worker worker = new Worker();
		worker.setAge(20);
		worker.setName("edimy");
		worker.setPosition("manager");
		
		System.out.println(WorkerService.update(worker));
		
//		更新age=1000
		worker.setAge(1000);
		System.out.println(WorkerService.update(worker));
		
//		删除
		WorkerService.delet(1);
		
//		查找
		sql = "select * from "+Worker.getTabel();
		List<Worker>list = new ArrayList<Worker>();
		try {
			list = WorkerService.select(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Worker worker2 : list) {
			System.out.println(worker2);
		}
		
	}
	
	/**
	 * resultSet也是需要关闭的
	 * @throws Exception 
	 */
	@Test
	public void testResultSet() throws Exception {
		Connection cnn = JdbcTool.getConnection();
		
		Statement statement = cnn.createStatement();
		
		String sql = "select * from t_worker where id = 2";
		
		ResultSet rs  = statement.executeQuery(sql);
		Worker worker = null;
		while (rs.next()) {
			worker = new Worker();
			worker.setId(rs.getInt("id"));
			worker.setAge(rs.getInt("age"));
			worker.setName(rs.getString("name"));
			worker.setPosition(rs.getString("position"));
			
		}
		System.out.println(worker.toString());
		
		JdbcTool.rease(rs, cnn, statement);
		
	}
	

	@Test//转换成yyyy-MM-dd HH:mm:ss的时间类型   @@@@@@@@@@@@@@  失败###############
	public  void dateTimeString2Date() throws Exception {
        try {
        	String date_str = "2012-04-08 09:00:00";
            Calendar cal = Calendar.getInstance();//日期类
            System.out.println(cal.getTimeInMillis());
            System.out.println(cal.getTime());
//            java.sql.Timestamp timestampnow = new java.sql.Timestamp(cal.getTimeInMillis());//转换成正常的日期格式
            java.sql.Timestamp timestampnow = null;//转换成正常的日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//            ParsePosition pos = new ParsePosition(0);
            java.util.Date current = formatter.parse(date_str);
            System.out.println(current.getTime());
            timestampnow = new java.sql.Timestamp(current.getTime());
            String insertsql = "INSERT INTO t_worker (time) VALUES ("+new java.sql.Timestamp(current.getTime())+")";
            System.out.println(timestampnow);
            JdbcTool.update(insertsql);
        }
        catch (NullPointerException e) {
        	e.printStackTrace();
        }
    }
	
	
	@Test
	public void test_Connection() throws Exception{
		
		timy.test.util.jdbcConnection connection = new timy.test.util.jdbcConnection();
		System.out.println(connection.JdbcConnect());
		System.out.println("简单的数据库链接方法="+connection.simpleConnection());
		
		jdbcStratment jdbcStratment = new jdbcStratment();
		jdbcStratment.sqlSteatment();
		
//		String sql = "INSERT INTO t_worker (time) VALUES str_to_date('2012-04-08 09:00:00','%Y-%m-%d %T')";
//		
//		JdbcTool.update(sql);
		
		
		
	}

}
