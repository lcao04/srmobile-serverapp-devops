package com.wakefern.Cart;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by zacpuste on 8/25/16.
 */
@Path(ApplicationConstants.Requests.Cart.CartUser)
public class ItemDelete extends BaseService {
    @DELETE
    @Produces("application/*")
    @Path("/{userId}/store/{storeId}/item/{itemId}")
    public Response getInfoResponse(@PathParam("userId") String userId, @PathParam("storeId") String storeId, @PathParam("itemId") String itemId,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(userId, storeId, itemId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try{
            HTTPRequest.executeDelete(secondMapping.getPath(), secondMapping.getgenericHeader());
            return this.createValidDelete();
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String userId, String storeId, String itemId, String authToken) throws Exception, IOException {
        prepareResponse(userId, storeId, itemId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeDelete(secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public ItemDelete(){
        this.serviceType = new MWGHeader();
    }

    public void prepareResponse(String userId, String storeId, String itemId, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Cart.CartUser
                + ApplicationConstants.StringConstants.backSlash + userId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.item
                + ApplicationConstants.StringConstants.backSlash + itemId;
    }
}
