package com.wakefern.stores;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path(MWGApplicationConstants.Requests.Stores.storesPath)
public class Cities extends BaseService {
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
    public Cities() {
        this.requestPath = MWGApplicationConstants.Requests.Stores.storesPath + MWGApplicationConstants.Requests.Stores.cities;
    }
    
	@GET
    @Consumes(MWGApplicationConstants.Headers.Stores.cities)
    @Produces("application/*")
    @Path(MWGApplicationConstants.Requests.Stores.cities)
    public Response getCities(
    		@PathParam(MWGApplicationConstants.regionID) String regionID,
    		@PathParam(MWGApplicationConstants.chainID) String chainID,
    		@QueryParam(MWGApplicationConstants.queryServices) String services,
    		@QueryParam(MWGApplicationConstants.querySkip) String skip,
    		@QueryParam(MWGApplicationConstants.queryTake) String take,
    		@HeaderParam(MWGApplicationConstants.Headers.Params.auth) String sessionToken) throws Exception, IOException {
        
		this.requestHeader = new MWGHeader(MWGApplicationConstants.Headers.Stores.cities, ApplicationConstants.jsonResponseType, sessionToken);
		this.requestParams = new HashMap<String, String>();
		this.queryParams   = new HashMap<String, String>();
		
		// Build the Map of Request Path parameters
		this.requestParams.put(MWGApplicationConstants.chainID, chainID);
		this.requestParams.put(MWGApplicationConstants.regionID, regionID);
		
		// Build the Map of Request Query parameters
		this.queryParams.put(MWGApplicationConstants.queryServices, services);
		this.queryParams.put(MWGApplicationConstants.querySkip, skip);
		this.queryParams.put(MWGApplicationConstants.queryTake, take);
		
        try {
            String jsonResponse = this.mwgRequest(BaseService.ReqType.GET, null);
            return this.createValidResponse(jsonResponse);
        
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }
}