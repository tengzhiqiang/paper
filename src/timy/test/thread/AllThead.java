package timy.test.thread;


public class AllThead {
	
	public static void main(String[] args) {
		boolean flag = false;
		
		while (!flag) {
			flag = true;
			System.out.println("启动 t_building 线程");
			CoalThread oil = new CoalThread("t_building");
			new Thread(oil).start();
//			System.out.println("启动t_mechanic线程");
//			CoalThread coal = new CoalThread("t_cement");
//			new Thread(coal).start();
			
		}
	}

}
