package org.opentripplanner.geocoder.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opentripplanner.geocoder.Geocoder;
import org.opentripplanner.geocoder.GeocoderResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.jersey.api.spring.Autowire;

@Path("/geocode")
@Autowire
public class GeocoderServer {
    
    private Geocoder geocoder;
    
    @Autowired
    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GeocoderResult geocode(@QueryParam("address") String address) {
        if (address == null) {
            throw new WebApplicationException(Response.status(400)
                    .entity("no address")
                    .type("text/plain")
                    .build());
        }
        GeocoderResult result = null;
        result = geocoder.geocode(address);
        return result;
    }
}
