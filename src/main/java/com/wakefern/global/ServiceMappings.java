package com.wakefern.global;

import com.wakefern.Wakefern.Models.WakefernHeader;
import com.wakefern.mywebgrocer.MWGApplicationConstants;
import com.wakefern.mywebgrocer.models.*;

import java.util.Map;
import java.util.Map.Entry;

public class ServiceMappings {

	private String path;
	private String servicePath;
	private String genericBody;
	private Map<String, String> genericHeader;
	
	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	public Map<String, String> getgenericHeader() {
		return genericHeader;
	}
	
	public void setgenericHeader(Map<String, String> genericHeader) {
		this.genericHeader = genericHeader;
	}

	public String getGenericBody() {
		return genericBody;
	}
	
	public void setGenericBody(String genericBody) {
		this.genericBody = genericBody;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getServicePath() {
		return servicePath;
	}
	
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	/**
	 * Used for GET & DELETE requests.
	 * 
	 * @param serviceObject
	 * @param reqParams
	 */
	public void setGetMapping(Object serviceObject, Map<String, String> reqParams, Map<String, String> queryParams) {
		BaseService aService = (BaseService) serviceObject;
		
		if (aService.requestHeader instanceof MWGHeader) {
			buildGetRequest(aService, reqParams, queryParams);
		}
	}

	/**
	 * Used for PUT & POST requests.
	 * 
	 * @param serviceObject
	 * @param jsonBody
	 * @param reqParams
	 */
	public void setPutMapping(Object serviceObject, String jsonBody, Map<String, String> reqParams, Map<String, String> queryParams) {
		BaseService aService = (BaseService) serviceObject;
		
		if (aService.requestHeader instanceof MWGHeader) {
			MWGBody mwgBody = new MWGBody("");
			buildPostRequest(aService, mwgBody, jsonBody, reqParams, queryParams);
		}
	}
	
	//v1 calls with passed in authToken
	public void setServiceMappingv1(Object serviceObject, String jsonBody) {
		BaseService aService = (BaseService) serviceObject;
		if (aService.requestHeader instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendServiceMappingv1((BaseService) serviceObject, mwgHeader, mwgBody, jsonBody);
		}
	}

	//Used for coupon api
	public void setCouponMapping(Object serviceObject){
		BaseService aService = (BaseService) serviceObject;
		if (aService.requestHeader instanceof WakefernHeader){
			WakefernHeader wakefernHeader = new WakefernHeader();
			sendCouponMapping((BaseService) serviceObject, wakefernHeader);
		}
	}
	
	public void setMappingWithURL(Object serviceObject, String baseURL){
		BaseService aService = (BaseService) serviceObject;
		if (aService.requestHeader instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			sendRequestWithURL(aService, mwgHeader, baseURL);
		}
	}

	public void setAllHeadersPutMapping(Object serviceObject, String jsonBody){
		BaseService aService = (BaseService) serviceObject;
		if (aService.requestHeader instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendAllHeadersPutRequest(aService, mwgHeader, mwgBody, jsonBody);
		}
	}
	
	//-------------------------------------------------------------------------
	// Private Methods
	//-------------------------------------------------------------------------

	/**
	 * Construct a PUT / POST request.
	 * 
	 * @param serviceObject
	 * @param body
	 * @param jsonBody
	 * @param reqParams
	 */
	private void buildPostRequest(BaseService serviceObject, MWGBody body, String jsonBody, Map<String, String> reqParams, Map<String, String> queryParams) {
		MWGHeader header = (MWGHeader) serviceObject.requestHeader;
		setgenericHeader(header.getMap());
		
		String reqURL = buildURL(serviceObject, reqParams, queryParams);
		
		setPath(reqURL);
		setGenericBody(body.Body(jsonBody));
	}
	
	/**
	 * Construct a GET / DELETE request.
	 * 
	 * @param serviceObject
	 * @param reqParams
	 */
	private void buildGetRequest(BaseService serviceObject, Map<String, String> reqParams, Map<String, String> queryParams) {
		MWGHeader header = (MWGHeader) serviceObject.requestHeader;
		setgenericHeader(header.getMap());
		
		String reqURL = buildURL(serviceObject, reqParams, queryParams);
		
		setPath(reqURL);
	}
	
	/**
	 * Build the full URL. If the 'requestPath' property of the Service Object contains any placeholder text, replace with matching request parameters.
	 * <p>
	 * For Example:
	 * <p>
	 * If the requestPath contains a '{chainId}' placeholder, there should be a corresponding 'chainId' request parameter.
	 * 
	 * @param serviceObj
	 * @param reqParams
	 * @param queryParams
	 * @return {String}
	 */
	private String buildURL(BaseService serviceObj, Map<String, String> reqParams, Map<String, String> queryParams) {
		String path = serviceObj.requestPath;
		StringBuilder query = new StringBuilder();
				
		// Insert any Request Path parameters
		if ((reqParams != null) && !reqParams.isEmpty()) {
			if (reqParams.containsKey(MWGApplicationConstants.chainID)) {
				path = path.replace("{" + MWGApplicationConstants.chainID + "}", reqParams.get(MWGApplicationConstants.chainID));
			}
			
			if (reqParams.containsKey(MWGApplicationConstants.userID)) {
				path = path.replace("{" + MWGApplicationConstants.userID + "}", reqParams.get(MWGApplicationConstants.userID));
			}
			
			if (reqParams.containsKey(MWGApplicationConstants.storeID)) {
				path = path.replace("{" + MWGApplicationConstants.storeID + "}", reqParams.get(MWGApplicationConstants.storeID));
			}
		}
		
		// Build the query string, if there are any query parameters
		if ((queryParams != null) && (!queryParams.isEmpty())) {
			int len = queryParams.size();
			int cnt = 0;
			
			query.append("?");
			
			for (Entry<String, String> pair : queryParams.entrySet()) {
	            cnt++;
	            
	            query.append(pair.getKey() + "=" + pair.getValue());
	            
	            if (cnt < len) {
	            		query.append("&");
	            }
	        }
		
		} else {
			query.setLength(0);
		}
		
		return (MWGApplicationConstants.baseURL + path + query);
	}

	private void sendAllHeadersPutRequest(BaseService serviceObject,MWGHeader header, MWGBody body, String jsonBody){
		header.authenticate(serviceObject.requestToken, ApplicationConstants.shoppingListItemPost.contentType, ApplicationConstants.shoppingListItemPost.contentAccept);
		setgenericHeader(header.getMap());
		setPath(ApplicationConstants.Requests.baseURLV5 + serviceObject.requestPath);
		setGenericBody(body.Body(jsonBody));
	}

	private void sendServiceMappingv1(BaseService serviceObject,MWGHeader header,MWGBody body, String jsonBody){
		header.v1Authentication(serviceObject.requestToken);
		setgenericHeader(header.getMap());
		setServicePath(ApplicationConstants.Requests.serviceURLV1 + serviceObject.requestPath);
		setGenericBody(body.Body(jsonBody));
	}
	
	private void sendCouponMapping(BaseService serviceObject, WakefernHeader wakefernHeader){
		wakefernHeader.cuponAuth(serviceObject.requestToken);
		setgenericHeader(wakefernHeader.getMap());
		setPath(serviceObject.requestPath);
	}
	
	private void sendRequestWithURL(BaseService serviceObject,MWGHeader header, String baseURL){
		header.authenticate(serviceObject.requestToken);
		setgenericHeader(header.getMap());
		setPath(baseURL + serviceObject.requestPath);
	}
}
