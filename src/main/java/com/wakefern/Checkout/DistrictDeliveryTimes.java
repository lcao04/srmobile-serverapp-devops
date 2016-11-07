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
 * Created by zacpuste on 10/31/16.
 */
@Path(ApplicationConstants.Requests.Checkout.Checkout)
public class DistrictDeliveryTimes extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{storeId}/delivery/{zipCode}/district/{districtId}/times")
    public Response getInfoResponse(@PathParam("storeId") String storeId, @PathParam("zipCode") String zipCode, @PathParam("districtId") String districtId,
                                    @DefaultValue("")@QueryParam("isMember") String isMember,
                                    @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(storeId, zipCode, districtId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader(), 0));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String storeId, String zipCode, String districtId, String isMember, String authToken) throws Exception, IOException {
        prepareResponse(storeId, zipCode, districtId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader(), 0);
    }

    public DistrictDeliveryTimes(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String storeId, String zipCode, String districtId, String isMember, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Checkout.Checkout
                + ApplicationConstants.StringConstants.backSlash + storeId +  ApplicationConstants.StringConstants.delivery
                + ApplicationConstants.StringConstants.backSlash + zipCode + ApplicationConstants.StringConstants.distrcit
                + ApplicationConstants.StringConstants.backSlash + districtId + ApplicationConstants.StringConstants.times;
        if(!isMember.isEmpty()){
            this.path = ApplicationConstants.Requests.Checkout.Checkout
                    + ApplicationConstants.StringConstants.backSlash + storeId +  ApplicationConstants.StringConstants.delivery
                    + ApplicationConstants.StringConstants.backSlash + zipCode + ApplicationConstants.StringConstants.distrcit
                    + ApplicationConstants.StringConstants.backSlash + districtId + ApplicationConstants.StringConstants.times
                    + ApplicationConstants.StringConstants.isMember;
        }
    }
}
