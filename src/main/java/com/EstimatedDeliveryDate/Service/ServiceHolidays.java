package com.EstimatedDeliveryDate.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.Holiday;

/*Contains the methods to check whether a date or a day is holiday or not*/
@Service
public class ServiceHolidays {
	@Autowired
	private DateFormatter dateFormat;

	// Checks whether a day is CPT holiday or not by iterating through the
	// CutoffTime List
	public Boolean checkCptHolidays(List<CutOffTime> cptList, String shipDay) {
		for (CutOffTime cutofftime : cptList) {
			if (shipDay.equals(cutofftime.getProcessingDay())) {
				return false;
			}
		}
		return true;
	}

	// Checks whether a date is PickUpHoliday or not by iterating through the
	// HolidayList
	public Boolean checkPickUpHolidays(List<Holiday> holidayList, Date shipDate) {
		for (Holiday holiday : holidayList) {
			if ((dateFormat.getDate(holiday.getHolidayDate())).compareTo(shipDate) == 0
					&& holiday.getPickUpHoliday() == 1) {
				return true;
			}
		}
		return false;
	}

	// Gives the CutoffTime of a day by iterating through the CutoffTime List
	public Date getCptDate(List<CutOffTime> cptList, String shipDate, String day) {
		String cptdate = shipDate;
		for (CutOffTime cutofftime : cptList) {
			if ((cutofftime.getProcessingDay()).equals(day)) {
				cptdate += "T" + cutofftime.getCutoffTime() + ":00.000Z";
				break;
			}
		}
		return dateFormat.getUtcDate(cptdate);
	}

	// Checks whether a date is ProcessingHoliday or not by iterating through the
	// HolidayList
	public Long checkTransitHolidays(Date startDate, Date endDate, List<Holiday> holidayList) {
		Long holidays = 0L;
		for (Holiday holiday : holidayList) {
			if (startDate.compareTo(dateFormat.getDate(holiday.getHolidayDate())) <= 0
					&& endDate.compareTo(dateFormat.getDate(holiday.getHolidayDate())) >= 0
					&& holiday.getProcessingHoliday() == 1) {
				holidays += 1;
			}
		}
		return holidays;

	}

	// Checks whether a day is Delivery holiday or not by iterating through the
	// deliveryDays
	public Boolean checkDeliveryDayHoliday(List<String> deliveryDays, String day) {
		if (!(deliveryDays.contains(day))) {
			return true;
		}
		return false;
	}

	// Checks whether a date is DeliveryHoliday or not by iterating through the
	// HolidayList
	public Boolean checkDeliveryHoliday(List<Holiday> holidayList, Date shipDate) {
		for (Holiday holiday : holidayList) {
			if (dateFormat.getDate(holiday.getHolidayDate()).compareTo(shipDate) == 0
					&& holiday.getDeliveryHoliday() == 1)
				return true;
		}
		return false;
	}

}
