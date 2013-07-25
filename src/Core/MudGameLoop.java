package Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for doing world idle updates; should switch to a timer instead of sleeping.
 * That way I can implement some sort of load handling to do more updates if it took too long.
 * @author Andrew
 *
 */
public class MudGameLoop implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MudProgramEntry.class);
	Thread threadRunner;
	
	public MudGameLoop()
	{
		threadRunner = new Thread(this, "MudGameLoop");
		logger.info("Beginning Game Loop.");
		threadRunner.start();
	}
	
	private void mainLoop()
	{
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true)
		{
			try {
				logger.info("Game tick started!");
				mainLoop();
				logger.info("Game tick completed!");
				threadRunner.sleep(1600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
