package org.datum.datasource;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Generator<T> {

	@SuppressWarnings("unchecked")
	protected <T> void set(Consumer<T> setter, String key, Map<String, Object> data) {
		setter.accept((T) data.get(key));
	}
	
	protected <T> void set(Consumer<T> setter, Supplier<T> dataSource) {
		setter.accept(dataSource.get());
	}


	public abstract T genData();
}
