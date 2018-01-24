package com.wakefern.Wakefern;

/**
 * Created by brandyn.brosemer on 9/13/16.
 */
public class WakefernApplicationConstants {
	
    public static class Requests {
        public static final String authToken = "7bd4a45d-4fef-4edf-a74d-c2214c0b7b54"; // Used by legacy Wakefern endpoints.

    		public static class Coupons {
            public static final String baseURL = "http://couponprodwest.azure-mobile.net/api";

            public static class Headers{
                public static final String CouponAuthenticationToken        = "noowhTBIYfzVrXOcFrNSwIFbkMoqRh19";
                public static final String CouponAuthenticationTokenHeader  = "X-ZUMO-APPLICATION";
            }

            public static class ListId{
                public static final String CouponId = "/getCouponIDListBySR";
                public static final String CouponByPPC = "/getCouponIDListByPPC";
                public static final String CouponAddPPC = "/addCouponToPPC";
            }

            public static class Metadata{
                public static final String PPCCoupons = "/getPPCCoupons";
                public static final String Metadata = "/getCouponMetadata";
                public static final String MetadataRecommendations = "/getCouponsRecommendations";
                public static final String PPC = "ppc_number";
                public static final String PPC_All = "all";
                public static final String PPCQuery = "?ppc_number=";
                public static final String CouponId = "coupon_id";
                public static final String CouponParam = "&coupon_id=";
                public static final String ClipSource = "&clip_source=";
                public static final String ClipSource_App_SR = "APP_SR";
                //For Coupon and recommendation
                public static final String store = "?storeId=";
                public static final String pseudo = "&pseudo=";
                public static final String email = "&email=";
                public static final String ppcNo = "&ppc_number=";
            }

            public static class Search{
                public static final String brandName = "brand_name";
                public static final String category = "Category";
                public static final String longDescription = "long_description";
                public static final String shortDescription = "short_description";
                public static final String requirementDescription = "requirement_description";
            }
        }
    }
}
