package jmx.mbean.notification;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Person extends NotificationBroadcasterSupport implements
		PersonMBean {
	private int seq = 0;
	
	@Override
	public void hi() {
		Notification notification = new Notification("ygyic.hi", this, ++seq, System.currentTimeMillis(), "YgYic");
		sendNotification(notification);
	}

}
