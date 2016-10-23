import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Timofey on 18.10.2016.
 */
public class MegaDURIBuilder extends URIBuilder {
    @Override
    public URI build() throws URISyntaxException {
        URI build = super.build();
        return new URI(build.toString().replace("%3A", ":"));
    }
}
