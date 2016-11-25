
package timy.test.projo;



public class Result {
	
	private int id;
	private float ton;
	private String outtime;
	private String intime;
	private String port;
	private String batchport;
	private float cargoout;
	private float cargoin;
	private String cargotype;
	private String remark;
	private long inid;
	private long outid;
	private double minutes;
	
	
	private static String tableName;
	public static String getTabel() {
			if(null  == tableName) {
				tableName = "t_" + Result.class.getSimpleName().toLowerCase();
			}
			return tableName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTon() {
		return ton;
	}
	public void setTon(float ton) {
		this.ton = ton;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getBatchport() {
		return batchport;
	}
	public void setBatchport(String batchport) {
		this.batchport = batchport;
	}
	public float getCargoout() {
		return cargoout;
	}
	public void setCargoout(float cargoout) {
		this.cargoout = cargoout;
	}
	public float getCargoin() {
		return cargoin;
	}
	public void setCargoin(float cargoin) {
		this.cargoin = cargoin;
	}
	public String getCargotype() {
		return cargotype;
	}
	public void setCargotype(String cargotype) {
		this.cargotype = cargotype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public long getInid() {
		return inid;
	}


	public void setInid(long l) {
		this.inid = l;
	}


	public long getOutid() {
		return outid;
	}

	public String getOuttime() {
	    return outtime;
	}


	public void setOuttime(String outtime) {
	    this.outtime = outtime;
	}


	public String getIntime() {
	    return intime;
	}


	public void setIntime(String intime) {
	    this.intime = intime;
	}


	public void setOutid(long outid) {
	    this.outid = outid;
	}


	public double getMinutes() {
	    return minutes;
	}


	public void setMinutes(double minutes) {
	    this.minutes = minutes;
	}


	@Override
	public String toString() {
		return "Result [id=" + id + ", ton=" + ton + ", outtime=" + outtime
				+ ", intime=" + intime + ", port=" + port + ", batchport="
				+ batchport + ", cargoout=" + cargoout + ", cargoin=" + cargoin
				+ ", cargotype=" + cargotype + ", remark=" + remark + ", inid="
				+ inid + ", outid=" + outid + ", minutes=" + minutes + "]";
	}


	

	
}
