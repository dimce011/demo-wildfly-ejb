package org.genio.demo.services;

import java.util.List;

import org.genio.demo.CommonIT;
import org.genio.demo.dto.SomeEntityDTO;
import org.genio.demo.exception.DemoBusinessException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeBusinessManagerIT extends CommonIT {

	static final Logger log = LoggerFactory.getLogger(SomeBusinessManagerIT.class);

	private SomeBusinessManager someBusinessManager = null;

	@Before
	public void setUp() throws DemoBusinessException {
		super.setUp();
		try {

			//Application name, if not specified - the name of ear archive by default.
			final String appName = "demo";
			//Module name, if not specified - the name of ejb archive by default.
			final String moduleName = "demo-service";
			//We did not set any.
			final String distinctName = "";
			//Internal - Our - convention.
			final String beanName = SomeBusinessManager.class.getSimpleName();
			final String viewClassName = SomeBusinessManager.class.getName();

 			//EJB Client Settings			
/*			String jndiValue = "ejb:" + appName + "/" + moduleName
					+ "/" + distinctName + "/" + beanName + "!" + viewClassName;
			log.info("jndiValue: {}", jndiValue);
*/
			//Java Naming (JNDI) Settings
			String jndiValue = "" + appName + "/" + moduleName
					+ "/" + distinctName + "/" + beanName + "!" + viewClassName;

			//Let's do the lookup - Always the same
			someBusinessManager = (SomeBusinessManager) initialContext.lookup(jndiValue);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DemoBusinessException("Could not lookup SomeBusinessManager!");
		}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		someBusinessManager = null;
	}

	@Test
	public void testGetAllSomeEntityDTOs() {
		
		List<SomeEntityDTO> someEntityDTOs = someBusinessManager. getAllSomeEntityDTOs();
		log.info("All Some Entities: {}", someEntityDTOs);
		Assert.assertNotNull(someEntityDTOs);
		Assert.assertEquals(2, someEntityDTOs.size());
		SomeEntityDTO firstSomeEntityDTO = someEntityDTOs.get(0);
		log.info("Firts Some Entity: {}", firstSomeEntityDTO);	
		Assert.assertNotNull(firstSomeEntityDTO);
		Assert.assertEquals("CODE1", firstSomeEntityDTO.getCode());
		Assert.assertEquals("NAME1", firstSomeEntityDTO.getName());
		Assert.assertEquals("Description of the value", firstSomeEntityDTO.getDescription());
	}

}
