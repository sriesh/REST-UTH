package com.restuth.entity;

import java.util.LinkedHashMap;

public class TaskManager {

	private String id;
	private String taskname;
	private LinkedHashMap<String, String> taskParam;
	private String status;
	private String message;
	private String resource;
	private String resultavailable;
	private String errorCode;
	private String errorText;

	public TaskManager() {
	}

	public TaskManager(String taskname, String success) {
		this.taskname = taskname;
		this.status = success;
	}

	// In case of Error
	public TaskManager(String taskname, String status, String errorCode,
			String errorText) {
		this.taskname = taskname;
		this.status = status;
		this.errorCode = errorCode;
		this.errorText = errorText;
	}

	// In case of Asynchronous processing
	public TaskManager(String taskname, String status, String message,
			String resource, String resultavailable) {
		this.taskname = taskname;
		this.status = status;
		this.message = message;
		this.resource = resource;
		this.resultavailable = resultavailable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedHashMap<String, String> getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(LinkedHashMap<String, String> taskParam) {
		this.taskParam = taskParam;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultavailable() {
		return resultavailable;
	}

	public void setResultavailable(String resultavailable) {
		this.resultavailable = resultavailable;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
