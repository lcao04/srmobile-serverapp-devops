package com.wakefern.stores;

import com.wakefern.global.BaseService;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path(MWGApplicationConstants.Requests.Stores.prefix)
public class GetRegions extends BaseService {
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
    public GetRegions() {
        this.requestPath = MWGApplicationConstants.Requests.Stores.prefix + MWGApplicationConstants.Requests.Stores.regions;
    }
    
	@GET
    @Consumes(MWGApplicationConstants.Headers.generic)
    @Produces(MWGApplicationConstants.Headers.generic)
    @Path(MWGApplicationConstants.Requests.Stores.regions)
    public Response getResponse(
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.chainID) String chainID,
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.services) String services,
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.skip) String skip,
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.take) String take,
    		@HeaderParam(MWGApplicationConstants.Headers.Params.auth) String sessionToken) throws Exception, IOException {
        
		this.requestHeader = new MWGHeader(MWGApplicationConstants.Headers.Stores.regions, MWGApplicationConstants.Headers.json, sessionToken);
		this.requestParams = new HashMap<String, String>();
		this.queryParams   = new HashMap<String, String>();
		
		// Build the Map of Request Path parameters
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.chainID, chainID);
		
		// Build the Map of Request Query parameters
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.services, services);
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.skip, skip);
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.take, take);
		
        try {
            String jsonResponse = this.mwgRequest(BaseService.ReqType.GET, null, "com.wakefern.stores.GetRegions");
            return this.createValidResponse(jsonResponse);
        
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }
}
