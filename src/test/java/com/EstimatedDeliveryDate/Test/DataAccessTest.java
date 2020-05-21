package com.EstimatedDeliveryDate.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.Holiday;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.TransitTime;

public class DataAccessTest {
	private Holiday holiday1;
	private Holiday holiday2;
	private Holidays holidays;
	private CriticalPullTime cpt;
	private CutOffTime cutofftime1;
	private CutOffTime cutofftime2;
	private TransitTime transittime;

	@Before
	public void setUp() {
		holiday1 = new Holiday();
		holiday2 = new Holiday();
		holidays = new Holidays();
		cpt = new CriticalPullTime();
		cutofftime1 = new CutOffTime();
		cutofftime2 = new CutOffTime();
		transittime = new TransitTime();
	}

	@Test
	public void testHoliday() {
		holiday1.setDeliveryHoliday(0);
		holiday1.setPickUpHoliday(1);
		holiday1.setProcessingHoliday(1);
		holiday1.setHolidayDate("2020-05-22");

		holiday2.setDeliveryHoliday(1);
		holiday2.setPickUpHoliday(0);
		holiday2.setProcessingHoliday(1);
		holiday2.setHolidayDate("2020-05-23");

		List<Holiday> holidaylist = new ArrayList<Holiday>();
		holidaylist.add(holiday1);
		holidaylist.add(holiday2);
		holidays.setHolidayDatesList(holidaylist);
		holidays.setShippingServiceId("ROYAL_MAIL_METHOD_1");

		assertEquals(holidays.getShippingServiceId(), "ROYAL_MAIL_METHOD_1");
		assertEquals(holidays.getHolidayDatesList().get(0).getHolidayDate(), "2020-05-22");
		assertEquals(holidays.getHolidayDatesList().get(1).getHolidayDate(), "2020-05-23");
		assertEquals(holidays.getHolidayDatesList().get(0).getDeliveryHoliday(), (Integer) 0);
		assertEquals(holidays.getHolidayDatesList().get(1).getPickUpHoliday(), (Integer) 0);
		assertEquals(holidays.getHolidayDatesList().get(0).getProcessingHoliday(), (Integer) 1);
	}

	@Test
	public void testCPT() {
		cutofftime1.setCutoffTime("20:00");
		cutofftime1.setProcessingDay("WEDNESDAY");

		cutofftime2.setCutoffTime("20:00");
		cutofftime2.setProcessingDay("THURSDAY");

		List<CutOffTime> cptlist = new ArrayList<CutOffTime>();
		cptlist.add(cutofftime1);
		cptlist.add(cutofftime2);
		List<String> deliverydaylist = new ArrayList<String>();
		deliverydaylist.add("MONDAY");
		deliverydaylist.add("THURSDAY");

		cpt.setDeliveryDays(deliverydaylist);
		cpt.setProcessingDayCutoffList(cptlist);
		cpt.setShippingServiceId("ROYAL_MAIL_METHOD_1");

		assertEquals(cpt.getShippingServiceId(), "ROYAL_MAIL_METHOD_1");
		assertEquals(cpt.getProcessingDayCutoffList().get(0).getProcessingDay(), "WEDNESDAY");
		assertEquals(cpt.getDeliveryDays().get(0), "MONDAY");

	}

	@Test
	public void testTransitTime() {
		transittime.setShippingServiceId("ROYAL_MAIL_METHOD_1");
		transittime.setTransitTime(840);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("US", 450);
		map.put("IND", 360);
		Map<String, Map<String, Integer>> transitmap = new HashMap<String, Map<String, Integer>>();
		transitmap.put("AUS", map);
		List<Map<String, Map<String, Integer>>> transitoverridemap = new ArrayList<Map<String, Map<String, Integer>>>();
		transitoverridemap.add(transitmap);

		assertEquals(transittime.getTransitTime(), (Integer) 840);
		assertEquals(transittime.getShippingServiceId(), "ROYAL_MAIL_METHOD_1");

	}
}
