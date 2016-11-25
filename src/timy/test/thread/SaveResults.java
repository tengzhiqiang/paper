package timy.test.thread;

import java.util.List;

import timy.test.projo.Cement;
import timy.test.service.ResultService;

public class SaveResults extends Thread {

	private List<Cement> inList;
	private List<Cement> outList;
	
	
	public SaveResults(List<Cement> inList, List<Cement> outList) {
		super();
		this.inList = inList;
		this.outList = outList;
	}


	@Override
	public void run() {
		ResultService.save_results(inList, outList,null);
	}
}
