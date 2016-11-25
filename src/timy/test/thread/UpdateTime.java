package timy.test.thread;

import java.util.List;

import timy.test.service.ResultService;

public class UpdateTime extends Thread {
	
	
	private List<Double> list ;
	
	
	
	public UpdateTime(List<Double> list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		try {
			ResultService.updateMinute(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
