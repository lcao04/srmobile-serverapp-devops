package com.wakefern.logging;

/*
 *  author: Danny zheng
 *  date:   7/23/2018
 *  
 *  all error type are for the high-level REST end points
 */
public enum MwgErrorType {
	
	AUTHENTICATION_AUTHENTICATE_CHECKOUT,
	AUTHENTICATION_AUTHENTICATE_UI,
	AUTHENTICATION_AUTHENTICATE_USER,
	AUTHENTICATION_DELETE_SESSION_TOKEN,
	
	USERS_CREATE_REGISTRATION,
	USERS_GET_PROFILE,
	USERS_UPDATE_PREFERRED_STORE,
	
	CART_CREATE_EMAIL,
	CART_CREATE_ITEM,
	CART_CREATE_ITEMS,
	CART_DELETE,
	CART_DELETE_ITEM,
	CART_GET_CONTENTS,
	CART_GET_ITEM,
	CART_MERGE_FROM_GUEST,
	CART_MERGE_FROM_LIST,
	CART_REVIEW,
	CART_UPDATE_ITEM,
	
	CHAINS_CREATE_PROMO_CODE,
	CHAINS_DELETE_PROMO_CODE,
	CHAINS_GET_PROMO_CODES,
	
	FULFILLMENTS_GET_DELIVERY_DATES,
	FULFILLMENTS_GET_DELIVERY_DISTRICT_DATES,
	FULFILLMENTS_GET_DELIVERY_DISTRICT_TIMES,
	FULFILLMENTS_GET_DELIVERY_INFO,
	FULFILLMENTS_GET_DELIVERY_TIMES,
	FULFILLMENTS_GET_OPTIONS,
	FULFILLMENTS_GET_PICKUP_DATES,
	FULFILLMENTS_GET_PICKUP_INFO,
	FULFILLMENTS_GET_PICKUP_TIMES,
	
	ORDERS_CREATE_ORDERS,
	ORDERS_DELETE_ORDER,
	ORDERS_GET_ORDER,
	ORDERS_GET_ORDER_RESULTS,
	ORDERS_GET_ORDERS,
	ORDER_GET_CHANGE_ORDER,
	ORDER_DELETE_CHANGE_ORDER,
	ORDER_GET_CHANGE_ORDER_MESSAGE,
	ORDER_START_CHANGE_ORDER_MESSAGE,
	
	PAYMENTS_GET_OPTIONS,
	
	USERS_DELETE_TIME_SLOT,
	USERS_GET_BILLING_ADDR,
	USERS_GET_CHECKOUT_STATE,
	USERS_GET_COMMENTS,
	USERS_GET_DELIVERY_ADDR,
	USERS_GET_FULFILLMENT_OPTS,
	USERS_GET_PAYMENT_OPTIONS,
	USERS_GET_RESERVED_TIME_SLOT,
	USERS_GET_TIME_SLOT,
	USERS_UPDATE_BILLING_ADDR,
	USERS_UPDATE_CHECKOUT_STATE,
	USERS_UPDATE_COMMENTS,
	USERS_UPDATE_DELIVERY_ADDR,
	USERS_UPDATE_FREQUENT_SHOPPER_NUMBER,
	USERS_UPDATE_PAYMENT_OPTIONS,
	USERS_UPDATE_RESERVED_TIME_SLOT,
	USERS_UPDATE_TIME_SLOT,
	
	CIRCULARS_GET_CATEGORIES,
	CIRCULARS_GET_CATEGORY,
	CIRCULARS_GET_CATEGORY_ITEM,
	CIRCULARS_GET_CATEGORY_ITEMS,
	CIRCULARS_GET_CIRCULAR,
	CIRCULARS_GET_CIRCULARS,
	CIRCULARS_GET_CIRCULARS_DETAILS,
	CIRCULARS_GET_FULL_PAGES,
	CIRCULARS_GET_PAGE_DETAILS,
	CIRCULARS_GET_PAGE_ITEM,
	CIRCULARS_GET_PAGE_ITEMS,
	CIRCULARS_SEARCH,
	
	COUPONS_COUPON_ADD_TO_PPC,
	COUPONS_COUPON_ID_LIST,
	COUPONS_COUPON_ID_LIST_BY_PPC,
	COUPONS_COUPONS,
	COUPONS_COUPON_ID_BY_PROMO_CODE,
	
	LOGGING_APP_LOGGING,
	
	PAYMENT_GET_TOKEN,
	
	PRODUCTS_GET_BY_CATEGORY,
	PRODUCTS_GET_BY_ID,
	PRODUCTS_GET_BY_SKU,
	PRODUCTS_GET_CIRCULAR_ITEM_PRODUCTS,
	PRODUCTS_GET_FEATURED_BY_CATEGORY,
	PRODUCTS_GET_FEATURED_BY_STORE,
	PRODUCTS_GET_NUTRITION_BY_ID,
	PRODUCTS_GET_NUTRITION_BY_SKU,
	PRODUCTS_GET_PRODUCT_BY_SKUS,
	PRODUCTS_GET_SALE_ITEMS_BY_CATEGORY,
	PRODUCTS_GET_SUGGESTIONS,
	PRODUCTS_GET_VARIATIONS_BY_ID,
	PRODUCTS_SEARCH,
	
	CATEGORIES_GET_CATEGORIES, 
	CATEGORIES_GET_CATEGORIES_WITH_SALE_ITEMS, 
	CATEGORIES_GET_SUB_CATEGORIES, 
	CATEGORIES_GET_SUB_CATS_WITH_SALE_ITEMS,
	CATEGORIES_GET_CIRC_PROD_CATS_WKLY_SPECIALS,
	CATEGORIES_GET_CIRC_PROD_CATS_DETAIL,

	CHAINS_GET_COUNTRIES,
	
	RECIPES_CREATE_EMAIL_REQUEST,
	RECIPES_DELETE_USER_RECIPE,
	RECIPES_GET_CATEGORIES,
	RECIPES_GET_DETAILS,
	RECIPES_GET_FEATURED,
	RECIPES_GET_INGREDIENTS,
	RECIPES_GET_INSTRUCTIONS,
	RECIPES_GET_NUTRITIONAL,
	RECIPES_GET_PREP_TIMES,
	RECIPES_GET_USER_RECIPES,
	RECIPES_SEARCH,
	RECIPES_SEARCH_BY_CATEGORY,
	RECIPES_UPDATE_USER_RECIPE,
	
	RECOMMENDATIONS_PRODUCTS_RECOMMENDATION,
	
	REWARDS_GET_POINTS_FOR_PPC,
	
	SHOP_CREATE_CUST_SERV_FEEDBACK_MSG,
	SHOP_GET_CUST_SERV_CONTACT_INFO,
	SHOP_GET_ENTRY_RESOURCES_BY_CHAIN,
	SHOP_GET_ENTRY_RESOURCES_BY_STORE,
	SHOP_GET_STORE_SETTINGS,
	SHOP_GET_USER_DASHBOARD,
	
	SHOPPING_LISTS_CREATE_DUPLICATE_LIST,
	SHOPPING_LISTS_CREATE_LIST,
	SHOPPING_LISTS_CREATE_LIST_EMAIL,
	SHOPPING_LISTS_CREATE_LIST_ITEMS,
	SHOPPING_LISTS_DELETE_LIST,
	SHOPPING_LISTS_DELETE_LIST_ITEM,
	SHOPPING_LISTS_DELETE_LIST_ITEMS,
	SHOPPING_LISTS_GET_DUPLICATE_LIST_ITEM_LIMIT,
	SHOPPING_LISTS_GET_LIST,
	SHOPPING_LISTS_GET_LIST_ITEM,
	SHOPPING_LISTS_GET_LIST_ITEMS,
	SHOPPING_LISTS_GET_LISTS,
	SHOPPING_LISTS_UPDATE_LIST,
	SHOPPING_LISTS_UPDATE_LIST_ITEM,
	
	STORES_GET_CHAINS,
	STORES_GET_CITIES,
	STORES_GET_DELIVERS_TO,
	STORES_GET_REGIONS,
	STORES_GET_STORES,
	
	SUBSTITUTIONS_GET_SUBSTITUTIONS,
	SUBSTITUTIONS_UPDATE_SUBSTITUTIONS,
	ITEM_LOCATOR_ARRAY;

}
