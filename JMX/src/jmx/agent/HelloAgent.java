package jmx.agent;


import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import jmx.mbean.normal.Hello;


public class HelloAgent {
	
	/**
	 * <p>Title: main </p>
	 * <p>Description: 标准MBean</p>
	 * @param @param args 
	 * @return void
	 * @throws 
	 */
	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		MBeanServer server = MBeanServerFactory.createMBeanServer();
//		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName helloName = new ObjectName("huangdie:name=HelloWorld");
		server.registerMBean(new Hello(), helloName);
		try {
			JMXServiceURL httpURL = new JMXServiceURL("jmxmp", "127.0.0.1", 6868);
			JMXConnectorServer httpCS = JMXConnectorServerFactory.newJMXConnectorServer(httpURL, null, server);
			System.out.println("\nCreate a wrapped jdmk-http connector server at: "	+ httpURL);
			ObjectName httpON = new ObjectName("legacyWrapper:protocol=jmxmp,port=6868");
			server.registerMBean(httpCS, httpON);
			System.out.println("The wrapped server has been registered with name "+ httpON);
			// Start the jdmk-http connector server
			System.out.println("\nStart the wrapped jdmk-http connector server");
			httpCS.start();
			System.out.println("\nWaiting for incoming connections...");
		} catch (Exception e) {
			System.out.println("Cannot create the HTML protocol adaptor!");
			e.printStackTrace();
			return;
		}
//		ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//		server.registerMBean(adapter, adapterName);
//		adapter.start();
		System.out.println("start ... ");
	}

	/**
	 * <p>Title: main </p>
	 * <p>Description: 通知MBean</p>
	 * @param @param args 
	 * @return void
	 * @throws 
	 */
//	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
//		MBeanServer server = MBeanServerFactory.createMBeanServer();
//		ObjectName helloName = new ObjectName("huangdie:name=HelloWorld");
//		Hello hello = new Hello();
//		server.registerMBean(hello, helloName);
//		ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//		server.registerMBean(adapter, adapterName);
//		Person person = new Person();
//		server.registerMBean(person, new ObjectName("HelloAgent:name=ygyic"));
//		person.addNotificationListener(new HelloListener(), null, hello);
//		adapter.start();
//		System.out.println("start ... ");
//	}
	
	/**
	 * <p>Title: main </p>
	 * <p>Description: 标准MBean</p>
	 * @param @param args 
	 * @return void
	 * @throws 
	 */
//	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
//		MBeanServer server = MBeanServerFactory.createMBeanServer();
////		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//		ObjectName helloName = new ObjectName("huangdie:name=HelloWorld");
//		server.registerMBean(new Hello(), helloName);
//		ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//		server.registerMBean(adapter, adapterName);
//		adapter.start();
//		System.out.println("start ... ");
//	}

}
