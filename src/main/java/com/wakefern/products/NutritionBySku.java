package com.wakefern.products;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path(MWGApplicationConstants.Requests.Products.productPath)
public class NutritionBySku extends BaseService {
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
    public NutritionBySku() {
        this.requestPath = MWGApplicationConstants.Requests.Products.productPath + MWGApplicationConstants.Requests.Products.nutritionBySKU;
    }
    
	@GET
    @Consumes(ApplicationConstants.jsonHeaderType)
    @Produces("application/*")
    @Path(MWGApplicationConstants.Requests.Products.nutritionBySKU)
    public Response getResponse(
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.productSKU) String productSKU,
    		@PathParam(MWGApplicationConstants.Requests.Params.Path.storeID) String storeID,
    		@HeaderParam(MWGApplicationConstants.Headers.Params.auth) String sessionToken
	) throws Exception, IOException {
        		
		this.requestHeader = new MWGHeader(ApplicationConstants.jsonHeaderType, ApplicationConstants.jsonHeaderType, sessionToken);
		this.requestParams = new HashMap<String, String>();
		
		// Build the Map of Request Path parameters
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.storeID, storeID);
		this.requestParams.put(MWGApplicationConstants.Requests.Params.Path.productSKU, productSKU);
		
        try {
            String jsonResponse = this.mwgRequest(BaseService.ReqType.GET, null);
            return this.createValidResponse(jsonResponse);
        
        } catch (Exception e) {
            return this.createErrorResponse(e);
        }
    }
}

