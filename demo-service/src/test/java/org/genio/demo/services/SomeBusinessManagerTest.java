package org.genio.demo.services;

import java.util.List;

import junit.framework.Assert;

import org.genio.demo.CommonTest;
import org.genio.demo.dao.SomeEntityDAO;
import org.genio.demo.dao.impl.SomeEntityDAOBean;
import org.genio.demo.entities.SomeEntity;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeBusinessManagerTest extends CommonTest {

	private SomeBusinessLocalManager someBusinessLocalManager = null;
	private SomeEntityDAO someEntityDAO = null;

	static final Logger log = LoggerFactory.getLogger(SomeBusinessManagerTest.class);

	@Before
	public void setUp() throws Exception {
		super.setUp();
		someEntityDAO = new SomeEntityDAOBean();
		((SomeEntityDAOBean)someEntityDAO).em = em;
		
		someBusinessLocalManager = new SomeBusinessManagerBean();
		((SomeBusinessManagerBean)someBusinessLocalManager).em = em;
		((SomeBusinessManagerBean)someBusinessLocalManager).someEntityDAO = someEntityDAO;
	}
	
	@Test
	public void testGetAllSomeEntities() {
		
		List<SomeEntity> someEntities = someBusinessLocalManager.getAllSomeEntities();
		Assert.assertNotNull(someEntities);
		Assert.assertEquals(2, someEntities.size());
		SomeEntity firstSomeEntity = someEntities.get(0);
		Assert.assertNotNull(firstSomeEntity);
		Assert.assertEquals("CODE1", firstSomeEntity.getCode());
		Assert.assertEquals("NAME1", firstSomeEntity.getName());
		Assert.assertEquals("Description of the value", firstSomeEntity.getDescription());
	}

}
