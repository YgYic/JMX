package jmx.mbean.normal;


public class Hello implements HelloMBean {
	private String name="Hello";
	
	public void printHello(){
		System.out.println("Hello World, I'am " + name);
	}
	
	public void printHello(String whoName){
		System.out.println("Hello, " + whoName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
