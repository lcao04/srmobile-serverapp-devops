package com.wakefern.Lists;

import com.wakefern.Lists.Models.GenericListItem;
import com.wakefern.ShoppingLists.ShoppingListItemsPost;
import com.wakefern.ShoppingLists.UserListPut;
import com.wakefern.global.ApplicationConstants;
import com.wakefern.ShoppingLists.ShoppingListsGet;
import com.wakefern.global.BaseService;
import com.wakefern.global.ErrorHandling.ExceptionHandler;
import com.wakefern.mywebgrocer.models.MWGHeader;

import javax.ws.rs.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.wakefern.global.ErrorHandling.*;

/**
 * Created by brandyn.brosemer on 9/22/16.
 */

@Path(ApplicationConstants.Requests.ShoppingLists.slGenericList)
public class AddItemToList extends BaseService {
    @POST
    @Produces("application/*")
    @Path("/{storeId}/users/{userId}/lists")
    public String getInfo(@PathParam("storeId") String storeId, @PathParam("userId") String userId,
                          @HeaderParam("Authorization") String authToken,@DefaultValue("") @QueryParam("listName") String listName,@DefaultValue("") @QueryParam("listId") String listId,@DefaultValue("") @QueryParam("update") String update, String jsonBody) throws Exception, IOException {

    	if(!update.isEmpty()){
    		DeleteItemFromList updateItem = new DeleteItemFromList();
    		updateItem.getInfo(storeId, userId, listId, authToken, listName, "", update, jsonBody);
    		return null;
    	}
    	
        GenericListItem createItem = new ObjectMapper().readValue(jsonBody,GenericListItem.class);
        if(listId.isEmpty()) {
            listId = ListHelpers.getListId(listName, userId, authToken, storeId);
        }
        System.out.println("List ID ::" + listId);
        //this.addItem(createItem,listId,storeId,userId,authToken);

        return this.addItem(createItem,listId,storeId,userId,authToken);
    }

    public AddItemToList(){this.serviceType = new MWGHeader();}

    /*
    Private Methods
     */

    /*

getInfo(@PathParam("chainId") String chainId, @PathParam("userId") String userId, @PathParam("listId") String listId,
                          @HeaderParam("Authorization") String authToken, String jsonBody)

     */

    private String addItem(GenericListItem item,String listId,String storeId,String userId,String authToken) throws Exception {
        ShoppingListItemsPost listPost = new ShoppingListItemsPost();
        JSONObject jsonItem = new JSONObject(new ObjectMapper().writeValueAsString(item));
        List itemList = new LinkedList();
        itemList.add(jsonItem);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Items",itemList);
        return listPost.getInfo(userId,storeId,listId,authToken,jsonObject.toString());
    }

}
