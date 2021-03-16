package org.datum.datasource.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.datum.util.Timer;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LastNameGeneratorTest {

	@Test
	public void functionalTest() {
		String name1 = LastNameGenerator.generateNameMarkovChainAlgorithm1();
		log.debug("alg 1: {}", name1);

	}

	@Test
	public void performanceTest() {
		Timer t1 = new Timer();
		for (int i = 0; i < 10; i++) {
			String name1 = LastNameGenerator.generateNameMarkovChainAlgorithm1();
			log.debug("alg 1: {}", name1);
		}
		t1.end();

	}

	@Test
	public void testClean1() {
		StringBuilder sb = new StringBuilder();
		sb.append("orr");
		LastNameGenerator.clean(sb);
		assertEquals("or", sb.toString());
	}

	@Test
	public void testClean2() {
		StringBuilder sb = new StringBuilder();
		sb.append("vvw");
		LastNameGenerator.clean(sb);
		assertEquals("vw", sb.toString());
	}

	@Test
	public void testClean3() {
		StringBuilder sb = new StringBuilder();
		sb.append("zz");
		LastNameGenerator.clean(sb);
		assertEquals("z", sb.toString());
	}
	
	@Test
	public void testClean4() {
		StringBuilder sb = new StringBuilder();
		sb.append("ozzy");
		LastNameGenerator.clean(sb);
		assertEquals("ozy", sb.toString());
	}

	@Test
	public void testClean5() {
		StringBuilder sb = new StringBuilder();
		sb.append("zzoo");
		LastNameGenerator.clean(sb);
		assertEquals("zoo", sb.toString());
	}


	@Test
	public void testClean6() {
		StringBuilder sb = new StringBuilder();
		LastNameGenerator.clean(sb);
	}

	@Test
	public void testSetFirstToCapital1() {
		StringBuilder sb = new StringBuilder();
		sb.append("abcd");
		List<Integer> toCapitilize = Arrays.asList(0,2);
		LastNameGenerator.setFirstToCapital(sb, toCapitilize );
		assertEquals('A', sb.charAt(0));
		assertEquals('C', sb.charAt(2));
	}
	
	@Test
	public void testSetFirstToCapital2() {
		StringBuilder sb = new StringBuilder();
		sb.append("ab");
		List<Integer> toCapitilize = Arrays.asList(0,2);
		LastNameGenerator.setFirstToCapital(sb, toCapitilize );
		assertEquals('A', sb.charAt(0));
	}
	
	@Test
	public void testSetFirstToCapital3() {
		StringBuilder sb = new StringBuilder();
		List<Integer> toCapitilize = Arrays.asList(0,2);
		LastNameGenerator.setFirstToCapital(sb, toCapitilize);
		
	}



}