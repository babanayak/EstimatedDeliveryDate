package com.EstimatedDeliveryDate.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.EstimatedDate;
import com.EstimatedDeliveryDate.Entity.Holiday;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.ShipDetail;
import com.EstimatedDeliveryDate.Entity.TransitTime;
import com.EstimatedDeliveryDate.Repository.EstimatedDeliveryDateRepository;

/*Service class for calculating the loading the data form data layer and calculating the estimated delivery date*/
public class EstimatedDeliveryDateServiceImpl implements EstimatedDeliveryDateService {
	@Autowired
	private EstimatedDeliveryDateRepository repository;
	@Autowired
	private DeliveryDateEstimator dateEstimator;
	private EstimatedDate estimatedDate = new EstimatedDate();

	// Loads the Holidays from data layer
	public Holidays getHolidays(String serviceId) {
		return repository.getHolidays(serviceId);
	}

	// Loads the CPT time form data layer
	public CriticalPullTime getCPT(String serviceId) {
		return repository.getCPT(serviceId);
	}

	// Loads the TransitTime from data layer
	public TransitTime getTransitTime(String serviceId) {
		return repository.getTransitTime(serviceId);
	}

	// Loads the holiday list form data layer
	public List<Holiday> getHolidayList(String serviceId) {
		return repository.getHolidays(serviceId).getHolidayDatesList();
	}

	// Loads the cutoff time list form data layer
	public List<CutOffTime> getCPTList(String serviceId) {
		return repository.getCPT(serviceId).getProcessingDayCutoffList();
	}

	// Loads the delivery days list form data layer
	public List<String> getDeliveryDays(String serviceId) {
		return repository.getCPT(serviceId).getDeliveryDays();
	}

	// Loads the transit time from data layer iterating through the Transit time
	// list
	public Integer transitTime(String serviceId, String Source, String Destination) {
		TransitTime transitTime = repository.getTransitTime(serviceId);
		for (Map<String, Map<String, Integer>> transitMap : transitTime.getTransitTimeOverrideMap()) {
			for (Map.Entry<String, Map<String, Integer>> source_location : transitMap.entrySet()) {
				// If source location found looks for the destination location
				if (Source.equals(source_location.getKey())) {
					for (Map.Entry<String, Integer> destination_location : source_location.getValue().entrySet()) {
						if (Destination.equals(destination_location.getKey())) {
							return destination_location.getValue();
						}
					}
				}
			}
		}
		// if locations are not found return default transit time
		return transitTime.getTransitTime();
	}

	// calculates the estimated delivery date by calling the DeliveryDateEstimator
	public EstimatedDate getEstimatedDeliveryDate(ShipDetail shipDetail) {
		// getting holiday list
		List<Holiday> holidayList = getHolidayList(shipDetail.getServiceId());
		// getting CPTList
		List<CutOffTime> cptList = getCPTList(shipDetail.getServiceId());
		// getting delivery days
		List<String> deliveryDays = getDeliveryDays(shipDetail.getServiceId());
		// getting transit time
		Long transittime = (long) transitTime(shipDetail.getServiceId(), shipDetail.getShipFrom(),
				shipDetail.getShipTo());
		// calling DeliverydateEstimator to adjust the ship date
		String shipDate = dateEstimator.adjustShipDate(shipDetail.getShipDate(), holidayList, cptList);
		// calling DeliverydateEstimator to adjust the pickup date
		String pickupDate = dateEstimator.adjustPickUpHoliday(shipDate, holidayList, cptList);
		// calling DeliverydateEstimator to adjust the transit date
		String transitDate = dateEstimator.adjustTransitHoliday(pickupDate, transittime, holidayList);
		// calling DeliverydateEstimator to adjust the delivery date
		String deliveryDate = dateEstimator.adjustDeliveryDay(transitDate, holidayList, deliveryDays);
		// setting the result with response body
		estimatedDate.setServiceId(shipDetail.getServiceId());
		estimatedDate.setShipDate(shipDetail.getShipDate());
		estimatedDate.setShipFrom(shipDetail.getShipFrom());
		estimatedDate.setShipTo(shipDetail.getShipTo());
		estimatedDate.setEstimatedDeliveryDate(deliveryDate);
		return estimatedDate;
	}

}
