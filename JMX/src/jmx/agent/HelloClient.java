package jmx.agent;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class HelloClient {
	public static String getStatus() {
		try {
			JMXConnector jmxc = getConnection();
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			System.out.println(mbsc);
			ObjectName mbeanName = new ObjectName("huangdie:name=HelloWorld");

			String status = (String) mbsc.getAttribute(mbeanName, "Name");
			mbsc.invoke(mbeanName, "printHello", new Object[]{"Hello this is a test!"}, new String[] { String.class.getName() });
			jmxc.close();
			return status;
		} catch (Throwable e) {
			e.printStackTrace();
			return "";
		}
	}

	private static JMXConnector getConnection() throws IOException {
		JMXServiceURL url = new JMXServiceURL("jmxmp", "192.168.1.104", 6868);

		JMXConnector jmxc = JMXConnectorFactory.connect(url);
		System.out.println("connect success.");
		return jmxc;
	}
	public static void main(String[] args){
		System.out.println(HelloClient.getStatus());
	}
}
