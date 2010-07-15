package org.opentripplanner.geocoder;

public class GeocoderStubImpl implements Geocoder {
    
    private double lat;
    private double lng;
    private String description;
    
    public GeocoderStubImpl() {
        this(40.719991, -73.99953, "148 Lafayette St,New York,NY,10013");
    }

    public GeocoderStubImpl(double lat, double lng, String description) {
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    @Override
    public GeocoderResult geocode(String address) {
        return new GeocoderResult(lat, lng, description);
    }

    
    public double getLat() {
        return lat;
    }

    
    public void setLat(double lat) {
        this.lat = lat;
    }

    
    public double getLng() {
        return lng;
    }

    
    public void setLng(double lng) {
        this.lng = lng;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

}
