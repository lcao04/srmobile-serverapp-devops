package com.wakefern.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wakefern.global.errorHandling.ResponseHandler;

public class HTTPRequest {

	private final static Logger logger = Logger.getLogger("HTTPRequest");
	private static int timeOutInt = 30000; // 30 seconds time out

	//-------------------------------------------------------------------------
	// Public Methods
	//-------------------------------------------------------------------------

	/**
	 * Trigger HTTP GET request.
	 * 
	 * @param requestURL
	 * @param requestHeaders
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String executeGet(String requestURL, Map<String, String> requestHeaders, int timeOut) throws Exception {
		return executeRequest(requestURL, requestHeaders, null, "GET", timeOut);
	}
	
	/**
	 * Trigger HTTP PUT request.
	 * 
	 * @param requestURL
	 * @param requestBody
	 * @param requestHeaders
	 * @return
	 * @throws Exception
	 */
	public static String executePut(String requestURL, String requestBody, Map<String, String> requestHeaders) throws Exception {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");

			if (requestBody != null) {
				// Set Content length
				connection.setRequestProperty("Content-length", requestBody.getBytes().length + "");
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setConnectTimeout(timeOutInt);
				connection.setReadTimeout(timeOutInt);

				for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}

				// Set JSON as body of request
				OutputStream oStream = null;
				try {
					oStream = connection.getOutputStream();
					oStream.write(requestBody.getBytes("UTF-8"));
				}finally {
					if(oStream != null) {
						try {
							oStream.close();
						} catch(Exception ex) {
    							logger.log(Level.SEVERE, "[HTTPRequest]::executePut::Exception close stream: " + ex.getMessage());
						}
					}
				}
			}

			// Connect to the server
			connection.connect();

			// Handle the response
			int responseCode = connection.getResponseCode();
			String response  = ResponseHandler.Response(connection);

			if (responseCode == 200 || responseCode == 201 || responseCode == 204 || responseCode == 205 || responseCode == 206) {
				return response;
			
			} else {
				logger.log(Level.INFO, "[executePut]::response code: "+responseCode+", msg: "+response+", URL: "+requestURL);

				String msg;
				
				if (response.length() > 0) {
					msg = responseCode + "," + response;
				} else {
					msg = responseCode + "," + connection.getResponseMessage();
				}
				
				throw new Exception(msg);
			}

		} catch (MalformedURLException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePut]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (IOException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePut]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (Exception ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePut]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {
					logger.log(Level.SEVERE, "[executePut]::Exception closing connection, URL: "+ requestURL);
					throw ex;
				}
			}
		}
	}

	/**
	 * Trigger HTTP POST request, with timeout=30 seconds. 
	 * 
	 * @param requestURL
	 * @param requestBody
	 * @param requestHeaders
	 * @return
	 * @throws Exception
	 */
	public static String executePost(String requestURL, String requestBody, Map<String, String> requestHeaders) throws Exception {
		return executePost(requestURL, requestBody, requestHeaders, timeOutInt);
	}
	

	/**
	 * Trigger HTTP POST request, with timeout as input, currently being used by create order api.
	 * 	The create order api, in some cases, requires more than 30 seconds to return success response from MWG;
	 * 	therefore setting timeout = 40 seconds for create order api
	 * 
	 * @param requestURL
	 * @param requestBody
	 * @param requestHeaders
	 * @return
	 * @throws Exception
	 */
	public static String executePost(String requestURL, String requestBody, Map<String, String> requestHeaders, int timeout) throws Exception {

		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			if (requestBody != null) {
				// Set Content length
				connection.setRequestProperty("Content-length", requestBody.getBytes().length + "");
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setConnectTimeout(timeout);
				connection.setReadTimeout(timeout);
				
				for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}

				// Set JSON as body of request
				OutputStream oStream = null;
				try {
					oStream = connection.getOutputStream();
					oStream.write(requestBody.getBytes("UTF-8"));
				} finally {
					if(oStream != null) {
						try {
							oStream.close();
						} catch(Exception ex) {
							logger.log(Level.SEVERE, "[HTTPRequest]::executePost::Exception close stream: " + ex.getMessage());
						}
					}
				}
			}

			// Connect to the server
			connection.connect();

			// Handle the response
			int responseCode = connection.getResponseCode();
			String response  = ResponseHandler.Response(connection);

			if (responseCode == 200 || responseCode == 201 || responseCode == 204 || responseCode == 205 || responseCode == 206) {
				return response;
			
			} else {
				logger.log(
					Level.INFO, 
					"[executePost]::response code: {0}, msg: {1}, URL: {2}",
					new Object[] { responseCode, response }
				);

				String msg;
				
				if (response.length() > 0) {
					msg = responseCode + "," + response;
				} else {
					msg = responseCode + "," + connection.getResponseMessage();
				}
				
				throw new Exception(msg);
			}
		
		} catch (MalformedURLException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePost]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (IOException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePost]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (Exception ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePost]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));			
			throw ex;
		
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {
					logger.log(Level.SEVERE, "[executePost]::Exception closing connection, path: "+ requestURL);
					throw ex;
				}
			}
		}
	}

	/**
	 * Used by com.wakefern.coupons
	 * 
	 * @param requestURL
	 * @param requestBody
	 * @param requestHeaders
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String executePostJSON(String requestURL, String requestBody, Map<String, String> requestHeaders, int timeOut) throws Exception {
		HttpURLConnection connection = null;
		long startTime, endTime;
		
		try {
			startTime = System.currentTimeMillis();
			
			// Create connection
			URL url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			if (requestBody != null) {
				// Set Content length
				connection.setRequestProperty("Content-length", requestBody.getBytes().length + "");
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				timeOut = (timeOut == 0) ? timeOutInt : timeOut;
				logger.log(Level.INFO, "[executePostJSON]::Timeout", timeOut);
				connection.setConnectTimeout(timeOut);
				connection.setReadTimeout(timeOut);

				for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}

				// Set JSON as body of request
				OutputStream oStream = null;
				try{
					oStream = connection.getOutputStream();
					oStream.write(requestBody.getBytes("UTF-8"));
				} finally {
					if(oStream != null) {
						try {
							oStream.close();
						} catch(Exception ex) {
	    						logger.log(Level.SEVERE, "[HTTPRequest]::executePostJSON::Exception close stream: " + ex.getMessage());
						}
					}
				}
			}

			// Connect to the server
			connection.connect();

			int status = connection.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sb = new StringBuilder();

			switch (status) {
			case 200:
			case 201:
			case 204:
				// sb.append(status);
				int read;
				char[] chars = new char[1024];
				while ((read = br.read(chars)) != -1) {
					sb.append(chars, 0, read);
				}
				br.close();
				break;
			default:
				// sb.append(status);
				throw new Exception(connection.getResponseCode() + "," + connection.getResponseMessage());
			}
			
			endTime = System.currentTimeMillis();
			
			logger.log(Level.INFO, "[executePostJSON]::Total process time: "+(endTime - startTime)+" ms, path: "+requestURL);
			
			// return body to auth
			return sb.toString();

		} catch (MalformedURLException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePostJSON]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (IOException ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePostJSON]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} catch (Exception ex) {
			logger.log(Level.SEVERE, getErrorMsg("[executePostJSON]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw ex;
		
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {
					logger.log(Level.SEVERE, "[executePostJSON]::Exception closing connection: "+ex.getMessage()+", URL: "+requestURL);
					throw ex;
				}
			}
		}
	}

	/**
	 * Execute HTTP Request
	 * 
	 * @param requestURL
	 * @param requestHeaders
	 * @param requestParameters
	 * @param requestMethod
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String executeRequest(
		String requestURL, 
		Map<String, String> requestHeaders,
		Map<String, String> requestParameters, 
		String requestMethod, 
		int timeOut
	) throws Exception {
		
		HttpURLConnection connection = null;

		try {
			long startTime, endTime;
			
			startTime = System.currentTimeMillis();
			connection = createConnection(requestURL, requestHeaders, requestParameters, requestMethod, timeOut);
			int responseCode = connection.getResponseCode();
			endTime = System.currentTimeMillis();

			logger.log(
				Level.INFO, 
				"[executeRequest]::Total process time for "+requestMethod+": "+(endTime - startTime)+" ms, URL: "+requestURL);
			
			String response = ResponseHandler.Response(connection);

			if (responseCode == 200 || responseCode == 201 || responseCode == 204 || responseCode == 205 || responseCode == 206) {
				return response;
			
			} else {
				logger.log(
					Level.INFO, 
					"[executeRequest]::response code: {0}, msg: {1}, URL: {2}",
					new Object[] { connection.getResponseCode(), connection.getResponseMessage() }
				);

				String msg;
				
				if (response.length() > 0) {
					msg = responseCode + "," + response;
				} else {
					msg = responseCode + "," + connection.getResponseMessage();
				}
				
				throw new Exception(msg);
			}
		
		} catch (IOException e) {
			logger.log(Level.SEVERE, getErrorMsg("[executeRequest]::Exception: "+ e.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw e;
		
		} catch (URISyntaxException e) {
			logger.log(Level.SEVERE, getErrorMsg("[executeRequest]::Exception: "+ e.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
			throw e;
		
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {
					logger.log(Level.SEVERE, getErrorMsg("[executeRequest]::Exception: "+ ex.getMessage(), requestURL, connection.getResponseCode(), connection.getResponseMessage()));
					throw ex;
				}
			}
		}
	}

	public static String executeDelete(String requestURL, Map<String, String> requestHeaders, int timeOut) throws Exception {
		HttpURLConnection connection = null;
		long startTime, endTime;

		try {			
			startTime = System.currentTimeMillis();

			// Create connection
			URL url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			timeOut = timeOutInt;
			connection.setConnectTimeout(timeOut);
			connection.setReadTimeout(timeOut);

			for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
				connection.addRequestProperty(entry.getKey(), entry.getValue());
			}

			// Connect to the server
			connection.connect();

			int status = connection.getResponseCode();
			endTime = System.currentTimeMillis();

			logger.log(
					Level.INFO,
					"[executeDelete]::Total process time: {0} ms, URL: {1}, response code: {2}, msg: {3}",
					new Object[] { 
							(endTime - startTime), 
							requestURL, 
							connection.getResponseCode(),
							connection.getResponseMessage() 
						}
					);

			switch (status) {
				case 200:
				case 201:
				case 204:
					return status + " Success";
				case 205:
					return status + " - "+connection.getResponseMessage();
				default:
					throw new Exception(connection.getResponseCode() + "," + connection.getResponseMessage());
			}
		
		} catch (MalformedURLException ex) {
			logger.log(
					Level.SEVERE,
					"[executeDelete]::MalformedURLException: {0}, URL: {1}, response code: {2}, msg: {3}",
					new Object[] { 
							ex.getMessage(), 
							requestURL, 
							connection.getResponseCode(),
							connection.getResponseMessage() 
						}
					);
			
			throw ex;
		
		} catch (IOException ex) {
			logger.log(
					Level.SEVERE, 
					"[executeDelete]::IOException: {0}, URL: {1}, response code: {2}, msg: {3}",
					new Object[] { 
							ex.getMessage(), 
							requestURL, 
							connection.getResponseCode(),
							connection.getResponseMessage() 
						}
					);
			
			throw ex;
		
		} catch (Exception ex) {
			logger.log(
					Level.SEVERE, 
					"[executeDelete]::Exception: {0}, URL: {1}, response code: {2}, msg: {3}",
					new Object[] { 
							ex.getMessage(), 
							requestURL, 
							connection.getResponseCode(),
							connection.getResponseMessage() 
						}
					);
			
			throw ex;
		
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				
				} catch (Exception ex) {
					logger.log(
							Level.SEVERE, 
							"[executeDelete]::Exception: {0}, URL: {1}, response code: {2}, msg: {3}",
							new Object[] { 
									ex.getMessage(), 
									requestURL, 
									connection.getResponseCode(),
									connection.getResponseMessage() 
								}
							);
					
					throw ex;
				}
			}
		}
	}
	
	//-------------------------------------------------------------------------
	// Private Methods
	//-------------------------------------------------------------------------


	/**
	 * Return a formatted error message
	 * 
	 * @param msg
	 * @param url
	 * @param respCode
	 * @param respMsg
	 * @return
	 */
	private static String getErrorMsg(String msg, String url, int respCode, String respMsg) {
		return msg + ", url: " + url + ", respCode: " + respCode + ", respMsg: " + respMsg; 
	}
	   
	/**
	 * Create HTTP Connection.
	 * 
	 * @param requestURL
	 * @param requestHeaders
	 * @param requestParameters
	 * @param requestMethod
	 * @param timeOut
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static HttpURLConnection createConnection(
			String requestURL, 
			Map<String, String> requestHeaders,
			Map<String, String> requestParameters, 
			String requestMethod, 
			int timeOut) throws IOException, URISyntaxException {
		
		HttpURLConnection connection = null;
		URI uri = new URI(requestURL);
		
		if (requestParameters != null) {
			for (Map.Entry<String, String> entry : requestParameters.entrySet()) {
				uri = appendUri(uri.toString(), entry.getKey() + "=" + entry.getValue());
			}
		}

		URL url = uri.toURL();
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(requestMethod);
		
		if (timeOut == 0) {
			timeOut = timeOutInt;
		}
		
		connection.setConnectTimeout(timeOut);
		connection.setReadTimeout(timeOut);

		if (requestHeaders != null) {
			for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
				connection.addRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		// Connect to the server
		connection.connect();

		return connection;
	}

	/**
	 * Append query string to a URL.
	 * 
	 * @param uri
	 * @param appendQuery
	 * @return
	 * @throws URISyntaxException
	 */
	private static URI appendUri(String uri, String appendQuery) throws URISyntaxException {
		URI oldUri = new URI(uri);

		String newQuery = oldUri.getQuery();
		
		if (newQuery == null) {
			newQuery = appendQuery;
		} else {
			newQuery += "&" + appendQuery;
		}

		URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(), oldUri.getPath(), newQuery, oldUri.getFragment());

		return newUri;
	}
}
