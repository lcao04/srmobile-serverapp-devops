package com.wakefern.Cart;

import com.wakefern.Wakefern.ItemLocatorArray;
import com.wakefern.Wakefern.ItemLocatorArrayPost;
import com.wakefern.Wakefern.WakefernAuth;
import com.wakefern.global.ApplicationConstants;

import org.json.JSONArray;
import org.json.JSONObject;

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
@Path(ApplicationConstants.Requests.Cart.CartUser)
public class CartGet extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{userId}/store/{storeId}")
    public Response getInfoResponse(@PathParam("userId") String userId,
    		@PathParam("storeId") String storeId,
            @HeaderParam("Authorization") String authToken,
            @DefaultValue("") @QueryParam("shortStoreId") String shortStoreId) throws Exception, IOException {
        prepareResponse(userId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        try{
            //return this.createValidResponse(HTTPRequest.executeGetJSON(secondMapping.getPath(), secondMapping.getgenericHeader()));
        	String cartResp = HTTPRequest.executeGetJSON(secondMapping.getPath(), secondMapping.getgenericHeader());
        	if(shortStoreId.isEmpty()){
                return this.createValidResponse(cartResp);
        	}
        	JSONObject cart = new JSONObject(cartResp);
        	JSONArray items = (JSONArray)cart.get(ApplicationConstants.AisleItemLocator.Items);
			JSONObject searchAble = new JSONObject();
			JSONObject retval = new JSONObject();
        	if(!items.isNull(0)){
        		WakefernAuth auth = new WakefernAuth();
        		String authString = auth.getInfo(ApplicationConstants.AisleItemLocator.WakefernAuth);
        		if(!authString.isEmpty()){
        			//return without AISLE Data
        			String responseString = "";
        			for (int i = 0, size = items.length(); i < size; i++){
        		    	//Get the items in the array and make a comma separated string of them as well trim the first and last digit 
        		    	JSONObject item = (JSONObject) items.get(i);
                    	
                    	String itemId = item.get(ApplicationConstants.AisleItemLocator.Sku).toString();
                    	String sku = this.updateUPC(itemId);
						if(sku.matches("[0-9]+")) {
							responseString += sku + ",";
							searchAble.append(ApplicationConstants.AisleItemLocator.Items, item);
						} else {
							retval.append(ApplicationConstants.AisleItemLocator.Items, item);
						}
        		    }
					items = (JSONArray)searchAble.get(ApplicationConstants.AisleItemLocator.Items);
        	    	responseString = responseString.substring(0,responseString.length()-1);
					String jsonBody = ApplicationConstants.AisleItemLocator.jsonOpen + shortStoreId
							+ ApplicationConstants.AisleItemLocator.jsonMiddle + responseString
							+ ApplicationConstants.AisleItemLocator.jsonClose;
					ItemLocatorArrayPost itemLocatorArrayPost = new ItemLocatorArrayPost();
					String itemLocations = itemLocatorArrayPost.getInfo(authString, jsonBody);
        			try{
        				JSONArray array = new JSONArray(itemLocations);
            		    for (int i = 0, size = array.length(); i < size; i++){
            				JSONObject locationItems = (JSONObject)array.get(i);
            				JSONArray locations = locationItems.getJSONArray(ApplicationConstants.AisleItemLocator.item_locations);
            				
            				//Iterate through these too - inner excluded UPCs 
            				for(int z = 0, sizez = locations.length(); z<sizez;z++){
            					JSONObject aItem = (JSONObject) locations.get(z);
                		    	for (int j = 0, sizej = items.length(); j < sizej; j++){
                    		    	//Get the items in the array and make a comma separated string of them as well trim the first and last digit 
                    		    	JSONObject item = (JSONObject) items.get(j);
									String itemString = aItem.get(ApplicationConstants.AisleItemLocator.upc_13_num).toString();
                                	if(item.get(ApplicationConstants.AisleItemLocator.Sku).toString().contains(itemString)){
                                		if(aItem.has(ApplicationConstants.AisleItemLocator.area_desc)){
                                			item.put(ApplicationConstants.AisleItemLocator.Aisle, aItem.get(ApplicationConstants.AisleItemLocator.area_desc));
                                    		break;
                                		} else{
                                    		item.put(ApplicationConstants.AisleItemLocator.Aisle, ApplicationConstants.AisleItemLocator.Other);
                                    		break;
                                    	}
                                	} else if(j+1 == sizej){
                                		item.put(ApplicationConstants.AisleItemLocator.Aisle, ApplicationConstants.AisleItemLocator.Other);
                                	}
									retval.append(ApplicationConstants.AisleItemLocator.Items, item);
                    		    }
            				}
            		    }
						return this.createValidResponse(retval.toString());
        			}catch(Exception e){
        				//Error casting
                        return this.createValidResponse(cartResp);
        			}
        		}
                return this.createValidResponse(cart.toString());	
        	}else{
        		//Return without anything 
                return this.createValidResponse(cartResp);
        	}
        	
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }
    
    private String updateUPC(String sku){
    	sku = sku.substring(1);
    	sku = sku.substring(0,sku.length()-1);
    	return sku;
    }
    
    public String getInfo(String userId, String storeId, String authToken) throws Exception, IOException {
        prepareResponse(userId, storeId, authToken);

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setMapping(this);

        return HTTPRequest.executeGetJSON(secondMapping.getPath(), secondMapping.getgenericHeader());
    }

    public void prepareResponse(String userId, String storeId, String authToken){
        this.token = authToken;
        this.path = ApplicationConstants.Requests.Cart.CartUser
                + ApplicationConstants.StringConstants.backSlash + userId + ApplicationConstants.StringConstants.store
                + ApplicationConstants.StringConstants.backSlash + storeId;
    }

    public CartGet(){
        this.serviceType = new MWGHeader();
    }
}
