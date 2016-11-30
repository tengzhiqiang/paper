package timy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import timy.test.projo.Cement;
import timy.test.projo.Coal;
import timy.test.projo.Oil;
import timy.test.service.CoalService;
import timy.test.util.Thread.ResultSave_Thread;

public class TestMode {
	
	public static String path = "D:/web/safe.i189.com/policy/receive/";
	public static String receive_txt = "D:/web/safe.i189.com/policy/receive_txt/";
	@Test
	public void addzipfile() {
		try {
			File f = new File(path);
			File[] zips = f.listFiles();
			
			for (int i = 0; i < zips.length; i++) {
				if (zips[i].getName().contains(".txt")) {
					BufferedReader br = new BufferedReader(new FileReader(zips[i]));
					String lines = br.readLine();
					while (lines!=null) {
						String[] str = lines.split("\t");
						File txts = new File(receive_txt);
						File[] array = txts.listFiles();
						if (str.length>4) {
							for (int j = 0; j < txts.length(); j++) {
//							System.out.println(Integer.parseInt(str[4]));
								int num = Integer.parseInt(str[4])/10000;
								String zipname = zips[i].getName().replace(".zip", "");
								if (num>10) {
									zipname = "_0"+num+".txt";
								}else {
									zipname = "_00"+num+".txt";
								}
//								if (zips[i].getName().replace(".zip", ".txt")) {
									
//								}
							}
						}
					}
					
				}
			}
			
			
			
			
			
			
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
