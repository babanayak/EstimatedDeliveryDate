package com.EstimatedDeliveryDate.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*Java object to extract details from request body*/
@Getter
@Setter
@NoArgsConstructor
public class ShipDetail {
	private String shipDate;
	private String shipFrom;
	private String shipTo;
	private String serviceId;	
}
