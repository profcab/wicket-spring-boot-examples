package com.giffing.examples.wicket.spring.boot.cluster.wicket;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;

@WicketSignInPage
@MountPath("login")
public class LoginPage extends WebPage {

	public LoginPage(PageParameters parameters) {
		super(parameters);
		System.out.println( "call login" );
		if (((AbstractAuthenticatedWebSession) getSession()).isSignedIn()) {
			setResponsePage(new HomePage());
		}
		add(new LoginForm("loginForm"));
	}

	private class LoginForm extends Form<LoginForm> {

		private String username;
		
		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel<>(this));
			add(new FeedbackPanel("feedback"));
			add(new RequiredTextField<String>("username"));
			add(new PasswordTextField("password"));
		}

		@Override
		protected void onSubmit() {
			System.out.println( "login try" );
			AuthenticatedWebSession session = AuthenticatedWebSession.get();
			if (session.signIn(username, password)) {
				setResponsePage(new HomePage());
			} else {
				error("Login failed");
			}
		}
	}
}
