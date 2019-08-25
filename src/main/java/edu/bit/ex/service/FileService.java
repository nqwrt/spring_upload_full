package edu.bit.ex.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface FileService {
	public void insertFileUpload(Map<String, Object> map, HttpServletRequest request) throws Exception;
}
