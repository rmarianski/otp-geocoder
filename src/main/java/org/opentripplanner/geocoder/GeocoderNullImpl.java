package org.opentripplanner.geocoder;

public class GeocoderNullImpl implements Geocoder {
    
    static final String ERROR_MSG = "no geocoder configured";
    
    @Override
    public GeocoderResult geocode(String address) {
        return new GeocoderResult(ERROR_MSG);
    }
}
