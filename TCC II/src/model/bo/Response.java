package model.bo;

public class Response {
	private int sucess;
	private String message;
	
	public Response () {
		this.sucess = 0;
		this.message = "";
	}

	public int getSucess() {
		return sucess;
	}

	public void setSucess(int sucess) {
		this.sucess = sucess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
