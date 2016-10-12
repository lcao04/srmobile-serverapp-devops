package com.wakefern.Checkout;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by zacpuste on 8/24/16.
 */

@Path(ApplicationConstants.Requests.Checkout.Checkout)
public class StoreFulfillmentPickupDateTimes extends BaseService{
    @GET
    @Produces("application/*")
    @Path("/{storeId}/pickup/{year}/{month}/{day}/times")
    public Response getInfoResponse(@PathParam("storeId") String storeId, @PathParam("year") String year, @PathParam("month") String month, @PathParam("day") String day,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(storeId, year, month, day, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader()));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String storeId, String year, String month, String day, String authToken) throws Exception, IOException {
        prepareResponse(storeId, year, month, day, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public StoreFulfillmentPickupDateTimes(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String storeId, String year, String month, String day, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Checkout.Checkout + ApplicationConstants.StringConstants.backSlash
                + storeId + ApplicationConstants.StringConstants.pickup + ApplicationConstants.StringConstants.backSlash
                + year + ApplicationConstants.StringConstants.backSlash + month
                + ApplicationConstants.StringConstants.backSlash + day + ApplicationConstants.StringConstants.times;
    }
}
