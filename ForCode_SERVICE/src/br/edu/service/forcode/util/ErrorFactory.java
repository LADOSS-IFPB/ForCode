package br.edu.service.forcode.util;

import java.util.HashMap;
import java.util.Map;

import br.edu.commons.forcode.entities.ForCodeError;

public class ErrorFactory {

	private ErrorFactory() {
	}

	private static int index = 0;

	/*
	 * Error status relatives to users.
	 */
	public static final int DUPLICATE_USERNAME = index++;
	public static final int DUPLICATE_EMAIL = index++;
	public static final int USER_NOT_FOUND = index++;
	public static final int INSTITUTION_NOT_FOUND = index++;
	public static final int INCORRECT_PASSWORD = index++;
	
	public static final int USER_CONTEST_NOT_FOUND = index++;
	public static final int CONTEST_NOT_EXISTENT = index++;
	public static final int USER_CONTEST_ALREADY_REGISTERED = index++;
	
	public static final int PROBLEM_NOT_DELETABLE = index++;
	
	public static final int DUPLICATE_INSTITUTION_NAME = index++;

	public static final int FILE_TOO_LARGE = index++;

	public static final int UNZIP_ERROR = index++;
	
	public static final int DATA_NOT_DELETABLE = index++;
	
	private static final Map<Integer, String> mapErrors = generateErrorMapping();


	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(DUPLICATE_USERNAME, "Duplicate entry for username");
		hashMap.put(DUPLICATE_EMAIL, "Duplicate entry for email");
		hashMap.put(USER_NOT_FOUND, "User not registered");
		hashMap.put(INSTITUTION_NOT_FOUND, "Institution not registered");
		hashMap.put(INCORRECT_PASSWORD, "Incorrect password");
		hashMap.put(USER_CONTEST_NOT_FOUND, "User Contest not found");
		hashMap.put(CONTEST_NOT_EXISTENT, "There is no such contest");
		hashMap.put(USER_CONTEST_ALREADY_REGISTERED, "User Contest already registered");
		hashMap.put(DUPLICATE_INSTITUTION_NAME, "Institution already registered");
		hashMap.put(PROBLEM_NOT_DELETABLE, "Problem was used for one or more contests");
		hashMap.put(FILE_TOO_LARGE, "The file is too large for the requested operation");
		hashMap.put(UNZIP_ERROR, "THe unzip operation failed");
		hashMap.put(DATA_NOT_DELETABLE, "Error while trying to delete data, contact the server admins.");
		
		return hashMap;
	}

	public static final ForCodeError getErrorFromIndex(int index) {
		ForCodeError error = new ForCodeError();
		error.setCode(index);
		error.setMessage(mapErrors.get(index));
		return error;
	}
}
