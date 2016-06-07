import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;


import com.google.gson.Gson;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;





import com.heroku.sdk.jdbc.DatabaseUrl;

public class Main 
{

  public static void main(String[] args) 
  {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

   

	get("/hello", (req, res) -> "Hello World GET");

	post("/hello", (req,res) -> "hello World POST");

	get("/", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          attributes.put("message", "Hello World!");

          return new ModelAndView(attributes, "index.ftl");
      }, new FreeMarkerEngine());


	    
	  


	get("/usersCreate", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();

		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users(username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50) NOT NULL, firstName VARCHAR(25) NOT NULL, lastName VARCHAR(25) NOT NULL, birthday DATE NOT NULL, gender VARCHAR(10) NOT NULL, CWP_finished INT NOT NULL DEFAULT 0, helpForDay INT NOT NULL DEFAULT 5)");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
   
	/////!!!!!!!!attention!!!!! the property "level" is active as "NOT NULL" if u want to make a change and fix it to "NULL" like it just written now u need to drop and create!!!!!
	get("/tashchezsCreate", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
		//dont have to fill "level" so need to check that with var
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tashchezs(idTashchez SERIAL NOT NULL PRIMARY KEY, status VARCHAR(10) NOT NULL, level INT, content VARCHAR(2000) NOT NULL)");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
   
    get("/definitionsCreate", (req, res) ->	{
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Definitions(idDefinition SERIAL NOT NULL PRIMARY KEY, definition VARCHAR(45) NOT NULL, solution VARCHAR(32) NOT NULL, solutionLength INT NOT NULL, level INT)");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
  
    get("/statusesCreate", (req, res) ->	{
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Statuses(idStatus SERIAL NOT NULL PRIMARY KEY, username VARCHAR(50) NOT NULL, content VARCHAR(500) NOT NULL, dateTime TIMESTAMP NOT NULL, type VARCHAR(20), index INT, comments VARCHAR(300))");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
	
	get("/commentsCreate", (req, res) ->	{
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Comments(idComment SERIAL NOT NULL PRIMARY KEY, username VARCHAR(50) NOT NULL, content VARCHAR(500) NOT NULL, dateTime TIMESTAMP NOT NULL)");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
	
   
  
  
  
  

	get("/userAdd", (req, res) -> {
		int i;
		String s[]={"","","","","",""};
		String arr[] = req.queryParams().toArray(new String[0]);
		List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		Map m = new HashMap<String, String>();
		
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
				s[0] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("password") == 0)
				s[1] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("firstName") == 0)
				s[2] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("lastName") == 0)
				s[3] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("birthday") == 0)
				s[4] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("gender") == 0)
				s[5] = req.queryParams(arr[i]);			
		}
			
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT INTO Users (username, password, firstName, lastName, birthday, gender) VALUES ('" + s[0] + "', '" + s[1] + "', '" + s[2] + "', '" + s[3] + "', '" + s[4] + "', '" + s[5] +"')");
		//return new ModelAndView(attributes, "db.ftl");
		m.put("GOOD2", "GOOD2: user added succeffuly");
		listOfMaps.add(m);
		return new Gson().toJson(listOfMaps);
		} catch (Exception e) 
		{
			
			m.put("EER2", "ERR2: cant add user" + e);
			listOfMaps.add(m);
			return new Gson().toJson(listOfMaps);
			//attributes.put("message", "There was an error: " + e);
			//return new ModelAndView(attributes, "error.ftl");
		} finally 
		{
		if (connection != null) 
			try{
				connection.close();
			} catch(SQLException e){}
		}
	});
  
	get("/tashchezAdd", (req, res) -> {
		int i;
		
		String arr[] = req.queryParams().toArray(new String[0]);
		String s = req.queryParams(arr[0]);
		
		for(i=1 ; i < arr.length ; i++)
			s += " AND idDefinition=" + req.queryParams(arr[i]);
		
		
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT INTO Tashchezs (status, level, content) VALUES ('app', 1, '301*definitionDownLeft*0|302*definitionRightDown*2|303*definitionDown*3|304*definitionLeftDown*4|305*definitionDown*6|306*definitionDownLeft*14|307*definitionDown*16|308*definitionLeft*18|309*definitionDown*26|310*definitionLeft*28|311*definitionDown*32|312*definitionDown*34|313*definitionDownLeft*35|314*definitionLeft*37|315*definitionLeft*45')");	
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
  
	get("/definitionAdd", (req, res) -> {
	int i;
	
	String arr[] = req.queryParams().toArray(new String[0]);
	String s = req.queryParams(arr[0]);
	
	for(i=1 ; i < arr.length ; i++)
		s += " AND idDefinition=" + req.queryParams(arr[i]);
	
	
    Connection connection = null;
    Map<String, Object> attributes = new HashMap<>();
    try {
      connection = DatabaseUrl.extract().getConnection();

      Statement stmt = connection.createStatement();
      stmt.executeUpdate("INSERT INTO Definitions (definition, solution, solutionLength, level) VALUES ('יחידה מרכזית מיוחדת', 'יממ', 3, 1)");	
      return new ModelAndView(attributes, "db.ftl");
    } catch (Exception e) {
      attributes.put("message", "There was an error: " + e);
      return new ModelAndView(attributes, "error.ftl");
    } finally {
      if (connection != null) try{connection.close();} catch(SQLException e){}
    }
  }, new FreeMarkerEngine());

	get("/statusAdd", (req, res) -> {
		int i;
		String s[]={"","","","","",""};
		String arr[] = req.queryParams().toArray(new String[0]);
		List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		Map m = new HashMap<String, String>();
		
	
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
				s[0] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("content") == 0)
				s[1] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("type") == 0)
				s[2] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("index") == 0)
				s[3] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("comments") == 0)
				s[4] = req.queryParams(arr[i]);			
		}
			
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT INTO Statuses (username, content, dateTime, type, index, comments) VALUES ('" + s[0] + "', '" + s[1] + "',clock_timestamp() " + ", '" + s[2] + "', " + s[3] + ", '" + s[4] +"')");
		//return new ModelAndView(attributes, "db.ftl");
		m.put("GOOD2", "GOOD2: status added succeffuly");
		listOfMaps.add(m);
		return new Gson().toJson(listOfMaps);
		} catch (Exception e) 
		{
			
			m.put("EER2", "ERR2: cant add status" + e);
			listOfMaps.add(m);
			return new Gson().toJson(listOfMaps);
			//attributes.put("message", "There was an error: " + e);
			//return new ModelAndView(attributes, "error.ftl");
		} finally 
		{
		if (connection != null) 
			try{
				connection.close();
			} catch(SQLException e){}
		}
	});

	get("/commentAdd", (req, res) -> {
		int i;
		String s[]={"","","","","",""};
		String arr[] = req.queryParams().toArray(new String[0]);
		List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		Map m = new HashMap<String, String>();
		
	
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
				s[0] = req.queryParams(arr[i]);
			else if(arr[i].compareTo("content") == 0)
				s[1] = req.queryParams(arr[i]);	
		}
			
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT INTO Comments (username, content, dateTime) VALUES ('" + s[0] + "', '" + s[1] + "',clock_timestamp())");
		//return new ModelAndView(attributes, "db.ftl");
		m.put("GOOD2", "GOOD2: status added succeffuly");
		listOfMaps.add(m);
		return new Gson().toJson(listOfMaps);
		} catch (Exception e) 
		{
			
			m.put("EER2", "ERR2: cant add status" + e);
			listOfMaps.add(m);
			return new Gson().toJson(listOfMaps);
			//attributes.put("message", "There was an error: " + e);
			//return new ModelAndView(attributes, "error.ftl");
		} finally 
		{
		if (connection != null) 
			try{
				connection.close();
			} catch(SQLException e){}
		}
	});


	
	

  
	get("/userGet", (req, res) -> {
		int i;
		String s[]={"",""};
		String arr[] = req.queryParams().toArray(new String[0]);
		
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
				s[0] = req.queryParams(arr[i]);
			if(arr[i].compareTo("password") == 0)
				s[1] = req.queryParams(arr[i]);
		}
			
	
		String query = "SELECT * FROM Users WHERE username='" + s[0] + "' AND password='" + s[1] + "'";
		Connection connection = null;
		List<Map<String, Object>> listOfMaps = null;
	
		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
			System.err.println("***exception trying to connect***");
			ex.printStackTrace();
		}
	
		try {
			QueryRunner queryRunner = new QueryRunner();
			listOfMaps = queryRunner.query(connection, query, new MapListHandler());

		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		
		if(listOfMaps.isEmpty())
		{
			Map m = new HashMap<String, String>();
			m.put("EER1", "ERR1: user not found");
			listOfMaps.add(m);
			return new Gson().toJson(listOfMaps);
		}
		else
		return new Gson().toJson(listOfMaps);
	});
   
   	get("/userGetName", (req, res) -> {
		int i;
		String s="";
		Connection connection = null;
		String arr[] = req.queryParams().toArray(new String[0]);
		List<Map<String, Object>> listOfMaps = new ArrayList<Map<String, Object>>();
		Map m = new HashMap<String, String>();
		
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
				s = req.queryParams(arr[i]);
		}
			
	
		String query = "SELECT firstName, lastName FROM Users WHERE username='" + s + "'";
		
		
	
		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
			System.err.println("***exception trying to connect***");
			ex.printStackTrace();
		}
	
		try {
			QueryRunner queryRunner = new QueryRunner();
			listOfMaps = queryRunner.query(connection, query, new MapListHandler());
			m.put("GOOD2", "GOOD2: user added succeffuly");
			listOfMaps.add(m);
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		
		if(listOfMaps.isEmpty())
		{			
			m.put("EER1", "ERR1: user not found");
			listOfMaps.add(m);
			return new Gson().toJson(listOfMaps);
		}
		else
		return new Gson().toJson(listOfMaps);
	});
   
	get("/tashchezGet", (req, res) -> {
		//int i;
		//
		//String arr[] = req.queryParams().toArray(new String[0]);
		//String s = req.queryParams(arr[0]);
		//
		//for(i=1 ; i < arr.length ; i++)
		//	s += " AND idDefinition=" + req.queryParams(arr[i]);
		
		String query = "SELECT * FROM Tashchez";
		Connection connection = null;
		List<Map<String, Object>> listOfMaps = null;
	
		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
			System.err.println("***exception trying to connect***");
			ex.printStackTrace();
		}
	
		try {
			QueryRunner queryRunner = new QueryRunner();
			listOfMaps = queryRunner.query(connection, query, new MapListHandler());
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		if(listOfMaps.isEmpty())
			return new Gson().toJson("ERR1: tashchez not found");
		else
		return new Gson().toJson(listOfMaps);
	});
  
//check input INJECTION
	get("/definitionGet", (req, res) -> {
		int i;
		
		String arr[] = req.queryParams().toArray(new String[0]);
		String s = req.queryParams(arr[0]);
		
		for(i=1 ; i < arr.length ; i++)
			s += " OR idDefinition=" + req.queryParams(arr[i]);
		
		
		
		String query = "SELECT * FROM Definition WHERE idDefinition=" + s;//when change to definition***s*** need to chenge it here!!!!!!!!!!!!
		
		
		Connection connection = null;
		List<Map<String, Object>> listOfMaps = null;

		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
          System.err.println("***exception trying to connect***");
          ex.printStackTrace();
		}

		try {
          QueryRunner queryRunner = new QueryRunner();
          listOfMaps = queryRunner.query(connection, query, new MapListHandler());
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
          DbUtils.closeQuietly(connection);
		}
	  
		if(listOfMaps.isEmpty())
			return new Gson().toJson("ERR1: all def not found");
		else
		return new Gson().toJson(listOfMaps);
	});
  
  	get("/statusGet", (req, res) -> {
		int i;
		String s[]={"",""};
		String arr[] = req.queryParams().toArray(new String[0]);
		boolean usernameFlag = false, typeFlag = false;
		String query = "";
		
		for(i=0 ; i < arr.length ; i++)
		{
			if(arr[i].compareTo("username") == 0)
			{
				s[0] = req.queryParams(arr[i]);
				usernameFlag = true;
			}
			if(arr[i].compareTo("type") == 0)//not exist in app ui
			{
				s[1] = req.queryParams(arr[i]);
				typeFlag = true;
			}
		}
	
		if((usernameFlag && typeFlag))
			query = "SELECT * FROM Statuses WHERE username='" + s[0] + "' AND type='" + s[1] + "' ORDER BY dateTime DESC";
		else if((usernameFlag && !typeFlag))
			query = "SELECT * FROM Statuses  WHERE username='" + s[0] + "' ORDER BY dateTime DESC";
		else if((!usernameFlag && typeFlag))
			query = "SELECT * FROM Statuses  WHERE type='" + s[1] + "' ORDER BY dateTime DESC";
		else if((!usernameFlag && !typeFlag))
			query = "SELECT * FROM Statuses  ORDER BY dateTime DESC";
	
		Connection connection = null;
		List<Map<String, Object>> listOfMaps = null;
	
		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
			System.err.println("***exception trying to connect***");
			ex.printStackTrace();
		}
	
		try {
			QueryRunner queryRunner = new QueryRunner();
			listOfMaps = queryRunner.query(connection, query, new MapListHandler());
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		if(listOfMaps.isEmpty())
			return new Gson().toJson("ERR1: tashchez not found");
		else
		return new Gson().toJson(listOfMaps);
	});
  
  	get("/commentGet", (req, res) -> {
		int i;
		
		String arr[] = req.queryParams().toArray(new String[0]);
		String s = req.queryParams(arr[0]);
		
		for(i=1 ; i < arr.length ; i++)
			s += " OR idComment=" + req.queryParams(arr[i]);
		
		
		
		String query = "SELECT * FROM Comments WHERE idComment=" + s + " ORDER BY dateTime DESC";
		
		
		Connection connection = null;
		List<Map<String, Object>> listOfMaps = null;

		try {
		connection = DatabaseUrl.extract().getConnection();
		} catch (Exception ex) {
          System.err.println("***exception trying to connect***");
          ex.printStackTrace();
		}

		try {
          QueryRunner queryRunner = new QueryRunner();
          listOfMaps = queryRunner.query(connection, query, new MapListHandler());
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't query the database.", se);
		} finally {
          DbUtils.closeQuietly(connection);
		}
	  
		if(listOfMaps.isEmpty())
			return new Gson().toJson("ERR1: all comments not found");
		else
		return new Gson().toJson(listOfMaps);
	});
  
  
  
  
  	get("/usersDelete", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();

		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE Users");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());  
	
	get("/tashchezsDelete", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
		//dont have to fill "level" so need to check that with var
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE Tashchezs");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
   
    get("/definitionsDelete", (req, res) ->	{
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE Definitions");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine());
  
    get("/statusesDelete", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE Statuses");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine()); 
  
    get("/commentsDelete", (req, res) -> {
		Connection connection = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
		connection = DatabaseUrl.extract().getConnection();
	
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP TABLE Comments");
		attributes.put("results", "outputtttt");
		return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
		attributes.put("message", "There was an error: " + e);
		return new ModelAndView(attributes, "error.ftl");
		} finally {
		if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}, new FreeMarkerEngine()); 
       
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

 


}
}
