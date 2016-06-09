package cn.shandian.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext act = actionInvocation.getInvocationContext();
		Map<String, Object> session = act.getSession();
		if (session.get("username") != null) {
			String result = actionInvocation.invoke();
			return result;
		} else {
			return Action.INPUT;
		}
	}

}
