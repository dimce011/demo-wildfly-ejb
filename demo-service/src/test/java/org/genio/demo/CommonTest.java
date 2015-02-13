package org.genio.demo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.hibernate.jdbc.Work;
import org.hibernate.jpa.HibernateEntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommonTest {
	
	static final Logger log = LoggerFactory.getLogger(CommonTest.class);
	
	protected static EntityManagerFactory emf = null;
	protected static EntityManager em = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("demo-hsqldb");
		prepareData();
	}
	
	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}
	
	public static void prepareData() throws DatabaseUnitException, SQLException {
		log.info("prepareData");

		DataFileLoader loader = new FlatXmlDataFileLoader();
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		((FlatXmlDataFileLoader) loader).setBuilder(builder);
		builder.setColumnSensing(true);
		
		//Operational database (mPlatform)
		EntityManager prepareEm = emf.createEntityManager();
		prepareEm.getTransaction().begin();
		IDatabaseConnection connection = new DatabaseConnection(getConnection(prepareEm));
		
		setReferentialIntegrity(false, connection);
		
		connection.getConfig().setProperty("http://www.dbunit.org/properties/datatypeFactory", new HsqldbDataTypeFactory());
		connection.getConfig().setProperty("http://www.dbunit.org/features/qualifiedTableNames", false);
		IDataSet dataSet = loader.load("/data/dbunit_init.xml");
		InsertIdentityOperation.CLEAN_INSERT.execute(connection, dataSet);
		
		setReferentialIntegrity(true, connection);
		
		if (prepareEm.getTransaction().getRollbackOnly()) {
			prepareEm.getTransaction().rollback();
		} else {
			prepareEm.getTransaction().commit();
		}
		
	}
	
    /**
     * <p/>
     * Execute whatever statement is necessary to either defer or disable foreign
     * key constraint checking on the given database connection, which is used by
     * DBUnit to import datasets.
     *
     * @param con A DBUnit connection wrapper, which is used afterwards for dataset operations
     */
    protected static void setReferentialIntegrity(boolean referentialIntegrityEnable, IDatabaseConnection con) {
        try {
			con.getConnection().prepareStatement("SET DATABASE REFERENTIAL INTEGRITY " +referentialIntegrityEnable).execute(); // HSQL DB
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	private static Connection getConnection(EntityManager em) {
		final Connection[] c = new Connection[1];
		((HibernateEntityManager) em).getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				c[0] = connection;
			}
		});
		return c[0];
	}
	
	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().getRollbackOnly()) {
			em.getTransaction().rollback();
		} else {
			em.getTransaction().commit();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emf.close();
	}
}
