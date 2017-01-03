package ru.ittim.oh2.megad.core;

import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Timofey on 23.10.2016 20:24 20:25 20:25 20:25.
 */
class MegaMediator implements HttpRequestHandler {
    //    private final static Logger logger = LogManager.getLogger();
    private static final String SUPPOTED_METHDOD = "GET";
    private static final Pattern channelPattern = Pattern.compile("pt=(\\d+)");
    private static final Pattern positionPattern = Pattern.compile("m=(\\d)");

    private final String host;
    private final List<MegaListener> listeners = new ArrayList<>();


    MegaMediator(String host, int listenPort) {
        this.host = host;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context)
            throws HttpException, IOException {
        String method = request.getRequestLine().getMethod();
        if (!SUPPOTED_METHDOD.equals(method)) {
            response.setStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
            // TODO: 03.01.2017
            return;
        }
        InetAddress remoteAddress = ((HttpInetConnection) context.getAttribute(HttpCoreContext.HTTP_CONNECTION))
                .getRemoteAddress();
        if (remoteAddress != null && !remoteAddress.toString().contains(host)) {
            response.setStatusCode(HttpStatus.SC_FORBIDDEN);
            // TODO: 03.01.2017
            return;
        }
        String uri = request.getRequestLine().getUri();
        Matcher channelMatcher = channelPattern.matcher(uri);
        Matcher posMatcher = positionPattern.matcher(uri);

        Integer channel = null;
        Integer pos = 0;
        if (channelMatcher.find()) {
            String group = channelMatcher.group(1);
            channel = Integer.valueOf(group);
        }
        if (channel == null || channel < 0 || channel > 13) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            // TODO: 03.01.2017
            return;
        }
        if (posMatcher.find()) {
            String group = posMatcher.group(1);
            pos = Integer.valueOf(group);
        }
        PortSwitchStatus status = PortSwitchStatus.getStatus(pos);

        int finalChannel = channel;
        listeners.forEach(l -> l.change(finalChannel, status));
    }

    void addListener(MegaListener listener) {
        listeners.add(listener);
    }

    void removeListener(MegaListener listener) {
        listeners.remove(listener);
    }

    void removeAllListeners() {
        listeners.clear();
    }
}
