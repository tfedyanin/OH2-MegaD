import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Timofey on 23.10.2016.
 */
public class MegaListener implements HttpRequestHandler {
    private final static Logger logger = LogManager.getLogger();
    private static final String SUPPOTED_METHDOD = "GET";
    private static final Pattern channelPattern = Pattern.compile("pt=(\\d+)");
    private static final Pattern positionPattern = Pattern.compile("m=(\\d)");

    private final String host;
    private final int port;


    public MegaListener(String host, int listenPort) {
        this.host = host;
        this.port = listenPort;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context)
            throws HttpException, IOException {
        String method = request.getRequestLine().getMethod();
        if (!SUPPOTED_METHDOD.equals(method)) {
            response.setStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
            logger.error("Supported only GET method from MegaD328");
            return;
        }
        InetAddress remoteAddress = ((HttpInetConnection) context.getAttribute(HttpCoreContext.HTTP_CONNECTION))
                .getRemoteAddress();
        if (remoteAddress != null && !remoteAddress.toString().contains(host)) {
            response.setStatusCode(HttpStatus.SC_FORBIDDEN);
            logger.error("Serve requests from " + host + " on port " + port + ", but request came from " + remoteAddress);
            return;
        }
        String uri = request.getRequestLine().getUri().toString();
        Matcher channelMatcher = channelPattern.matcher(uri);
        Matcher posMatcher = positionPattern.matcher(uri);

        Integer channel = null;
        Integer pos = 0;
        if (channelMatcher.find()){
            String group = channelMatcher.group(1);
            channel = Integer.valueOf(group);
        }
        if (posMatcher.find()) {
            String group = posMatcher.group(1);
            pos = Integer.valueOf(group);
        }
    }
}
