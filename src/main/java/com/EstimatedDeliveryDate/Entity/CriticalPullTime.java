package com.EstimatedDeliveryDate.Entity;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.NoArgsConstructor;
import lombok.Setter;

/*EDD_CPT table item is mapped to CriticalPullTime object*/
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "EDD_CPT")
public class CriticalPullTime {
	private String shippingServiceId;
	private List<CutOffTime> processingDayCutoffList;
	private List<String> deliveryDays;

	@DynamoDBHashKey(attributeName = "SHIPPING_SERVICE_ID")
	public String getShippingServiceId() {
		return shippingServiceId;
	}
	@DynamoDBAttribute(attributeName = "PROCESSING_DAY_CUTOFF_LIST")
	public List<CutOffTime> getProcessingDayCutoffList() {
		return processingDayCutoffList;
	}
	@DynamoDBAttribute(attributeName = "DELIVERY_DAYS")
	public List<String> getDeliveryDays() {
		return deliveryDays;
	}

}
