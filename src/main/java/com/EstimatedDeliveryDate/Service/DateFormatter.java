package com.EstimatedDeliveryDate.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*This class contains the methods to format the date*/

@Service
public class DateFormatter {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatter.class);

//Converts the String to Date format of "yyyy-MM-dd"
	public Date getDate(String shipdate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(shipdate);
			return date;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

//Converts the String to Date format of "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
	public Date getUtcDate(String shipdate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date date = formatter.parse(shipdate);

			return date;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

// Converts the date to day
	public String getDay(Date DayDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
		return formatter.format(DayDate).toUpperCase();

	}

//Converts the time in milliseconds to date in UTC format
	public String getUtcString(Long milliseconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = new Date(milliseconds);
		return formatter.format(date);

	}

//Converts the Date to String  
	public String getDateString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

}
