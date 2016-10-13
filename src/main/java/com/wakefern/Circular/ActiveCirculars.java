package com.wakefern.Circular;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by zacpuste on 8/19/16.
 */

@Path(ApplicationConstants.Requests.Circular.Categories)
public class ActiveCirculars extends BaseService{
    @GET
    @Produces("application/*")
    @Path("/{chainId}/stores/{storeId}/circulars")
    public Response getInfoResponse(@PathParam("chainId") String chainId, @PathParam("storeId") String storeId,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(chainId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGetJSON(secondMapping.getPath(), secondMapping.getgenericHeader()));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String chainId, String storeId, String authToken) throws Exception, IOException {
        prepareResponse(chainId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGetJSON( secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public ActiveCirculars(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String chainId, String storeId, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Circular.Categories + ApplicationConstants.StringConstants.backSlash
                + chainId + ApplicationConstants.StringConstants.stores + ApplicationConstants.StringConstants.backSlash
                + storeId + ApplicationConstants.StringConstants.circulars;
    }
}
