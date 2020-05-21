package com.EstimatedDeliveryDate.Repository;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.TransitTime;

public interface EstimatedDeliveryDateRepository {
	public Holidays getHolidays(String ServiceId);
	public CriticalPullTime getCPT(String ServiceId);
	public TransitTime getTransitTime(String ServiceId);

}
