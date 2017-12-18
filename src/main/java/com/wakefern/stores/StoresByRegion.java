package com.wakefern.stores;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.global.XMLtoJSONConverter;

import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by zacpuste on 11/14/16.
 */
@Path(MWGApplicationConstants.Requests.Stores.StoreLocator)
public class StoresByRegion extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{chainId}/region/{regionId}/page/{pageNumber}/size/{pageSize}")
    public Response getInfoResponse(@PathParam("chainId") String chainId, @PathParam("regionId") String regionId,
                                    @PathParam("pageNumber") String pageNumber, @PathParam("pageSize") String pageSize,
                                    @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        regionId = URLEncoder.encode(regionId, "UTF-8");
        regionId = regionId.replaceAll("\\+", "%20");
        prepareResponse(chainId, regionId, pageNumber, pageSize, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setServiceMappingv1(this, null);

        try {
            String xmlRequest = HTTPRequest.executeGet(secondMapping.getServicePath(), secondMapping.getgenericHeader(), 0);
            XMLtoJSONConverter xmLtoJSONConverter = new XMLtoJSONConverter();
            return this.createValidResponse(xmLtoJSONConverter.convert(xmlRequest));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String chainId, String regionId, String pageNumber, String pageSize, String authToken)
            throws Exception, IOException {
        regionId = URLEncoder.encode(regionId, "UTF-8");
        regionId = regionId.replaceAll("\\+", "%20");
        prepareResponse(chainId, regionId, pageNumber, pageSize, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setServiceMappingv1(this, null);

        String xmlRequest = HTTPRequest.executeGet(secondMapping.getServicePath(), secondMapping.getgenericHeader(), 0);
        XMLtoJSONConverter xmLtoJSONConverter = new XMLtoJSONConverter();
        return xmLtoJSONConverter.convert(xmlRequest);
    }

    public StoresByRegion(){
        this.requestHeader = new MWGHeader();
    }

    private void prepareResponse(String chainId, String regionId, String pageNumber, String pageSize, String authToken){
        
    		this.requestToken = ApplicationConstants.Requests.Tokens.planningToken;

        this.requestPath = MWGApplicationConstants.Requests.Stores.StoreLocator
                + "/" + chainId    + ApplicationConstants.StringConstants.region
                + "/" + regionId   + ApplicationConstants.StringConstants.page
                + "/" + pageNumber + ApplicationConstants.StringConstants.size
                + "/" + pageSize;
    }
}
