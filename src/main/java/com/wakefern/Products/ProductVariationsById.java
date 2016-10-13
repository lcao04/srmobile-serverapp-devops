package com.wakefern.Products;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by zacpuste on 8/25/16.
 */
@Path(ApplicationConstants.Requests.Categories.ProductId)
public class ProductVariationsById extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{productId}/store/{storeId}/all/variations")
    public Response getInfoResponse(@PathParam("productId") String productId, @PathParam("storeId") String storeId,
                            @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        prepareResponse(productId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader()));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String productId, String storeId, String authToken) throws Exception, IOException {
        prepareResponse(productId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGet(secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public ProductVariationsById(){
        this.serviceType = new MWGHeader();
    }

    private void prepareResponse(String productId, String storeId, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Categories.ProductId
                + ApplicationConstants.StringConstants.backSlash + productId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.all
                + ApplicationConstants.StringConstants.variations;
    }
}
