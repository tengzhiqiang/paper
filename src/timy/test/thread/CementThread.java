package timy.test.thread;

import timy.test.controller.CementController;

public class CementThread implements Runnable{

	
	public void run() {
		
		CementController cementController = new CementController();
		
		try {
//			cementController.dele_unusedata();
//			cementController.dealList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
