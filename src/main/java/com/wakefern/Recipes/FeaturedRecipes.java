package com.wakefern.Recipes;

import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.global.XMLtoJSONConverter;
import com.wakefern.mywebgrocer.models.MWGHeader;
import com.wakefern.request.HTTPRequest;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zacpuste on 8/29/16.
 */
@Path(ApplicationConstants.Requests.Recipes.RecipeChain)
public class FeaturedRecipes extends BaseService {
    @GET
    @Produces("application/*")
    @Path("/{chainId}/featured")
    public String getInfo(@PathParam("chainId") String chainId, @QueryParam("q") String q,
                          @HeaderParam("Authorization") String authToken) throws Exception, IOException {
        this.token = authToken;

        this.path = ApplicationConstants.Requests.Recipes.RecipeChain
                + ApplicationConstants.StringConstants.backSlash + chainId + ApplicationConstants.StringConstants.featured
                + ApplicationConstants.StringConstants.queryParam + q;

        ServiceMappings secondMapping = new ServiceMappings();
        secondMapping.setServiceMapping(this, null);

        String featuredReturn =  HTTPRequest.executeGet(secondMapping.getServicePath(), secondMapping.getgenericHeader());

        String extractedXml = extractId(featuredReturn, chainId, authToken);
        XMLtoJSONConverter xmLtoJSONConverter = new XMLtoJSONConverter();

        return xmLtoJSONConverter.convert(extractedXml);
    }

    public FeaturedRecipes(){
        this.serviceType = new MWGHeader();
    }

    public String extractId(String featuredReturn, String chainId, String authToken){
        //Pattern matches any set of numbers between <Id></Id>
        Pattern pattern = Pattern.compile("<Id>([^abc]*)</Id>");
        Matcher matcher = pattern.matcher(featuredReturn);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            try {
                //Create new recipe detail call
                RecipeDetailsXml recipeDetails = new RecipeDetailsXml();
                //Save match to use in recipe detail call
                String temp = matcher.group(0).substring(4, matcher.group().length() - 5);
                //matcher does support append, so replace match with match and recipe detail
                matcher.appendReplacement(sb, matcher.group(0) + recipeDetails.getInfo(chainId, temp, authToken));
            } catch (Exception e){
                System.out.print(matcher.group(0).substring(4, matcher.group().length() - 5) + "Failed");
            }
        }
        //Append any data after final match
        matcher.appendTail(sb);
        return sb.toString();
    }
}
