package org.genio.demo.dao;

import junit.framework.Assert;

import org.genio.demo.CommonTest;
import org.genio.demo.dao.impl.SomeEntityDAOBean;
import org.genio.demo.entities.SomeEntity;
import org.genio.demo.exception.DemoNullParamJPAException;
import org.genio.demo.exception.DemoPersistJPAException;
import org.genio.demo.exception.DemoRemoveJPAException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeEntityDAOTest extends CommonTest{

	private SomeEntityDAOBean someEntityDAO = null;

	private static Integer testIdGet = 1;		//NAL
	private static Integer testIdDelete = 2;	//DEL
	
	static final Logger log = LoggerFactory.getLogger(SomeEntityDAOTest.class);
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		someEntityDAO = new SomeEntityDAOBean();
		someEntityDAO.em = em;
	}
	
	@Test
	public void testSet() throws DemoNullParamJPAException, DemoPersistJPAException {
		
		SomeEntity someEntity = new SomeEntity();
		someEntity.setCode("NEW");
		someEntity.setName("NEW RECORD");
		someEntity.setDescription("New some entity record");
		
		someEntity = someEntityDAO.set(someEntity);
		log.info("SomeEntity: " + someEntity);
		someEntity = someEntityDAO.get("NEW");
		Assert.assertNotNull(someEntity);
		Assert.assertEquals("NEW", someEntity.getCode());
		Assert.assertEquals("NEW RECORD", someEntity.getName());
		Assert.assertEquals("New some entity record", someEntity.getDescription());
	}

	@Test
	public void testGet() {
		SomeEntity someEntity = someEntityDAO.get(testIdGet);
		log.info("SomeEntity: " + someEntity);
		Assert.assertNotNull(someEntity);
		Assert.assertEquals((Integer)1, someEntity.getId());
		Assert.assertEquals("CODE1", someEntity.getCode());
		Assert.assertEquals("NAME1", someEntity.getName());
		Assert.assertEquals("Description of the value", someEntity.getDescription());
	}

	@Test
	public void testGetByCode() {
		String code = "CODE1";
		SomeEntity someEntity = someEntityDAO.get(code);
		log.info("SomeEntity: " + someEntity);
		Assert.assertNotNull(someEntity);
		Assert.assertEquals((Integer)1, someEntity.getId());
		Assert.assertEquals("CODE1", someEntity.getCode());
		Assert.assertEquals("NAME1", someEntity.getName());
		Assert.assertEquals("Description of the value", someEntity.getDescription());
	}

	@Test
	public void testRemove() throws DemoNullParamJPAException, DemoRemoveJPAException {
		SomeEntity someEntity = someEntityDAO.get(testIdDelete);
		someEntityDAO.remove(someEntity);
		someEntity = someEntityDAO.get(testIdDelete);
		Assert.assertNull(someEntity);
	}

}
