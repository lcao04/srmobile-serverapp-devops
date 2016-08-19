package com.wakefern.Circular;

import com.ibm.json.java.JSONObject;
import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by zacpuste on 8/19/16.
 */

@Path(ApplicationConstants.Requests.Circular.Categories)
public class ActiveCirculars extends BaseService{
    @GET
    @Produces("application/*")
    @Path("/{chainId}/stores/{storeId}/circulars")
    public String getInfo(@PathParam("chainId") String chainId, @PathParam("storeId") String storeId,
                          @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        JSONObject myJSONObj = new JSONObject();

        this.token = authToken;
        //this.path = ApplicationConstants.Requests.Circular.Categories+ "/" + chainId + "/stores/" + storeId + "/circulars";
        this.path = ApplicationConstants.Requests.Circular.Categories + ApplicationConstants.StringConstants.backSlash
                + chainId + ApplicationConstants.StringConstants.stores + ApplicationConstants.StringConstants.backSlash
                + storeId + ApplicationConstants.StringConstants.circulars;

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        myJSONObj.put(ApplicationConstants.RequestType.HTTPRequest + ApplicationConstants.RequestType.ActiveCirculars,
                HTTPRequest.executeGet( secondMapping.getPath(), secondMapping.getgenericHeader()));

        return myJSONObj.toString();
    }

    public ActiveCirculars(){
        this.serviceType = new MWGHeader();
    }
}
