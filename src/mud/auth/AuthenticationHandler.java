package mud.auth;

import static org.apache.mina.statemachine.event.IoFilterEvents.*;

import mud.auth.command.Command;
import mud.auth.command.PasswordCommand;
import mud.auth.command.QuitCommand;
import mud.auth.command.UserCommand;
import mud.server.core.ConnectionStrings;

import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.example.tapedeck.CommandSyntaxException;
import org.apache.mina.statemachine.StateControl;
import org.apache.mina.statemachine.annotation.IoFilterTransition;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.context.AbstractStateContext;
import org.apache.mina.statemachine.event.Event;
import org.apache.mina.statemachine.event.IoFilterEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthenticationHandler {
	public static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);
	@State public static final String ROOT  = "Root";
	@State(ROOT) public static final String START = "Start";
	@State(ROOT) public static final String WAIT_USER = "WaitUser";
	@State(ROOT) public static final String WAIT_PASSWORD = "WaitPassword";
	@State(ROOT) public static final String DONE = "Done";
	@State(ROOT) public static final String FAILED = "Failed";
	private final int MAX_ATTEMPTS = 5;
	
	// Encapsulation of login data/attempts
	public static class AuthenticationContext extends AbstractStateContext {
		public String username;
		public String password;
		public int tries = 0;
	}
	
	@IoFilterTransition(on = SESSION_OPENED, in = START, next = WAIT_USER)
	public void sendAuthRequest(NextFilter nextFilter, IoSession session) {
		session.write(ConnectionStrings.AUTH_USER_NAME);
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_USER, next = WAIT_PASSWORD)
	public void user(AuthenticationContext context, NextFilter nextFilter, 
			         IoSession session, UserCommand cmd) {
		context.username = cmd.getUsername();
		session.write(ConnectionStrings.AUTH_USER_PASS);
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_PASSWORD, next = DONE)
	public void password(AuthenticationContext context, NextFilter nextFilter,
						 IoSession session, PasswordCommand cmd) {
		context.password = cmd.getPassword();
		if(context.password.equals( new StringBuffer(context.username).reverse().toString() )) {
			session.write("Authenticated Succesfully.");
		}
		else {
			context.tries++;
			
			if(context.tries < MAX_ATTEMPTS) {
				session.write("-- Authentication Failed, Try again.");
				StateControl.breakAndGotoNext(START);
			}
			else {
				session.write("-- Authentication failed too many times. Please conact an admin.").addListener(IoFutureListener.CLOSE);
				StateControl.breakAndGotoNext(FAILED);
			}
		}
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_USER, weight = 10)
	public void errorOnUsername(IoSession session, Command cmd) {
		if (cmd instanceof QuitCommand ) {
			StateControl.breakAndContinue();
		}
		session.write("Unexpected command '" + cmd.getName() + "'. Expected 'user <username>'.");
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_PASSWORD, weight = 10)
	public void errorOnPassword(IoSession session, Command cmd) {
		if (cmd instanceof QuitCommand ) {
			StateControl.breakAndContinue();
		}
		session.write("Unexpected command '" + cmd.getName() + "'. Expected 'password <password>'.");
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = ROOT)
	public void quit(IoSession session, QuitCommand cmd) {
		session.write("Bye.").addListener(IoFutureListener.CLOSE);
	}
	
	@IoFilterTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 10)
	public void commandSyntaxError(IoSession session, CommandSyntaxException cse) {
		session.write("-- " + cse.getMessage());
	}
	
	@IoFilterTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 10)
	public void exceptionCaught(IoSession session, Exception e) {
		e.printStackTrace();
		session.close(true);
	}
	
	@IoFilterTransition(on = SESSION_CLOSED, in = DONE)
	public void sessionClosed(NextFilter nextFilter, IoSession session) {
		nextFilter.sessionClosed(session);
	}
	
	@IoFilterTransition(on = EXCEPTION_CAUGHT, in = DONE)
	public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) {
		nextFilter.exceptionCaught(session, cause);
	}
	
	@IoFilterTransition(on = MESSAGE_RECEIVED, in = DONE)
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) {
		nextFilter.messageReceived(session, message);
	}

	@IoFilterTransition(on = MESSAGE_SENT, in = {DONE, WAIT_USER, WAIT_PASSWORD})
	public void messageSent(AuthenticationContext context, NextFilter nextFilter, IoSession session, WriteRequest writeRequest) {
		nextFilter.messageSent(session, writeRequest);
	}
	
	@IoFilterTransition(on = CLOSE, in = DONE)
	public void filterClose(NextFilter nextFilter, IoSession session) {
		nextFilter.filterClose(session);
	}

	@IoFilterTransition(on = IoFilterEvents.ANY, in = ROOT, weight = 1000)
	public void unhandledEvent(Event event) {
		logger.warn("Unhandled event: " + event.toString() + "\n\n" + event.getContext().toString());
	}
	
	@IoFilterTransition(on = WRITE, in = {DONE, WAIT_USER, WAIT_PASSWORD})
	public void filterWrite(AuthenticationContext context, NextFilter nextFilter, IoSession session, WriteRequest writeRequest) {
		nextFilter.filterWrite(session, writeRequest);
	}
	
	@IoFilterTransition(on = SESSION_CREATED, in = ROOT)
	public void sessionCreated(NextFilter nextFilter, IoSession session) {
		nextFilter.sessionCreated(session);
	}
	
	@IoFilterTransition(on = SESSION_IDLE, in = DONE)
	public void sessionIdle(AuthenticationContext context, NextFilter nextFilter, IoSession session, IdleStatus idleStatus) {
		// Start connection handler
		session.setAttribute("user", context.username);
		nextFilter.sessionOpened(session);
	}
}
