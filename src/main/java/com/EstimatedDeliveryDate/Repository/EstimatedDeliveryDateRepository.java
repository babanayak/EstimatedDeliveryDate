package com.EstimatedDeliveryDate.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.TransitTime;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/*Repository to load the data from DyanmoDB table and map it to respective objects*/

@Repository
public class EstimatedDeliveryDateRepository implements CarrierDataService{
	@Autowired
	private DynamoDBMapper mapper;

	// DynamoDBMapper loads the item using serviceId and maps to Holiday object
	public Holidays getHolidays(String ServiceId) {
		return mapper.load(Holidays.class, ServiceId);
	}

	// DynamoDBMapper loads the item using serviceId and maps to CriticalPullTime
	// object
	public CriticalPullTime getCPT(String ServiceId) {
		return mapper.load(CriticalPullTime.class, ServiceId);
	}

	// //DynamoDBMapper loads the item using serviceId and maps to TransitTime
	// object
	public TransitTime getTransitTime(String ServiceId) {
		return mapper.load(TransitTime.class, ServiceId);
	}
}
