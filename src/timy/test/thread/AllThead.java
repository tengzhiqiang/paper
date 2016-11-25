package timy.test.thread;


public class AllThead {
	
	public static void main(String[] args) {
		boolean flag = false;
		
		while (!flag) {
			flag = true;
			System.out.println("启动 t_grain 线程");
			CoalThread oil = new CoalThread("t_grain");//3
			new Thread(oil).start();
			
			
			System.out.println("启动t_iron线程");
			CoalThread coal = new CoalThread("t_iron");//9
			new Thread(coal).start();
			
		}
	}

}
