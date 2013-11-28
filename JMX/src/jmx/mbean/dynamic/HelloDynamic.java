package jmx.mbean.dynamic;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;

public class HelloDynamic implements DynamicMBean {
	private String name;
	private MBeanInfo mBeanInfo = null;
	private String className;
	private String description;
	private MBeanAttributeInfo[] attributes;
	private MBeanConstructorInfo[] constructors;
	private MBeanOperationInfo[] operations;
	MBeanNotificationInfo[] notifications;
	
	public HelloDynamic(){
		init();
		buildDynamicMBean();
	}
	
	private void init() {
		className = this.getClass().getName();
		description = "Simple implementatin of a dynamic mbean!";
		attributes = new MBeanAttributeInfo[1];
		constructors = new MBeanConstructorInfo[1];
		operations = new MBeanOperationInfo[1];
		notifications = new MBeanNotificationInfo[0];
	}

	private void buildDynamicMBean() {
		Constructor[] thisConstructors = this.getClass().getConstructors();
		constructors[0] = new MBeanConstructorInfo("HelloDynamic(): Construct a HelloDynamic Object",
				thisConstructors[0]);
		attributes[0] = new MBeanAttributeInfo("Name", "java.lang.String", "Name: name String", true, true, false);
		MBeanParameterInfo[] params = null;
		operations[0] = new MBeanOperationInfo("print", "print: print the name!", params, 
				"void", MBeanOperationInfo.INFO);
		mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations, notifications);
	}
	
	private void dynamicAddOperation(){
		init();
		operations = new MBeanOperationInfo[2];
		buildDynamicMBean();
		operations[1] = new MBeanOperationInfo("print1", "print1:print the name!", null, 
				"void", MBeanOperationInfo.INFO);
		mBeanInfo = new MBeanInfo(className, description, attributes, constructors, operations, notifications);
	}
	
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException,
			ReflectionException {
		if (attribute.equals("Name")) {
			return name;
		}
		return null;
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException {
		if (null == attribute) {
			return ;
		}
		String Name = attribute.getName();
		Object value = attribute.getValue();
		try {
			if (Name.equals("Name")) {
				if (value == null) {
					name = null;
				} else if((Class.forName("java.lang.String").isAssignableFrom(value.getClass()))){
					name = (String)value;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		if (attributes == null) {
			return null;
		}
		AttributeList resultList = new AttributeList();
		if (attributes.length == 0) {
			return resultList;
		}
		for (int i = 0; i < attributes.length; i++) {
			try {
				Object value = getAttribute(attributes[i]);
				resultList.add(new Attribute(attributes[i], value));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return resultList;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		if (attributes == null) {
			return null;
		}
		AttributeList resultList = new AttributeList();
		if (attributes.isEmpty()) {
			return resultList;
		}
		for (Iterator i = attributes.iterator();i.hasNext();) {
			Attribute attr = (Attribute)i.next();
			try {
				setAttribute(attr);
				String name = attr.getName();
				Object value = getAttribute(name);
				resultList.add(new Attribute(name, value));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return resultList;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if (actionName.equals("print")) {
			System.out.println("Hello, " + name + ", this is HelloDynamic!");
			dynamicAddOperation();
			return null;
		}else if(actionName.equals("print1")){
			System.out.println("Hello, " + name + ", this is HelloDynamic1!");
			return null;
		}else{
			throw new ReflectionException(new NoSuchMethodException(actionName), "Connot find the operation "
					+ actionName + " in " + className);
		}
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return mBeanInfo;
	}

	public String getName(){
		return "HellOoooO!";
	}
}
