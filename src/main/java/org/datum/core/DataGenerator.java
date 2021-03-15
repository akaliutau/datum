package org.datum.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.datum.configuration.Configurator;
import org.datum.generator.Generator;
import org.datum.reflection.AnnotationProcessors;
import org.datum.reflection.ReflectionHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * The core class implementing the main functionality and responsible for
 * run-time configuration
 * 
 * @author akaliutau
 *
 * @param <T>
 */
@Slf4j
public class DataGenerator<T> {

	private final Configurator configurator;
	private Generator<?> generator = null;

	public DataGenerator(Configurator configurator) {
		this.configurator = configurator;
	}

	/**
	 * Adds new generator to the list, injects necessary DataSources from
	 * configurator if needed
	 * 
	 * @param addressGenerator
	 */
	public void addGenerator(Generator<?> generator) {
		
		Class<?> clazz = generator.getClass();
		List<Field> fields = ReflectionHelper.getAllFields(generator);
		for (Field field : fields) {
			AnnotationProcessors.processWireAnnotation(generator, configurator, field);
		}
		this.generator = generator;
		log.debug("added class {}", clazz);
	}

	@SuppressWarnings("unchecked")
	public T getOne() {
		return (T) generator.setData();
	}

	@SuppressWarnings("unchecked")
	public List<T> getBatch(int count) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			list.add((T) generator.setData());
		}
		return list;
	}
}