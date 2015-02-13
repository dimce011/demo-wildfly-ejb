package org.genio.demo.hibernate;

import java.sql.Types;

import org.hibernate.dialect.HSQLDialect;

/**
 * Boolean support from HSQLDB and hibernate
 * 
 * @author nilic
 */
public class AdvancedHSQLDialect extends HSQLDialect {

	public AdvancedHSQLDialect() {

		super();
		
		registerColumnType(Types.BOOLEAN, "boolean");
		registerHibernateType(Types.BOOLEAN, "boolean");
	}

}