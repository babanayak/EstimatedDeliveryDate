package com.EstimatedDeliveryDate.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*Java object to give response to Request API*/

@Getter
@Setter
@NoArgsConstructor
public class EstimatedDate {
	private String shipDate;
	private String shipFrom;
	private String shipTo;
	private String serviceId;
	private String estimatedDeliveryDate;
}
