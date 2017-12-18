package com.wakefern.Circular;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zacpuste on 9/14/16.
 */
@Path(ApplicationConstants.Requests.Circular.Categories)
public class PageItemId extends BaseService {
	
	private final static Logger logger = Logger.getLogger("PageItemId");
	
    @GET
    @Produces("application/*")
    @Path("/{chainId}/stores/{storeId}/circulars/{circularId}/pages/{pageId}/items/{itemId}")
    public Response getInfoResponse(@PathParam("chainId") String chainId, @PathParam("storeId") String storeId, @PathParam("circularId") String circularId, @PathParam("pageId") String pageId,
                                    @DefaultValue("")@QueryParam("isMember") String isMember,
                            @PathParam("itemId") String itemId, @HeaderParam("Authorization") String authToken) throws Exception, IOException {

        prepareResponse(chainId, storeId, circularId, pageId, itemId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setGetMapping(this, null);

        try {
        	String secondMappingPath = secondMapping.getPath();
        	String jsonResp = HTTPRequest.executeGet(secondMappingPath, secondMapping.getgenericHeader(), 0);
            return this.createValidResponse(jsonResp);
        } catch (Exception e){
        	logger.log(Level.SEVERE, "[getInfoResponse]::Exception getInfoResponse ", e);
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String chainId, String storeId, String circularId, String pageId, String itemId, String isMember, String authToken) throws Exception, IOException {
        prepareResponse(chainId, storeId, circularId, pageId, itemId, isMember, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setGetMapping(this, null);
        
        String jsonResp = HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader(), 0);
        
        return jsonResp;
    }

    public PageItemId(){
        this.requestHeader = new MWGHeader();
    }

    private void prepareResponse(String chainId, String storeId, String circularId, String pageId, String itemId, String isMember, String authToken){
        this.requestToken = authToken;
        this.requestPath = ApplicationConstants.Requests.Circular.Categories
                + ApplicationConstants.StringConstants.backSlash + chainId + ApplicationConstants.StringConstants.stores
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.circulars
                + ApplicationConstants.StringConstants.backSlash + circularId + ApplicationConstants.StringConstants.pages
                + ApplicationConstants.StringConstants.backSlash + pageId + ApplicationConstants.StringConstants.items
                + ApplicationConstants.StringConstants.backSlash + itemId;
        if(!isMember.isEmpty()){
            this.requestPath = ApplicationConstants.Requests.Circular.Categories
                    + ApplicationConstants.StringConstants.backSlash + chainId + ApplicationConstants.StringConstants.stores
                    + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.circulars
                    + ApplicationConstants.StringConstants.backSlash + circularId + ApplicationConstants.StringConstants.pages
                    + ApplicationConstants.StringConstants.backSlash + pageId + ApplicationConstants.StringConstants.items
                    + ApplicationConstants.StringConstants.backSlash + itemId + ApplicationConstants.StringConstants.isMember;
        }
        logger.log(Level.INFO, "[prepareResponse]::Path: "+ this.requestPath); 

    }
}