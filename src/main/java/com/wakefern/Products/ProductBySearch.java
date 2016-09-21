package com.wakefern.Products;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.Search;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by zacpuste on 8/25/16.
 */
@Path(ApplicationConstants.Requests.Categories.ProductsStore)
public class ProductBySearch extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{storeId}/search")
    public String getInfo(@PathParam("storeId") String storeId, @QueryParam("q") String q, @QueryParam("take") String take, @QueryParam("skip") String skip,
                          @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        this.token = authToken;

        String partialUrl = ApplicationConstants.Requests.Categories.ProductsStore
                + ApplicationConstants.StringConstants.backSlash + storeId + ApplicationConstants.StringConstants.search
                + ApplicationConstants.StringConstants.queryParam + q;

        Search search = new Search();
        String json = search.search(partialUrl, take, skip, authToken);
        JSONArray jsonArray = new JSONArray(json);
        int itemCount = jsonArray.getJSONObject(0).getInt(ApplicationConstants.ProductSearch.itemCount);

        if(itemCount == 0){
            return "{}";
        } else {
            return formatResponse(jsonArray);
        }
    }

    private String formatResponse(JSONArray jsonArray){
        JSONArray items = new JSONArray();
        JSONArray facets = new JSONArray();
        for(Object match: jsonArray){
            JSONObject currentMatch = (JSONObject) match;

            JSONArray item = currentMatch.getJSONArray(ApplicationConstants.ProductSearch.items);
            for(int j = 0; j < item.length(); j++){
                items.put(item.getJSONObject(j));
            }
            JSONArray facet = currentMatch.getJSONArray(ApplicationConstants.ProductSearch.facets);
            for(int j = 0; j < facet.length(); j++){
                facets.put(facet.getJSONObject(j));
            }
        }

        JSONObject zeroth = jsonArray.getJSONObject(0);
        JSONObject retval = new JSONObject();

        retval.append(ApplicationConstants.ProductSearch.activeFilters, zeroth.getJSONArray(ApplicationConstants.ProductSearch.activeFilters));
        retval.append(ApplicationConstants.ProductSearch.recentFilters, zeroth.getJSONArray(ApplicationConstants.ProductSearch.recentFilters));
        retval.append(ApplicationConstants.ProductSearch.facets, facets);
        retval.append(ApplicationConstants.ProductSearch.sortLinks, zeroth.getJSONArray(ApplicationConstants.ProductSearch.sortLinks));
        retval.append(ApplicationConstants.ProductSearch.pages, zeroth.getJSONArray(ApplicationConstants.ProductSearch.pages));
        retval.append(ApplicationConstants.ProductSearch.pageLinks, zeroth.getJSONArray(ApplicationConstants.ProductSearch.pageLinks));
        retval.append(ApplicationConstants.ProductSearch.items, items);
        retval.append(ApplicationConstants.ProductSearch.skip, Integer.toString(zeroth.getInt(ApplicationConstants.ProductSearch.skip)));
        retval.append(ApplicationConstants.ProductSearch.take, Integer.toString(zeroth.getInt(ApplicationConstants.ProductSearch.take)));
        retval.append(ApplicationConstants.ProductSearch.totalQuantity, Integer.toString(zeroth.getInt(ApplicationConstants.ProductSearch.totalQuantity)));
        retval.append(ApplicationConstants.ProductSearch.itemCount, Integer.toString(zeroth.getInt(ApplicationConstants.ProductSearch.itemCount)));
        retval.append(ApplicationConstants.ProductSearch.links, zeroth.getJSONArray(ApplicationConstants.ProductSearch.links));


        return retval.toString();
    }
}