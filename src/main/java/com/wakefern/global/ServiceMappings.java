package com.wakefern.global;

import com.wakefern.mywebgrocer.models.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceMappings {

	private String path;
	private String servicePath;
	private String genericBody;
	private Map<String, String> genericHeader;

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

	//Used for v5 authorization
	public void setMapping(Object serviceObject, String jsonBody) {
		BaseService aService = (BaseService) serviceObject;
		if (aService.serviceType instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendRequest((BaseService) serviceObject, mwgHeader, mwgBody, jsonBody);
		}
	}

	private void sendRequest(BaseService serviceObject,MWGHeader header,MWGBody body, String jsonBody){
		header.authenticate();
		setgenericHeader(header.getMap());
		setPath(ApplicationConstants.Requests.baseURLV5 + serviceObject.path);
		setGenericBody(body.Body(jsonBody));
	}

	//Used for GET and DELETE
	public void setMapping(Object serviceObject){
		BaseService aService = (BaseService) serviceObject;
		if (aService.serviceType instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			sendRequest(aService, mwgHeader);
		}
	}

	private void sendRequest(BaseService serviceObject,MWGHeader header){
		header.authenticate(serviceObject.token);
		setgenericHeader(header.getMap());
		setPath(ApplicationConstants.Requests.baseURLV5 + serviceObject.path);
	}

	//User for all PUT and POST methods
	public void setPutMapping(Object serviceObject, String jsonBody){
		BaseService aService = (BaseService) serviceObject;
		if (aService.serviceType instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendPutRequest(aService, mwgHeader, mwgBody, jsonBody);
		}
	}

	private void sendPutRequest(BaseService serviceObject,MWGHeader header, MWGBody body, String jsonBody){
		header.authenticate(serviceObject.token);
		setgenericHeader(header.getMap());
		setPath(ApplicationConstants.Requests.baseURLV5 + serviceObject.path);
		setGenericBody(body.Body(jsonBody));
	}

	public void setAllHeadersPutMapping(Object serviceObject, String jsonBody){
		BaseService aService = (BaseService) serviceObject;
		if (aService.serviceType instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendAllHeadersPutRequest(aService, mwgHeader, mwgBody, jsonBody);
		}
	}


	private void sendAllHeadersPutRequest(BaseService serviceObject,MWGHeader header, MWGBody body, String jsonBody){
		header.authenticate(serviceObject.token, ApplicationConstants.shoppingListItemPost.contentType,
				ApplicationConstants.shoppingListItemPost.contentAccept);
		setgenericHeader(header.getMap());
		setPath(ApplicationConstants.Requests.baseURLV5 + serviceObject.path);
		setGenericBody(body.Body(jsonBody));
	}

	//Used for v1 calls
	public void setServiceMapping(Object serviceObject, String jsonBody) {
		BaseService aService = (BaseService) serviceObject;
		if (aService.serviceType instanceof MWGHeader) {
			MWGHeader mwgHeader = new MWGHeader();
			MWGBody mwgBody = new MWGBody("");
			sendServiceMapping((BaseService) serviceObject, mwgHeader, mwgBody, jsonBody);
		}
	}

	private void sendServiceMapping(BaseService serviceObject,MWGHeader header,MWGBody body, String jsonBody){
		header.serviceAuth(serviceObject.token);
		//setgenericHeader(header.getMap());
		setgenericHeader(v1Map());
		setServicePath(ApplicationConstants.Requests.serviceURLV1 + serviceObject.path);
		setGenericBody(body.Body(jsonBody));
	}

	private Map<String, String> v1Map(){
		Map<String, String> map = new HashMap<>();
		map.put(ApplicationConstants.MapVariables.contentType, ApplicationConstants.MapVariables.requestType);
		map.put(ApplicationConstants.MapVariables.auth, ApplicationConstants.MapVariables.authToken);
		return map;
	}
}
