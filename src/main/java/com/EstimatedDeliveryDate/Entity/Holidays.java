package com.EstimatedDeliveryDate.Entity;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.NoArgsConstructor;
import lombok.Setter;

/*EDD_HOLIDAYS table item is mapped to Holidays object*/
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "EDD_HOLIDAYS")
public class Holidays {
	private String shippingServiceId;
	private List<Holiday> holidayDatesList;

	@DynamoDBHashKey(attributeName = "SHIPPING_SERVICE_ID")
	public String getShippingServiceId() {
		return shippingServiceId;
	}

	@DynamoDBAttribute(attributeName = "HOLIDAY_DATES_LIST")
	public List<Holiday> getHolidayDatesList() {
		return holidayDatesList;
	}

}
