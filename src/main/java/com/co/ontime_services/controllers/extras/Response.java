package com.co.ontime_services.controllers.extras;

public class Response {
	
	private String status;
    
    private Object data;
     	
	public Response() {
		super();
	}

	public Response(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	/*private class Wrapper<T> {
    	
        private final List<T> result;

		public Wrapper(List<T> result) {
			super();
			this.result = result;
		}

		public List<T> getResult() {
			return result;
		}
    }*/

}
