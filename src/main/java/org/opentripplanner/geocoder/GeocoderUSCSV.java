package org.opentripplanner.geocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.core.UriBuilder;

public class GeocoderUSCSV implements Geocoder {

    private String geocoderBaseUrl;

    public void setGeocoderBaseUrl(String geocoderBaseUrl) {
        this.geocoderBaseUrl = geocoderBaseUrl;
    }

    private URL getGeocoderURL(String geocoderBaseUrl, String address) throws MalformedURLException {
        UriBuilder builder = UriBuilder.fromUri(geocoderBaseUrl);
        builder.queryParam("address", address);
        URI uri = builder.build();
        return new URL(uri.toString());
    }

    /* (non-Javadoc)
     * @see org.opentripplanner.api.geocode.Geocoder#geocode(java.lang.String)
     */
    public GeocoderResult geocode(String address) {
        assert geocoderBaseUrl != null;
        
        String content = null;        
        
        try {
            URL url = getGeocoderURL(geocoderBaseUrl, address);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuilder sb = new StringBuilder(128);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            content = sb.toString();
        } catch (MalformedURLException e) {
            return noGeocoderResult("invalid geocoder");
        } catch (IOException e) {
            return noGeocoderResult("communication error");
        }
        
        try {
            String firstLine = content.split("\n")[0];
            String[] fields = firstLine.split(",", 3);
            double lat = Double.parseDouble(fields[0]);
            double lng = Double.parseDouble(fields[1]);
            String description = fields[2];
            return new GeocoderResult(lat, lng, description);
        } catch (NumberFormatException e) {
            return noGeocoderResult(content);
        } catch (ArrayIndexOutOfBoundsException e) {
            return noGeocoderResult(content);
        }
    }

    private GeocoderResult noGeocoderResult(String content) {
        // use the response as the error message returned back
        return new GeocoderResult(content);
    }
}
