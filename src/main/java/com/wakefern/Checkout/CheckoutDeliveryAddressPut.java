package com.wakefern.Checkout;

import com.ibm.json.java.JSONObject;
import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by zacpuste on 8/24/16.
 */
@Path(ApplicationConstants.Requests.Checkout.UserCheckout)
public class CheckoutDeliveryAddressPut extends BaseService {
    @PUT
    @Produces("application/*")
    @Path("/{userId}/store/{storeId}/address/delivery")
    /**
     * {
     * Format for passed in json
     "FirstName": "Brandyn",
     "LastName": "Ngo",
     "Phone1": {
     "Number": "7322334775",
     "IsMobile": true
     },
     "Phone2": null,
     "Street1": "2513 Autumn drive",
     "Line2": "",
     "Line3": "",
     "City": "Manasquan",
     "State": "NJ",
     "PostalCode": "08736",
     "CountryCode": "USA",
     "NeighborhoodId": "",
     "DeliveryPointId": null,
     "Validated": false
     }
     */
    public String getInfo(@PathParam("userId") String userId, @PathParam("storeId") String storeId,
                          @HeaderParam("Authorization") String authToken, String jsonBody) throws Exception, IOException {
        JSONObject myJSONObj = new JSONObject();

        this.token = authToken;
        this.path = ApplicationConstants.Requests.Checkout.UserCheckout + ApplicationConstants.StringConstants.backSlash
                + userId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.address
                + ApplicationConstants.StringConstants.delivery;

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setPutMapping(this, jsonBody);

        return HTTPRequest.executePut("", secondMapping.getPath(), "", secondMapping.getGenericBody(), secondMapping.getgenericHeader());

    }
    public CheckoutDeliveryAddressPut(){
        this.serviceType = new MWGHeader();
    }
}

