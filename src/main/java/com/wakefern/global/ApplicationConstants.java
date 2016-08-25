package com.wakefern.global;

import com.wakefern.mywebgrocer.MWGApplicationConstants;

public final class ApplicationConstants {
    public static final String testUser				    = "bbrosemer@gmail.com";
    public static final String password				    = "fuzzy2345";
    public static final String jsonResponseType		    = "application/json";
    public static final String jsonAcceptType		    = "application/json";
    public static final String authToken 				= "FE8803F0-D4FA-4AFF-B688-1A3BD5915FAA";

    public static class StringConstants{
        public static final String address = "/address";
        public static final String all = "/all";
        public static final String backSlash = "/";
        public static final String billing = "/billing";
        public static final String categories = "/categories";
        public static final String circulars = "/circulars";
        public static final String comments = "/comments";
        public static final String dates = "/dates";
        public static final String delivery = "/delivery";
        public static final String fulfillments = "/fulfillments";
        public static final String guest = "/guest";
        public static final String items = "/items";
        public static final String pickup = "/pickup";
        public static final String promocode = "/promocode";
        public static final String special = "/special";
        public static final String store = "/store";
        public static final String stores = "/stores";
        public static final String subscription = "/subscription";
        public static final String substitutions = "/substitutions";
        public static final String times = "/times";


        public static final String ASDF = "/{categoryId}/store/{storeId}/categories";
    }

    public static class Requests{

        public static String baseURLV5 = MWGApplicationConstants.baseURL;
        public static String baseURLV1 = MWGApplicationConstants.baseURLv1;

        public static class Header{
            public static final String contentType	= "Content-Type";
            public static final String contentAccept = "Accept";
            public static final String contentAuthorization	= "Authorization";
        }

        public static class Authentication{
            public static final String Authenticate = MWGApplicationConstants.Requests.Authentication.Authenticate;
            public static final String Authenticatev1 = MWGApplicationConstants.Requests.Authentication.AuthenticateV1;
        }

        public static class Categories{
            public static final String CategoriesFromStoreId = MWGApplicationConstants.Requests.Categories.CategoriesFromStoreId;
            public static final String Subcategories = MWGApplicationConstants.Requests.Categories.SubCategories;
        }

        public static class Circular{
            public static final String Categories = MWGApplicationConstants.Requests.Circular.Categories;
        }

        public static class Checkout{
            public static final String Checkout = MWGApplicationConstants.Requests.Checkout.Checkout;
            public static final String Payments = MWGApplicationConstants.Requests.Checkout.Payments;
            public static final String UserCheckout = MWGApplicationConstants.Requests.Checkout.UserCheckout;
        }

        public static final class Cart{
            public static final String CartAuth = MWGApplicationConstants.Requests.Cart.CartAuth;
            public static final String CartUser = MWGApplicationConstants.Requests.Cart.CartUser;
        }
    }

    public static class RequestType{
        public static final String HTTPRequest = "HTTPRequest: ";

        public static final String ActiveCirculars = "Active Circulars";
        public static final String Categories = "Categories";
        public static final String CategoriesWithSpecials = "Categories with Specials";
        public static final String CheckoutFulfillment = "Checkout fufillment";
        public static final String CheckoutFulfillmentSpecial = "Checkout Fufillment Special";
        public static final String CircularCategories = "Circular Categories";
        public static final String CircularSearch = "Circular Search";
        public static final String RetrieveCategories = "Retrieve Categories";
        public static final String Subcategories = "Subcategories";
        public static final String SubcategoriesWithSpecials = "Subcategories with Specials";
    }
}
