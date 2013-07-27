package mud.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for doing world idle updates; should switch to a timer instead of sleeping.
 * That way I can implement some sort of load handling to do more updates if it took too long.
 * @author Andrew
 *
 */
public class MudGameDriver implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MudGameDriver.class);
	protected BaseMudGameServer server;
	private Thread threadRunner;
	private int gameTick = MudConfig.GAME_TICK;
	
	public MudGameDriver(BaseMudGameServer server)
	{
		this.server = server;
		threadRunner = new Thread(this, this.getClass().getName());
		
		
		logger.info("Beginning game loop at a {} tick rate.", gameTick);
		threadRunner.start();
	}
	
	protected void mainLoop()
	{
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true) // Spin it into an infinite loop
		{
			try {
				mainLoop();
				threadRunner.sleep(gameTick);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * EOF
	 */
}
