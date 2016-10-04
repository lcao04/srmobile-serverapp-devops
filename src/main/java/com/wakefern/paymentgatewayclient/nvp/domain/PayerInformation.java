/*
 *  Copyright (C) 2015 - Wakefern Food Corporation
 */
package com.wakefern.paymentgatewayclient.nvp.domain;

import java.util.HashMap;
import java.util.Map;
import com.wakefern.paymentgatewayclient.nvp.util.Validator;

/**
 * Payer Information Fields
 *
 * @author Wakefern Food Corp.
 * @version 1.0.0
 * @date 10/06/2015
 */
public final class PayerInformation implements RequestFields {

	private static final long serialVersionUID = -1865577515433577800L;

	// Status of payer 
	public enum PayerStatus { verified, unverified }

    // Map that holds name-value pair request values 
    private final Map<String, String> nvpRequest;

    public PayerInformation() {
        this.nvpRequest = new HashMap<String, String>();
    }

    public Map<String, String> getNVPRequest() {
        return new HashMap<String, String>(nvpRequest);
    }

	/**
	 * Email address of payer. Email address has to be valid and cannot exceed
	 * 127 characters, otherwise exception is thrown.
	 *
	 * @param email  Character length and limitations: 127 single-byte characters.
	 * @throws IllegalArgumentException
	 */
	public void setEmail(String email) throws IllegalArgumentException {

        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (email.length() > 127) {
            throw new IllegalArgumentException("Email can be maximum 127 characters long.");
        }
        nvpRequest.put("EMAIL", email);
	}

	/**
     * Unique Payment Gateway customer account identification number.
	 * Character length and limitations: 13 single-byte alphanumeric characters.
	 *
	 * @param payerId
	 * @throws IllegalArgumentException
	 */
	public void setPayerId(String payerId) throws IllegalArgumentException {

        if (payerId == null || payerId.length() > 127) {
            throw new IllegalArgumentException("PayerId can be maximum 127 characters long.");
        }
        nvpRequest.put("PAYERID", payerId);
	}

	/**
	 * Sets status of payer
	 *
	 * @param status
	 */
	public void setPayerStatus(PayerStatus status) {
        nvpRequest.put("PAYERSTATUS", status.toString());
	}

	/**
	 * Payer's country of residence - Default set to "US"
	 *
	 * @param country
	 */
	public void setCountry() {
        nvpRequest.put("COUNTRYCODE", "US");
	}

	/**
	 * Payer's business name. Throws exception if name is null or exceeds 127 characters.
	 *
	 * @param name 	Character length and limitations: 127 single-byte characters
	 * @throws IllegalArgumentException
	 */
	public void setBusinessName(String name) throws IllegalArgumentException {

        if (name == null || name.length() > 127) {
            throw new IllegalArgumentException("Name can be maximum 127 characters long.");
        }
        nvpRequest.put("BUSINESS", name);
	}

    @Override
    public String toString() {
        return "Instance of PayerInformation class with the values: "
                + "nvpRequest: " + nvpRequest.toString();
    }
}
