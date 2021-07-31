package com.abc.DemoLogin.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import java.util.Map;

@Embeddable
public class BaseResponse {
	
	
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String Status;
	
    public String messageType;
   
	
    public String message;
	
    @Temporal(TIMESTAMP)
    public Date timeStamp;
    
    public String accessToken;
    
    
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/*public Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}*/

	public Map<String, Object> data;



	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	private Paging paging;

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

    public void error(String status, String message) {
		this.messageType = "Failure";
		this.message = message;
		this.Status = status;
    }

    public static class Paging {
		private long noOfPages;
		private long page;
		private int limit;
		private long count;

		public void setNoOfRecord(long noOfRecord) {
			this.count = noOfRecord;
			this.noOfPages = (int) (noOfRecord / limit) + (noOfRecord % limit == 0 ? 0 : 1);
		}

		public long getNoOfPages() {
			return noOfPages;
		}

		public long getPage() {
			return page;
		}

		public Paging setPage(long page) {
			if (page < 1)
				page = 1;
			this.page = page;
			return this;
		}

		public int getLimit() {
			return limit;
		}

		public Paging setLimit(int limit) {
			if (limit < 1)
				limit = 10;
			this.limit = limit;
			return this;
		}

		public long getCount() {
			return count;
		}
	}

}
