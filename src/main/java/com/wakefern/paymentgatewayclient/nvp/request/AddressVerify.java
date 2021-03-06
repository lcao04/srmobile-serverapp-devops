/*
 *  Copyright (C) 2015 - Wakefern Food Corporation
 */
package com.wakefern.paymentgatewayclient.nvp.request;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.wakefern.paymentgatewayclient.nvp.domain.Constants;
import com.wakefern.paymentgatewayclient.nvp.util.Validator;

/**
 * AddressVerify request to confirm whether a postal address and postal code match those of the
 * specified Wakefern's Payment Gateway account holder.
 *
 * @author Wakefern Food Corp.
 * @version 1.0.0
 * @date 10/06/2015
 */
public final class AddressVerify implements Request {

	private static final long serialVersionUID = 2743462628384472729L;

	// Method value of this request
    private static final String METHOD_NAME = Constants.NVP_METHOD_ADDRESSVERIFY;

    // Map that holds name-value pair request values 
    private final Map<String, String> nvpRequest;

    // Map that holds name-value pair response values 
    private Map<String, String> nvpResponse;

    /**
     * 
     * @param email     Email address of a customer to verify. Must be 
     *                  valid email with maximum 255 single-byte characters
     * @param street    First line of the billing or shipping postal address to 
     *                  verify. The value of Street must match the first three 
     *                  single-byte characters of a postal address on file for 
     *                  the customer. Maximum string length: 35 
     *                  single-byte characters. Alphanumeric plus - , . ' # \
     * @param zip       Postal code to verify. To pass verification, the value 
     *                  of Zip must match the first five single-byte characters 
     *                  of the postal code of the verified postal address for 
     *                  the verified customer. Maximum string length: 16 
     *                  single-byte characters.
     * @throws IllegalArgumentException
     */
    public AddressVerify(String email, String street, String zip)
            								throws IllegalArgumentException {

        // validation 
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (email.length() > 255) {
            throw new IllegalArgumentException("Email can by max 255 characters long");
        }
        if (street == null) {
            throw new IllegalArgumentException("Street cannot be null");
        }
        if (street.length() > 35) {
            throw new IllegalArgumentException("Street cannot be longer than 35 characters");
        }
        
        String streetRegex = "^[0-9a-zA-Z\\s\\-,\\.'#\\\\]{1,35}$";
        Matcher streetMatcher = Pattern.compile(streetRegex).matcher(street);
        if (!streetMatcher.matches()) {
            throw new IllegalArgumentException("Street is invalid");
        }

        if (zip == null) {
            throw new IllegalArgumentException("Zip cannot be null");
        }
        if (zip.length() > 16) {
            throw new IllegalArgumentException("Zip cannot be longer than 16 characters");
        }

        nvpResponse = new HashMap<String, String>();
        nvpRequest  = new HashMap<String, String>();
        nvpRequest.put("VERSION", Constants.VERSION);
        nvpRequest.put("METHOD", METHOD_NAME);
        nvpRequest.put("EMAIL", email);
        nvpRequest.put("STREET", street);
        nvpRequest.put("ZIP", zip);
    }

    public Map<String, String> getNVPRequest() {
        return new HashMap<String, String>(nvpRequest);
    }

    public void setNVPResponse(Map<String, String> nvpResponse) {
        this.nvpResponse = new HashMap<String, String>(nvpResponse);
    }

    public Map<String, String> getNVPResponse() {
        return new HashMap<String, String>(nvpResponse);
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("Instance of AddressVerify class ");
        str.append("with the values as nvpRequest: " + nvpRequest.toString());
		str.append("; nvpResponse: " + nvpResponse.toString());
		return str.toString();
    }
}
