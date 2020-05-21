package com.EstimatedDeliveryDate.Entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.NoArgsConstructor;
import lombok.Setter;

/*Document object for the CutOffTimeList*/
@Setter
@NoArgsConstructor
@DynamoDBDocument
public class CutOffTime implements Serializable{
	private static final long serialVersionUID = 1L;
	private String cutoffTime;
	private String processingDay;

	@DynamoDBAttribute(attributeName = "CUTOFF_TIME")
	public String getCutoffTime() {
		return cutoffTime;
	}

	@DynamoDBAttribute(attributeName = "PROCESSING_DAY")
	public String getProcessingDay() {
		return processingDay;
	}


}
