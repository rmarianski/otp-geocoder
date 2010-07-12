package org.opentripplanner.geocoder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GeocoderResult {
    
    private double lat;
    private double lng;
    private String description;
    private String error;

    @XmlElement(required=false)
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
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

    public GeocoderResult() {}

    public GeocoderResult(double lat, double lng, String description) {
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    public GeocoderResult(String error) {
        this.error = error;
    }
}
