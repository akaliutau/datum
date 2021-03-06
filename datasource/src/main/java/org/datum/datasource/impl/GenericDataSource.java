package org.datum.datasource.impl;

import org.datum.datastructures.FieldSet;
import org.datum.processor.DataConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * Added for debugging purposes only
 * @author akaliutau
 *
 */
@Slf4j
public class GenericDataSource implements DataConsumer<FieldSet> {
	
	private long counter = 0l;

	@Override
	public void insert(FieldSet record) {
		// do nothing - used for testing
		log.debug("consume {}", record);
		counter ++;
	}
	
	@Override
	public long getCounter() {
		return counter;
	}

}
