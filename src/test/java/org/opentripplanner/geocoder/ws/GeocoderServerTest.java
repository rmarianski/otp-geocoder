package org.opentripplanner.geocoder.ws;

import static org.junit.Assert.*;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;
import org.opentripplanner.geocoder.Geocoder;
import org.opentripplanner.geocoder.GeocoderResult;

public class GeocoderServerTest {

    private GeocoderServer geocoderServer;

    @Before
    public void setUp() {
        geocoderServer = new GeocoderServer();
    }
    
    @Test(expected = WebApplicationException.class)
    public void testGeocodeNoAddress() {
        geocoderServer.geocode(null);
        fail("Should have thrown an error");
    }
    
    @Test
    public void testGeocodeValidAddress() {
        final double lat = 78.121;
        final double lng = -43.237;
        final String description = "121 elm street";
        
        geocoderServer.setGeocoder(new Geocoder() {
            @Override
            public GeocoderResult geocode(String address) {
                return new GeocoderResult(lat, lng, description);
            }
        });
        
        GeocoderResult result = geocoderServer.geocode("121 elm street");
        assertEquals("description matches", description, result.getDescription());
        assertEquals(lat, result.getLat(), 0.001);
        assertEquals(lng, result.getLng(), 0.001);
    }
    
    @Test
    public void testGeocodeInvalidAddress() {
        final String error = "uh oh";
        geocoderServer.setGeocoder(new Geocoder() {
            @Override
            public GeocoderResult geocode(String address) {
                return new GeocoderResult(error);
            }
        });

        GeocoderResult result = geocoderServer.geocode("121 elm street");
        assertEquals("error returned", error, result.getError());
    }
}
