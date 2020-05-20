package com.EstimatedDeliveryDate.Entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.NoArgsConstructor;
import lombok.Setter;

/*Document object for the Holiday List*/

@Setter
@NoArgsConstructor
@DynamoDBDocument
public class Holiday  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String holidayDate;
	private Integer deliveryHoliday;
	private Integer pickUpHoliday;
	private Integer processingHoliday;
	
	@DynamoDBAttribute(attributeName = "HOLIDAY_DATE")
	public String getHolidayDate() {
		return holidayDate;
	}
	@DynamoDBAttribute(attributeName = "DELIVERY_HOLIDAY")
	public Integer getDeliveryHoliday() {
		return deliveryHoliday;
	}
	@DynamoDBAttribute(attributeName = "PICKUP_HOLIDAY")
	public Integer getPickUpHoliday() {
		return pickUpHoliday;
	}
	@DynamoDBAttribute(attributeName = "PROCESSING_HOLIDAY")
	public Integer getProcessingHoliday() {
		return processingHoliday;
	}
	

}
