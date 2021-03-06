package org.datum.configuration.impl;

import org.datum.configuration.Configurator;
import org.datum.dataproviders.DataProvider;
import org.datum.dataproviders.ResourceDataProvider;
import org.datum.datasource.GeneratorType;
import org.datum.datasource.impl.AddressDataSource;
import org.datum.datasource.impl.PersonDataSource;
import org.datum.datasource.impl.TrieDataSource;
import org.datum.datastructures.DataSchema;
import org.datum.datastructures.FieldSet;
import org.datum.processor.Pipeline;
import org.datum.processor.Processor;
import org.datum.processor.impl.SimpleOrderConverter;
import org.datum.processor.impl.SimpleTypeConverter;
import org.datum.util.CSVLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InMemoryConfigurator extends Configurator {

	private String[] usLocationMergePath = new String[] { "state", "city", "zip_code", "area_codes", "latitude", "longitude"};
	private String[] caLocationMergePath = new String[] { "province", "city", "zip_code" };

	private String[] firstNameMergePath = new String[] { "gender", "first_name" };

	public InMemoryConfigurator() throws Exception {
		
		// location datasource
		
		DataSchema schema = new DataSchema(6);
		schema.addType("state", "java.lang.String", 0);
		schema.addType("city", "java.lang.String", 1);
		schema.addType("zip_code", "java.lang.String", 2);
		schema.addType("area_codes", "java.lang.String", 3);
		schema.addType("latitude", "java.lang.Double", 4);
		schema.addType("longitude", "java.lang.Double", 5);

		Processor<FieldSet> typeConverter = new SimpleTypeConverter(schema);
		Processor<FieldSet> orderConverter = new SimpleOrderConverter(usLocationMergePath);

		DataProvider rdp = new ResourceDataProvider("us_locations.csv");
		CSVLoader loader = new CSVLoader();


		TrieDataSource locationDS = new TrieDataSource("us_locations", schema);


		Pipeline<FieldSet> pipe = new Pipeline.Builder<FieldSet>()
				.addConsumer(locationDS)
				.addProcessor(typeConverter)
				.addProcessor(orderConverter)
				.build();

		loader.loadCSV(rdp, schema, pipe, true);
		log.info("datasource {} updated {} times", locationDS.getName(), locationDS.getCounter());
		log.info("unique nodes {}", locationDS.countNodes());

		register("location:us", GeneratorType.TRIE, locationDS);
		
		// addresses source
		DataProvider addresses = new ResourceDataProvider("key_words.txt");
		register("location:any", GeneratorType.RANDOM_ADDRESS_STRING, new AddressDataSource(addresses));

		
		// first name datasource
		DataSchema schemaName = new DataSchema(6);
		schemaName.addType("gender", "org.datum.model.Gender", 0);
		schemaName.addType("first_name", "java.lang.String", 1);

		Processor<FieldSet> typeConverterName = new SimpleTypeConverter(schemaName);
		Processor<FieldSet> orderConverterName = new SimpleOrderConverter(firstNameMergePath);

		DataProvider rdpName = new ResourceDataProvider("names.csv");


		TrieDataSource namesDS = new TrieDataSource("names", schemaName);


		pipe = new Pipeline.Builder<FieldSet>()
				.addConsumer(namesDS)
				.addProcessor(typeConverterName)
				.addProcessor(orderConverterName)
				.build();

		loader.loadCSV(rdpName, schemaName, pipe, true);
		log.info("datasource {} updated {} times", namesDS.getName(), namesDS.getCounter());
		log.info("unique nodes {}", namesDS.countNodes());

		register("names", GeneratorType.TRIE, namesDS);
		

		// last name datasource
		
		register("person_data", GeneratorType.PERSONAL_DATA, new PersonDataSource());

	}

}
