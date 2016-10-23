import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Timofey on 18.10.2016.
 */
public class MegaD {
    private static Logger logger = LogManager.getLogger();
    private static String SCHEME = "http";
    private static String CHANNEL = "pt";
    private static String CMD = "cmd";
    private static String ON = ":1";
    private static String OFF = ":0";
    private static String GET = "get";

    private final String host;
    private final String password;
    private final int listenPort;
    private final CloseableHttpClient client;
    private final ExecutorService singleExecutor = Executors.newSingleThreadExecutor();


    public MegaD(String host, String password, int listenPort, int serverSoTimeout) {
        this.host = host;
        this.password = password;
        this.listenPort = listenPort;
        client = HttpClients.createDefault();

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(serverSoTimeout)
                .setTcpNoDelay(true)
                .build();

        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(listenPort)
                .setServerInfo("Test/1.1")
                .setSocketConfig(socketConfig)
                .setExceptionLogger(new HttpFileServer.StdErrorExceptionLogger())
                .registerHandler("*",new MegaListener(host, listenPort))
                .create();

        singleExecutor.submit(() -> {
            try {
                server.start();
                server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (IOException | InterruptedException e) {
                //// TODO: 23.10.2016 Обработку вовне
                logger.error("Http server corrupted.", e);
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.shutdown(5, TimeUnit.SECONDS);
            }
        });
    }

    public PortSwitchStatus getSwitchStatus(int channel) {
        PortSwitchStatus status = null;
        try {
            String body = getBody(GET, channel);
            if (body != null) {
                status =  PortSwitchStatus.valueOf(body);
            }
        } catch (URISyntaxException e) {
            logger.error("Error while form URI", e);
        } catch (IOException e) {
            logger.error("Can't execute get to MegaD " + host, e);
        }
        if (status != null) {
            logger.info(getInfo(channel, status.toString()));
        }
        return status;
    }

    public void turnOn(int channel) {
        turnX(channel, ON);
    }

    public void turnOff(int channel) {
        turnX(channel, OFF);
    }

    public void switchState(int channel) {
        switch (getSwitchStatus(channel)) {
            case ON:
                turnOff(channel);
                break;
            case OFF:
                turnOn(channel);
                break;
            default:
                logger.error("Can't switch port in UNKNOWN state");
        }
    }

    private void turnX(int channel, String cmd) {
        try {
            getBody(channel + cmd, channel);
        } catch (URISyntaxException e) {
            logger.error("Error while form URI", e);
        } catch (IOException e) {
            logger.error("Can't execute get to MegaD " + host, e);
        }

    }

    private String getBody(String command, int channel) throws URISyntaxException, IOException {
        CloseableHttpResponse response = null;
        try {
            HttpGet get = formGet(command, channel);
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("Can't close HTTP response", e);
                }
            }
        }
        return null;
    }

    private HttpGet formGet(String command, int channel) throws URISyntaxException {
        URI uri = new MegaDURIBuilder()
                .setScheme(SCHEME)
                .setHost(host)
                .setPath("/" + password)
                .setParameter(CHANNEL, String.valueOf(channel))
                .setParameter(CMD, command)
                .build();
        return new HttpGet(uri);
    }

    private String getInfo(int channel, String message) {
        return "Device: " + this.toString() + "; Channel: " + String.valueOf(channel) +
                "; Message: " + message;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        MegaD megaD = new MegaD("192.168.1.51", "tac", 80, 15000);

        int channel = 9;
        int timeout = 1;

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


    @Override
    public String toString() {
        return "MegaD{" +
                "host='" + host + '\'' +
                ", listenPort=" + listenPort +
                '}';
    }
}
