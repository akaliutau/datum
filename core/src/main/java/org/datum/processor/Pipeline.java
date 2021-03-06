package org.datum.processor;

import java.util.ArrayList;
import java.util.List;

/**
 * The structure implementing Pipeline pattern
 * 
 * Used for chain data processing
 *  
 * @author akaliutau
 *
 * @param <T>
 */
public class Pipeline<T> {

	// final consumer of the data
	private DataConsumer<T> dataConsumer;
	private final List<Processor<T>> processors = new ArrayList<>();

	private Pipeline() {

	}

	public void addProcessor(Processor<T> processor) {
		processors.add(processor);
	}

	public void setDataConsumer(DataConsumer<T> dataConsumer) {
		this.dataConsumer = dataConsumer;
	}

	public void process(T record) {
		for (Processor<T> proc : processors) {
			record = proc.proceed(record);
		}
		dataConsumer.insert(record);
	}

	public static class Builder<T> {

		private Pipeline<T> pipeline = null;

		public Builder() {
			pipeline = new Pipeline<>();
		}

		public Builder<T> addProcessor(Processor<T> processor) {
			pipeline.addProcessor(processor);
			return this;
		}

		public Builder<T> addConsumer(DataConsumer<T> dataConsumer) {
			pipeline.setDataConsumer(dataConsumer);
			return this;
		}

		public Pipeline<T> build() {
			return pipeline;
		}

	}

}
