package com.EstimatedDeliveryDate.Service;

import java.util.List;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.EstimatedDate;
import com.EstimatedDeliveryDate.Entity.Holiday;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.ShipDetail;
import com.EstimatedDeliveryDate.Entity.TransitTime;

/*common interface for Application service layer*/
public interface EstimatedDeliveryDateService {
	public Holidays getHolidays(String serviceId);

	public CriticalPullTime getCPT(String serviceId);

	public TransitTime getTransitTime(String serviceId);

	public List<Holiday> getHolidayList(String serviceId);

	public List<CutOffTime> getCPTList(String serviceId);

	public List<String> getDeliveryDays(String serviceId);

	public Integer transitTime(String serviceId, String Source, String Destination);

	public EstimatedDate getEstimatedDeliveryDate(ShipDetail shipDetail);

}
