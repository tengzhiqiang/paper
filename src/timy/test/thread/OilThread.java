package timy.test.thread;

import timy.test.controller.OilController;

public class OilThread extends Thread {
	
	@Override
	public void run() {
		OilController oil = new OilController();
//		oil.dele_unusedata();
		try {
			oil.dealList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}