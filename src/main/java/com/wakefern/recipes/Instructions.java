package com.wakefern.recipes;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path(MWGApplicationConstants.Requests.Recipes.recipesPath)
public class Instructions extends BaseService {
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
    public Instructions() {
        this.requestPath = MWGApplicationConstants.Requests.Recipes.recipesPath + MWGApplicationConstants.Requests.Recipes.instructions;
    }
    
	@GET
    @Consumes(MWGApplicationConstants.Headers.Recipes.instructions)
    @Produces("application/*")
    @Path(MWGApplicationConstants.Requests.Recipes.instructions)
    public Response getResponse(
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.chainID) String chainID,
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.recipeID) String recipeID,
    		
    		@QueryParam(MWGApplicationConstants.Requests.Params.Query.storeID) String storeID,
    		
    		@HeaderParam(MWGApplicationConstants.Headers.Params.auth) String sessionToken
	) throws Exception, IOException {
        		
		this.requestHeader = new MWGHeader(MWGApplicationConstants.Headers.Recipes.instructions, ApplicationConstants.jsonResponseType, sessionToken);
		this.requestParams = new HashMap<String, String>();
		this.queryParams   = new HashMap<String, String>();
		
		// Build the Map of Request Path parameters
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.chainID, chainID);
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.recipeID, recipeID);
		
		// Build the Map of Query String parameters.
		this.queryParams.put(MWGApplicationConstants.Requests.Params.Query.storeID, storeID);
		
        try {
            String jsonResponse = this.mwgRequest(BaseService.ReqType.GET, null);
            return this.createValidResponse(jsonResponse);
        
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }
}
