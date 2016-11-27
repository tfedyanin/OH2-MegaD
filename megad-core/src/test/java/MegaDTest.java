import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Timofey on 27.11.2016.
 */
public class MegaDTest {

    @Test
    public void manualTest1() throws InterruptedException, IOException, URISyntaxException {
        MegaD megaD = new MegaD("192.168.1.51", "tac", 80, 15000);
        int channel = 9;
        int timeout = 1;
        megaD.getMediator().addListener(new SimpleLogMegaListener());
        megaD.getSwitchStatus(channel);
        megaD.turnOff(channel);
        TimeUnit.SECONDS.sleep(timeout);
        megaD.turnOn(channel);
        TimeUnit.SECONDS.sleep(timeout);
        megaD.switchState(channel);
        TimeUnit.SECONDS.sleep(timeout);
        megaD.turnOn(channel);
        TimeUnit.DAYS.sleep(1);
    }

}