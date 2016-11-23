package timy.test.projo;

import java.util.Date;

public class Worker {
	private long id;
	private String 	name ;
	private int age;
	private String position;
	private Date time;
	
	private static String tableName;
	public static String getTabel() {
			if(null  == tableName) {
				tableName = "t_" + Worker.class.getSimpleName().toLowerCase();
			}
			return tableName;
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", age=" + age
				+ ", position=" + position + ",time="+time+"]";
	}
	
	

}
