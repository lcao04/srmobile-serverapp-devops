package com.wakefern.Checkout;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by zacpuste on 10/4/16.
 */
@Path(ApplicationConstants.Requests.Checkout.UserOrder)
public class CreateOrChangeOrder extends BaseService {
    /**
     *
     * @param userId
     * @param storeId
     * @param authToken NOTE This auth is the regular user auth
     * @param jsonBody
     * @return
     * @throws Exception
     * @throws IOException
     */
    @POST
    @Consumes("application/json")
    @Produces("application/*")
    @Path("/{userId}/store/{storeId}")
    public String getInfo(@PathParam("userId") String userId, @PathParam("storeId") String storeId,
                          @HeaderParam("Authorization") String authToken, String jsonBody) throws Exception, IOException {

        this.token = authToken;
        this.path = ApplicationConstants.Requests.Checkout.UserOrder
                + ApplicationConstants.StringConstants.backSlash + userId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId;

        ServiceMappings mapping = new ServiceMappings();
        mapping.setPutMapping(this, jsonBody);

        return (HTTPRequest.executePostJSON(mapping.getPath(), mapping.getGenericBody(), mapping.getgenericHeader()));
    }

    public CreateOrChangeOrder(){
        this.serviceType = new MWGHeader();
    }

}
