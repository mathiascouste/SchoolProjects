package model;

import model.enums.Action;
import model.user.PrivateUser;

public class Notification {
	private PrivateUser privateUser;
	private Action actionEnum;
	private String notification;

	public Notification(PrivateUser privateUser, Action actionEnum) {
		this.privateUser = privateUser;
		this.actionEnum = actionEnum;
		this.notification = this.privateUser.getThisPublicUser().getName() + " " + this.actionEnum;
	}

	public PrivateUser getPrivateUser() {
		return privateUser;
	}

	public void setPrivateUser(PrivateUser privateUser) {
		this.privateUser = privateUser;
	}

	public Action getActionEnum() {
		return actionEnum;
	}

	public void setActionEnum(Action actionEnum) {
		this.actionEnum = actionEnum;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
}
