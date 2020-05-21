package com.EstimatedDeliveryDate.Service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.Holiday;
/*Contains the methods for adjusting Ship date,Pickup holidays,Transit time, Processing holidays and Delivery holidays*/
@Service
public class DeliveryDateEstimator {
	@Autowired
	private DateFormatter dateFormat;
	@Autowired
	private ServiceHolidays serviceHolidays;

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryDateEstimator.class);

	// Adjusts the ship date by checking pickup holidays,CPT pick up holidays and
	// CPT time
	public String adjustShipDate(String ShipDate, List<Holiday> holidayList, List<CutOffTime> cptList) {
		// String converted to Date
		Date shipdate = dateFormat.getDate(ShipDate);
		// Date converted day
		String shipday = dateFormat.getDay(shipdate);
		// checking whether date and day is holiday or not
		if (serviceHolidays.checkCptHolidays(cptList, shipday)
				|| serviceHolidays.checkPickUpHolidays(holidayList, shipdate)) {
			Long milliseconds = shipdate.getTime() + 1L * 24L * 60L * 60L * 1000L;
			LOGGER.info("adjusted shipdate by 1 day");
			// return the date in UTC format
			return dateFormat.getUtcString(milliseconds);
		}
		// If not a holiday CPT time is checked to adjust the date
		else {
			// String converted to Date
			Date utcshipdate = dateFormat.getUtcDate(ShipDate);
			// Getting CPT date of the day
			Date cptdate = serviceHolidays.getCptDate(cptList, dateFormat.getDateString(shipdate), shipday);
			// Comparing the dates to adjust the date
			if (utcshipdate.compareTo(cptdate) > 0) {
				Long milliseconds = shipdate.getTime() + 1L * 24L * 60L * 60L * 1000L;
				LOGGER.info("adjusted shipdate by 1 day");
				// return the date in UTC format
				return dateFormat.getUtcString(milliseconds);
			}
			LOGGER.info("incremented shipdate by 0");
			// return the date in UTC format
			return ShipDate;
		}

	}

	// Adjusts the date by checking pickup holidays and CPT pick up holidays
	public String adjustPickUpHoliday(String pickUpDate, List<Holiday> holidayList, List<CutOffTime> cptList) {
		// String converted to Date
		Date pickupdate = dateFormat.getDate(pickUpDate);
		// pickup date initialised to variable date
		Date date = pickupdate;
		// Date converted day
		String day = dateFormat.getDay(date);
		// for number of pickup holidays
		Integer h = 0;
		Long milliseconds = 0L;
		// checking pick up holidays untill working day is found
		while (serviceHolidays.checkPickUpHolidays(holidayList, date)
				|| serviceHolidays.checkCptHolidays(cptList, day)) {
			milliseconds = date.getTime() + 1L * 24L * 60L * 60L * 1000L;
			h += 1;
			date = new Date(milliseconds);
			day = dateFormat.getDay(date);
		}
		LOGGER.info("number of pickup " + h);
		// comparing dates to check whether pickup date is increased or not
		if (date.compareTo(pickupdate) == 0) {
			// return date in UTC format
			return pickUpDate;
		}
		// return date in UTC format
		return dateFormat.getUtcString(milliseconds);
	}

	// Adjusts the date with transit time and Processing holidays
	public String adjustTransitHoliday(String transitDate, Long transitTime, List<Holiday> holidayList) {
		Long holidays = 0L;
		// String converted to UTCDate
		Date date = dateFormat.getUtcDate(transitDate);
		// String converted to Date
		Date startdate = dateFormat.getDate(transitDate);
		// transit time added to the date
		Long milliseconds = date.getTime() + transitTime * 60L * 1000L;
		Date enddate = new Date(milliseconds);
		// checking number of processing holidays
		holidays = serviceHolidays.checkTransitHolidays(startdate, enddate, holidayList);
		milliseconds += holidays * 24L * 60L * 60L * 1000l;
		LOGGER.info("number of transit " + holidays);
		// returns the date in UTC format
		return dateFormat.getUtcString(milliseconds);

	}

	// Adjusts the delivery holidays by checking delivery days and delivery holidays
	public String adjustDeliveryDay(String deliveryDate, List<Holiday> holidayList, List<String> deliveryDays) {
		// String converted to Date
		Date deliverydate = dateFormat.getDate(deliveryDate);
		// delivery date initialised to variable date
		Date date = deliverydate;
		// Date converted day
		String day = dateFormat.getDay(date);
		Integer h = 0;
		Long milliseconds = 0L;
		// checking delivery holidays untill working day is found
		while (serviceHolidays.checkDeliveryHoliday(holidayList, date)
				|| serviceHolidays.checkDeliveryDayHoliday(deliveryDays, day)) {
			milliseconds = date.getTime() + 1L * 24L * 60L * 60L * 1000L;
			h += 1;
			date = new Date(milliseconds);
			day = dateFormat.getDay(date);
		}
		LOGGER.info("number of delivery " + h);
		if (date.compareTo(deliverydate) == 0)
			// returns the date
			return deliveryDate.substring(0, 10);
		else
			// returns the date
			return dateFormat.getDateString(date);

	}

}
