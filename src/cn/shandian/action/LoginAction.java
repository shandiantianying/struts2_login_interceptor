package cn.shandian.action;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;

	public String execute() {
		ActionContext act = ActionContext.getContext();
		Map<String, Object> session = act.getSession();
		if ("admin".equals(username)) {
			session.put("username", username);
			return Action.SUCCESS;
		} else
			return Action.INPUT;
	}
}
