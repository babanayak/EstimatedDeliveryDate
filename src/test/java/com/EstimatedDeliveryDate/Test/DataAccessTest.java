package com.EstimatedDeliveryDate.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.EstimatedDeliveryDate.Entity.CriticalPullTime;
import com.EstimatedDeliveryDate.Entity.CutOffTime;
import com.EstimatedDeliveryDate.Entity.Holiday;
import com.EstimatedDeliveryDate.Entity.Holidays;
import com.EstimatedDeliveryDate.Entity.TransitTime;
import com.EstimatedDeliveryDate.Repository.EstimatedDeliveryDateRepositoryImpl;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@ExtendWith(MockitoExtension.class)
public class DataAccessTest {
	private Holiday holiday1;
	private Holiday holiday2;
	private Holidays holidays;
	private CriticalPullTime cpt;
	private CutOffTime cutofftime1;
	private CutOffTime cutofftime2;
	private TransitTime transittime;
    private DynamoDBMapper mapper;
    @InjectMocks 
    private EstimatedDeliveryDateRepositoryImpl repository;

	@BeforeEach
	public void setUp() {
		mapper=mock(DynamoDBMapper.class);
		repository=new EstimatedDeliveryDateRepositoryImpl();
		repository.setMapper(mapper);
	}

	@Test
	public void testHoliday() {
		holiday1 = new Holiday();
		holiday2 = new Holiday();
		holidays = new Holidays();
		
		holiday1.setDeliveryHoliday(false);
		holiday1.setPickUpHoliday(true);
		holiday1.setProcessingHoliday(true);
		holiday1.setHolidayDate("2020-05-22");

		holiday2.setDeliveryHoliday(true);
		holiday2.setPickUpHoliday(false);
		holiday2.setProcessingHoliday(true);
		holiday2.setHolidayDate("2020-05-23");

		List<Holiday> holidaylist = new ArrayList<Holiday>();
		holidaylist.add(holiday1);
		holidaylist.add(holiday2);
		holidays.setHolidayDatesList(holidaylist);
		holidays.setShippingServiceId("ROYAL_MAIL_METHOD_1");
		
        when(mapper.load(Holidays.class,"ROYAL_MAIL_METHOD_1")).thenReturn(holidays);
        assertEquals(repository.getHolidays("ROYAL_MAIL_METHOD_1"),holidays);
	}

	@Test
	public void testCPT() {
		cpt = new CriticalPullTime();
		cutofftime1 = new CutOffTime();
		cutofftime2 = new CutOffTime();
		
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
       
		when(mapper.load(CriticalPullTime.class,"ROYAL_MAIL_METHOD_1")).thenReturn(cpt);
		assertEquals(repository.getCPT("ROYAL_MAIL_METHOD_1"),cpt);

	}

	@Test
	public void testTransitTime() {
		transittime = new TransitTime();
		transittime.setShippingServiceId("ROYAL_MAIL_METHOD_1");
		transittime.setTransitTime(840);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("US", 450);
		map.put("IND", 360);
		Map<String, Map<String, Integer>> transitmap = new HashMap<String, Map<String, Integer>>();
		transitmap.put("AUS", map);
		List<Map<String, Map<String, Integer>>> transitoverridemap = new ArrayList<Map<String, Map<String, Integer>>>();
		transitoverridemap.add(transitmap);

		when(mapper.load(TransitTime.class,"ROYAL_MAIL_METHOD_1")).thenReturn(transittime);
		assertEquals(repository.getTransitTime("ROYAL_MAIL_METHOD_1"),transittime);
	}
}
