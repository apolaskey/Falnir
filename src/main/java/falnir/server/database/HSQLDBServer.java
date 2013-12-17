package falnir.server.database;

import org.hsqldb.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by apolaskey on 12/15/13.
 */
public class HSQLDBServer {
    private static final Logger logger = LoggerFactory.getLogger(HSQLDBServer.class);
    private Server hsqlServer = null;

    public final String DB_NAME = "falnir";
    public final String DB_PATH = "file:falnirdb";

    public HSQLDBServer() {
        try
        {
            hsqlServer = new Server();
            hsqlServer.setLogWriter(null);
            hsqlServer.setSilent(true);

            hsqlServer.setDatabaseName(0, DB_NAME);
            hsqlServer.setDatabasePath(0, DB_PATH);

            hsqlServer.start();
        }
        catch(Exception e)
        {
            logger.error("Failed to startup HSQLDB!", e);
        }
    }

    /**
     * Start the HSQLDB; this should be ran on a different thread.
     */
    public void Start() {
        logger.info("Starting HSQL in memory DB...");
        hsqlServer.start();
    }
}
