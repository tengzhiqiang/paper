package timy.test.util;


public class StrToDate {
	/**
	 * 字符串转化成日期
	 * @param opetime  操作时间
	 * @param time 进出港时间
	 * @return
	 * @throws Exception
	 */
	public static String strToDate(String opetime, String time) throws Exception{
		String str  = null;
		
		opetime = opetime.substring(0,4)+"-"+opetime.substring(4, 6)+"-"+opetime.substring(6,8);
		time = time.substring(9, 18).replace(".", ":");
		str = opetime+time;
//		System.out.println(str);
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		java.util.Date date = dateFormat.parse(str);
		
		return str;	
		

		
	}

}
