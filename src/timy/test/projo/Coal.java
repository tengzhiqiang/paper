package timy.test.projo;



public class Coal extends Cement{
	
	public String getTabel() {
			if(null  == tableName) {
				tableName = "t_" + Coal.class.getSimpleName().toLowerCase();
			}
			return tableName;
	}
	@Override
	public String toString() {
		return "Coal [id=" + id + ", vessel=" + vessel + ", flag=" + flag
				+ ", route=" + route + ", ton=" + ton + ", navigation="
				+ navigation + ", time=" + time + ", opetime=" + opetime
				+ ", port=" + port + ", batchport=" + batchport + ", cargo="
				+ cargo + ", agency=" + agency + ", cargotype=" + cargotype
				+ ", type=" + type + ", dealtime=" + dealtime + ", date="
				+ date + ", remark=" + remark + ", tableName=" + tableName
				+ "]";
	}
	
}
