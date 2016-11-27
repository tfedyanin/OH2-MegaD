import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Timofey on 27.11.2016.
 */
public class SimpleLogMegaListener implements MegaListener {
    private static Logger logger = LogManager.getLogger();
    @Override
    public void change(int channel, PortSwitchStatus status) {
        logger.info("State of channel "+channel + " changed. Current status: " + status);
    }
}
