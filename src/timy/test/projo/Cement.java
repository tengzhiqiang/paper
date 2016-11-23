package timy.test.projo;

import java.util.Date;


public class Cement {
	
	protected long id;
	protected String vessel ;
	protected String flag;
	protected String route;
	protected float ton;
	protected String navigation;
	protected String time;
	protected String opetime;
	protected String port;
	protected String batchport;
	protected float cargo;
	protected String agency;
	protected String cargotype;
	protected String type;
	protected String dealtime;
	protected Date date;
	protected String remark;
	
	
	protected  String tableName;
	public  String getTabel() {
			if(null  == tableName) {
				tableName = "t_" + Cement.class.getSimpleName().toLowerCase();
			}
			return tableName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVessel() {
		return vessel;
	}
	public void setVessel(String vessel) {
		this.vessel = vessel;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public float getTon() {
		return ton;
	}
	public void setTon(float ton) {
		this.ton = ton;
	}
	public String getNavigation() {
		return navigation;
	}
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpetime() {
		return opetime;
	}
	public void setOpetime(String opetime) {
		this.opetime = opetime;
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
	public float getCargo() {
		return cargo;
	}
	public void setCargo(float cargo) {
		this.cargo = cargo;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getCargotype() {
		return cargotype;
	}
	public void setCargotype(String cargotype) {
		this.cargotype = cargotype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDealtime() {
		return dealtime;
	}
	public void setDealtime(String dealtime) {
		this.dealtime = dealtime;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Cement [id=" + id + ", vessel=" + vessel + ", flag=" + flag
				+ ", route=" + route + ", ton=" + ton + ", navigation="
				+ navigation + ", time=" + time + ", opetime=" + opetime
				+ ", port=" + port + ", batchport=" + batchport + ", cargo="
				+ cargo + ", agency=" + agency + ", cargotype=" + cargotype
				+ ", type=" + type + ", dealtime=" + dealtime + ", date="
				+ date + "]";
	}
	
}
