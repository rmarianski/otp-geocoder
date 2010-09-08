package org.opentripplanner.geocoder.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.opentripplanner.geocoder.Geocoder;
import org.opentripplanner.geocoder.GeocoderResult;
import org.opentripplanner.geocoder.GeocoderResults;

public class GoogleGeocoder implements Geocoder {
	
	private GoogleJsonDeserializer googleJsonDeserializer = new GoogleJsonDeserializer();

	@Override
	public GeocoderResults geocode(String address) {
		String content = null;
		
		try {
			// make json request
			URL googleGeocoderUrl = getGoogleGeocoderUrl(address);
            URLConnection conn = googleGeocoderUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuilder sb = new StringBuilder(128);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            content = sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return noGeocoderResult("Error parsing geocoder response");
		}
		
        GoogleGeocoderResults googleGeocoderResults = googleJsonDeserializer.parseResults(content);
        
        List<GoogleGeocoderResult> googleResults = googleGeocoderResults.getResults();
        List<GeocoderResult> geocoderResults = new ArrayList<GeocoderResult>();
        for (GoogleGeocoderResult googleGeocoderResult : googleResults) {
			Geometry geometry = googleGeocoderResult.getGeometry();
			Location location = geometry.getLocation();
			Double lat = location.getLat();
			Double lng = location.getLng();
			
			String formattedAddress = googleGeocoderResult.getFormatted_address();
			
			GeocoderResult geocoderResult = new GeocoderResult(lat, lng, formattedAddress);
			geocoderResults.add(geocoderResult);
		}

		return new GeocoderResults(geocoderResults);
	}

	private GeocoderResults noGeocoderResult(String error) {
		return new GeocoderResults(error);
	}

	private URL getGoogleGeocoderUrl(String address) throws IOException {
		UriBuilder uriBuilder = UriBuilder.fromUri("http://maps.google.com/maps/api/geocode/json");
		uriBuilder.queryParam("sensor", false);
		uriBuilder.queryParam("address", address);
		URI uri = uriBuilder.build();
		return new URL(uri.toString());
	}

}
