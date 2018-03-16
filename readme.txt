Issue Description
================================================
I am running 10 tasks using Java's Fork Join Framework. Each task opens and closes it's own connection using Tomcat's datasource. 

When deploying the code in Tomcat 8.5 and even 9.0.6 it throws error::

javax.naming.NoInitialContextException: Cannot instantiate class: org.apache.naming.java.javaURLContextFactory

Caused by: java.lang.ClassNotFoundException: org.apache.naming.java.javaURLContextFactory

But when I run the same tasks by creating 10 Threads and running them, then Tomcat throws no error.

This problems seems to appear only in Tomcat. Not facing this issue when running in Wildfly 11, Glassfish 5 and JBOSS 7 EAP.
================================================
Environment Details:

Java Version: 1.8
Tomcat Version: 8.5, 9.0.6
OS: Windows 10 Pro 64 bit
Database: Oracle 11g and MySQL 5.7
================================================
Datasource Configuration in server.xml

<GlobalNamingResources>
<Resource name="jdbc/MySqlDS" 
			  auth="Container" 
			  type="javax.sql.DataSource" 
			  driverClassName="com.mysql.jdbc.Driver" 
			  username="testUser" 
			  password="testPassword" 
			  url="jdbc:mysql://localhost:3306/test?useSSL=false" 
			  initialSize="5" 
			  maxTotal="20" 
			  defaultAutoCommit="false" 
			  factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
			  removeAbandonedOnBorrow="true"
			  removeAbandonedOnMaintenance="true"/>
</GlobalNamingResources>
=================================================
Resource Link in context.xml

I have updated the context.xml inside <TOMCAT_HOME>/conf folder. 

Issue still appears even if I place context.xml inside my META-INF folder.

<ResourceLink name="jdbc/MySqlDS"
                global="jdbc/MySqlDS"
                auth="Container"
                type="javax.sql.DataSource" />	
==================================================

Refer to Project=> DummyProject_NotWorking for issue with Fork Join Framework in Tomcat. Logs are present under folder logs/daily

Refer to Project=> DummyProject when Tomcat throws no exception if I use Thread class. Logs are present under folder logs/daily

War files are also present inside each folder to deploy on Tomcat and test.

Servlet is mapped to URL: "/runDummy"			
