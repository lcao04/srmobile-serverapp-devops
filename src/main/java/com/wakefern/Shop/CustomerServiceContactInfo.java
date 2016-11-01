package com.wakefern.Shop;

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
@Path(ApplicationConstants.Requests.Shop.ShopStore)
public class CustomerServiceContactInfo extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{storeId}/contact")
    public Response getInfoResponse(@PathParam("storeId") String storeId,
                                    @DefaultValue("")@QueryParam("isMember") String isMember,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(storeId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader(), 0));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String storeId, String isMember, String authToken) throws Exception, IOException {
        prepareResponse(storeId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader(), 0);
    }

    public CustomerServiceContactInfo(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String storeId, String isMember, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Shop.ShopStore
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.contact;
        if(!isMember.isEmpty()){
            this.path = ApplicationConstants.Requests.Shop.ShopStore
                    + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.contact
                    + ApplicationConstants.StringConstants.isMember;
        }
    }
}
