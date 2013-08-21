package mud.server.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for doing world idle updates; should switch to a timer instead of sleeping.
 * That way I can implement some sort of load handling to do more updates if it took too long.
 * @author Andrew
 *
 */
public class GameDriver implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(GameDriver.class);
	private static BaseMudGameServer server;
	private Thread threadRunner;
	private int gameTick = MudConfig.GAME_TICK;
	
	public static final BaseMudGameServer getServer() {
		return server;
	}
	
	
	public GameDriver(BaseMudGameServer server)
	{
		this.server = server;
		threadRunner = new Thread(this, this.getClass().getName());
		
		
		logger.info("Beginning game loop at a {} tick rate.", gameTick);
		threadRunner.start();
	}
	
	private void mainLoop()
	{
		// Any and all Updateable Game Logic
	}
	
	
	@Override
	public void run() {
		while(true) // Spin it into an infinite loop
		{
			try {
				mainLoop();
				Thread.sleep(gameTick);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * EOF
	 */
}
