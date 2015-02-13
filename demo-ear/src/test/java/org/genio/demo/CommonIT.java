package org.genio.demo;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.genio.demo.exception.DemoBusinessException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommonIT {

	final static Logger log = LoggerFactory.getLogger(CommonIT.class);
	protected InitialContext initialContext = null;
	private static Properties data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws DemoBusinessException {
		try {
			
			Properties jndiProperties = new Properties();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			initialContext = new InitialContext(jndiProperties);

//			initialContext = new InitialContext();
		    
		} catch (NamingException e) {
			throw new DemoBusinessException(e);
		}
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private Properties getData() throws Exception {
		if (data == null) {
			data = new Properties();
			InputStream stream = CommonIT.class.getResourceAsStream("/data.properties");
			data.load(stream);
		}
		return data;
	}

	public class DataBaseCommunication {

		private Connection con;

		private Connection connectToDB() throws Exception {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {

				}
			}
			Class.forName("com.mysql.jdbc.Driver");

			String userName = getData().getProperty("user");
			String password = getData().getProperty("password");
			String url = getData().getProperty("connString");
			con = DriverManager.getConnection(url, userName, password);

			return con;
		}

		private ResultSet executeQuery(String query) throws Exception {
			connectToDB();
			Statement s1 = con.createStatement();
			ResultSet rs = s1.executeQuery(query);

			return rs;
		}

		private void closeConnection() throws SQLException {
			con.close();
		}

		/**
		 * This method returns ArrayList<ArrayList<Object>>. It should be
		 * overridden if you want different reading of result set
		 * 
		 * @param resultSet
		 * @return
		 * @throws SQLException
		 */
		protected Object handleResultSet(ResultSet resultSet) throws SQLException {
			ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
			while (resultSet.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					row.add(resultSet.getObject(i));
				}
				rows.add(row);
			}

			return rows;
		}

		/**
		 * By default this method returns ArrayList<ArrayList<Object>> which
		 * represent table result from DB. If you want to get other result
		 * please override
		 * "protected Object handleResultSet(ResultSet resultSet)".
		 * 
		 * @param query
		 * @return
		 * @throws Exception
		 */
		public Object getResult(String query) throws Exception {
			ResultSet rs = executeQuery(query);
			Object ob = handleResultSet(rs);
			closeConnection();
			return ob;
		}

		/**
		 * Execute query from params and it return String from first column in
		 * first row of resultset.
		 * 
		 * @param query
		 * @return
		 * @throws Exception
		 */
		public String executeQueryGetString(String query) throws Exception {
			String s = null;
			ResultSet rs = executeQuery(query);
			if (rs.next()) {
				s = rs.getString(1);
			}
			closeConnection();
			return s;
		}

		/**
		 * Execute query from params and it return BigDecimal from first column
		 * in first row of resultset.
		 * 
		 * @param query
		 * @return
		 * @throws Exception
		 */
		public BigDecimal executeQueryGetBigDecimal(String query) throws Exception {
			BigDecimal d = null;
			ResultSet rs = executeQuery(query);
			if (rs.next()) {
				d = rs.getBigDecimal(1);
			}
			closeConnection();
			return d;
		}
	}
}
