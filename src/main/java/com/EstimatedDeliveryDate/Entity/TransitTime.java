package com.EstimatedDeliveryDate.Entity;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.NoArgsConstructor;
import lombok.Setter;

/*EDD_Transit table item is mapped to TransitTime object*/

@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "EDD_TRANSIT")
public class TransitTime {
	private String shippingServiceId;
	private Integer transitTime;
	private List<Map<String, Map<String, Integer>>> transitTimeOverrideMap;

	@DynamoDBHashKey(attributeName = "SHIPPING_SERVICE_ID")
	public String getShippingServiceId() {
		return shippingServiceId;
	}

	@DynamoDBAttribute(attributeName = "TRANSIT_TIME")
	public Integer getTransitTime() {
		return transitTime;
	}

	@DynamoDBAttribute(attributeName = "TRANSIT_TIME_OVERRIDE_MAP")
	public List<Map<String, Map<String, Integer>>> getTransitTimeOverrideMap() {
		return transitTimeOverrideMap;
	}

}
