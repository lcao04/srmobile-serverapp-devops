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
 * Created by zacpuste on 9/1/16.
 */
@Path(ApplicationConstants.Requests.Checkout.UserOrder)
public class PreviousOrders extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{userId}/store/{storeId}")
    public Response getInfoResponse(@PathParam("userId") String userId, @PathParam("storeId") String storeId,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(userId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader()));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String userId, String storeId, String authToken) throws Exception, IOException {
        prepareResponse(userId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public PreviousOrders(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String userId, String storeId, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Checkout.UserOrder
                + ApplicationConstants.StringConstants.backSlash + userId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId;
    }
}

