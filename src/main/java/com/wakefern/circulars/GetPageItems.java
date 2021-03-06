package com.wakefern.circulars;

import com.wakefern.global.BaseService;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path(MWGApplicationConstants.Requests.Circulars.prefix)
public class GetPageItems extends BaseService {
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
    public GetPageItems() {
        this.requestPath = MWGApplicationConstants.Requests.Circulars.prefix + MWGApplicationConstants.Requests.Circulars.pageItems;
    }
    
	@GET
    @Consumes(MWGApplicationConstants.Headers.generic)
    @Produces(MWGApplicationConstants.Headers.generic)
    @Path(MWGApplicationConstants.Requests.Circulars.pageItems)
    public Response getResponse(
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.storeID) String storeID,
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.chainID) String chainID,    		
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.circularID) String circularID,
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.pageID) String pageID,
    		
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.skip) String skip,
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.take) String take,
    		
    		@HeaderParam(MWGApplicationConstants.Headers.Params.auth) String sessionToken
	) throws Exception, IOException {
        		
		this.requestHeader = new MWGHeader(MWGApplicationConstants.Headers.Circulars.items, MWGApplicationConstants.Headers.json, sessionToken);
		this.requestParams = new HashMap<String, String>();
		this.queryParams   = new HashMap<String, String>();
		
		// Build the Map of Request Path parameters
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.storeID, storeID);
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.chainID, chainID);
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.circularID, circularID);
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.pageID, pageID);
		
		// Map of the Query String parameters
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.take, take);
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.skip, skip);
		
        try {
            String jsonResponse = this.mwgRequest(BaseService.ReqType.GET, null, "com.wakefern.circulars.GetPageItems");
            return this.createValidResponse(jsonResponse);
        
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }
}

