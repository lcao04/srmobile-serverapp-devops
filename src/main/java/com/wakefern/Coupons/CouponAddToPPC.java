package com.wakefern.Coupons;

import com.wakefern.Wakefern.Models.WakefernHeader;
import com.wakefern.Wakefern.WakefernApplicationConstants;
import com.wakefern.global.ApplicationConstants;
import com.wakefern.global.BaseService;
import com.wakefern.global.ServiceMappings;
import com.wakefern.request.HTTPRequest;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by zacpuste on 9/27/16.
 */
@Path(ApplicationConstants.Requests.Coupons.CouponAddPPC)
public class CouponAddToPPC extends BaseService {
    public JSONObject matchedObjects;

    @GET
    @Produces("application/*")
    public Response getInfoResponse(@DefaultValue("") @QueryParam(WakefernApplicationConstants.Requests.Coupons.Metadata.PPC) String ppcParam,
                            @DefaultValue("") @QueryParam(WakefernApplicationConstants.Requests.Coupons.Metadata.CouponId) String couponId)
                          throws Exception, IOException {
        matchedObjects = new JSONObject();
        prepareResponse(ppcParam, couponId);

        //Execute Post
        ServiceMappings serviceMappings = new ServiceMappings();
        serviceMappings.setCouponMapping(this);

        try {
            return this.createValidResponse(HTTPRequest.executePostJSON(serviceMappings.getPath(), "", serviceMappings.getgenericHeader()));
        } catch (Exception e){
            return this.createErrorResponse(e);
        }
    }

    public String getInfo(String ppcParam, String couponId) throws Exception, IOException {
        matchedObjects = new JSONObject();
        prepareResponse(ppcParam, couponId);

        //Execute Post
        ServiceMappings serviceMappings = new ServiceMappings();
        serviceMappings.setCouponMapping(this);

        return HTTPRequest.executePostJSON(serviceMappings.getPath(), "", serviceMappings.getgenericHeader());
    }

    public CouponAddToPPC() {     this.serviceType = new WakefernHeader();    }

    private void prepareResponse(String ppcParam, String couponId){
        this.path = ApplicationConstants.Requests.Coupons.BaseCouponURL + ApplicationConstants.Requests.Coupons.CouponAddPPC
                + WakefernApplicationConstants.Requests.Coupons.Metadata.PPCQuery + ppcParam
                + WakefernApplicationConstants.Requests.Coupons.Metadata.CouponParam + couponId;
    }
}
