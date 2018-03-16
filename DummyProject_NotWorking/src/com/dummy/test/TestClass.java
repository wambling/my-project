package com.dummy.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.RecursiveAction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author priyam.srivastava
 *
 */
public class 
	TestClass 
extends
	RecursiveAction
{
	private static final Logger
		Logger = LogManager.getLogger(TestClass.class)
	;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String 
		threadName
	;
	
	public String getName() {
		return threadName;
	}
	private Context
		initialContext
	;
	
	private DataSource
		dataSource
	;
	
	private String 
		dataSourceName = "jdbc/MySqlDS" //Oracle DS->"jdbc/FincluezDS"
	;
	
	private String
		contextPrefix = "java:/comp/env/"
	;
	
	public TestClass(
		String p_threadName
	){
		threadName = p_threadName;
		
	}
	/* (non-Javadoc)
	 * @see java.util.concurrent.RecursiveAction#compute()
	 */
	@Override
	protected void compute() {
		Connection l_conn = null;
		try {
			Logger.info(threadName + "=> Getting connection.");
			//Class.forName ("oracle.jdbc.OracleDriver");
			Class.forName ("com.mysql.jdbc.Driver");
			initialContext	= new InitialContext();
			if(initialContext == null) {
				throw new Exception("No InitialContext found for::" + dataSourceName);
			}
			dataSource = (DataSource) initialContext.lookup(contextPrefix + dataSourceName);

			if ( dataSource == null ) {
			   throw new Exception("Data source not found-->" + dataSourceName);
			}
			l_conn = dataSource.getConnection();
			Logger.info(threadName + "=> Got connection. Going to sleep.");
			Thread.sleep(5000);
			Logger.info(threadName + "=> Awoke. Closing connection.");
		}catch(Exception e) {
			Logger.error("Exception occured for=> {}", threadName);
			Logger.error("{}:: Error Message=> {}", threadName, e.getMessage(), e);
		}finally {
			try {
				l_conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(threadName + "=> Exiting.");
		
	}
}
