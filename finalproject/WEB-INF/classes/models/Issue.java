/*
    Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
    Student No: 3291441, 3276255, 3088810 , 3189731
    Date: 08/06/2019
    Course: SENG2050
    Assignment: Assignment 3

    Description: Issue represents an issue created by a user in our database.
    Has an inner class comment.
 */

package models;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/* Written by Nathan Fittler-Willis, C3189731
 * Assignment 3, SENG2050 - 31/May/2019
 */

/* HOW TO USE THIS CLASS
 * when creating a new issue, make sure to use the 3rd constructor only.
 * use the 2nd (copy) constructor when attempting to instantiate an existing issue with code: Issue issue = new Issue(getIssue(<issueid>))
 * never use the first constructor, its their so this class complies with the javabeans standard.
 */

public class Issue implements Serializable {
	private int issueID; //unique issueID of the issue.
	private String username, content, resolutionDetails, title; //issue body details.
	private Date dateCreated, dateResolved; //dates w.r.t issue.
	private IssueCatagories catagory; //what type of catagory the issue is.
	private IssueStatus status; //what status this issue is in currently

	//Constructors
	//default constructor for javaBeans
	public Issue()
	{

	}
	
	//this copy constructor can be used to quickly perform a deep copy of another issue class - use with getIssue().
	public Issue(Issue i)
	{
		this.issueID = i.getIssueID();
		this.username =i.getUsername();
		this.content = i.getContent();
		this.resolutionDetails = i.getResolutionDetails();
		this.title = i.getTitle();
		this.dateCreated = i.getDateCreated();
		this.dateResolved = i.getDateResolved();
		this.catagory = i.getCatagory();
		this.status = i.getStatus();
	}
	//use this constructor when making new a new issue
	public Issue(String author, String content, String title, IssueCatagories catagory) {
		this.dateCreated = new Date();
		this.username = author;
		this.content = content;
		this.title = title;
		this.setCatagory(catagory);
		this.issueID = generateUniqueID();
		this.resolutionDetails = "";
		this.status = IssueStatus.NEW;
	}

	//Mutator methods
	//Use this method when you need to update the status of an issue, as it saves the changes to the database.
	//the only changes that can be updated is the status. if that status is resolved then the dateResolved is updated too!
	public void updateIssueStatus(IssueStatus status)
	{
		String update;
		boolean updateDateResolved = false;
		Date date = null;
		Connection connection = null;
		PreparedStatement ps = null;
		
		switch(status) 
		{
		case RESOLVED:
			update = "UPDATE issues SET status=?, dateresolved=? WHERE id=?";
			updateDateResolved = true;
			date = new Date();
			break;
		
		default:
			update = "UPDATE issues SET status=? WHERE id=?";
			break;
		}
			
		try {

			//Database link
			connection = Config.getConnection();
			ps = connection.prepareStatement(update);

			ps.setString(1, status.toString());
			if(updateDateResolved) //update the date resolved if status = resolved
				{
					ps.setTimestamp(2, new Timestamp(date.getTime()));
					ps.setInt(3, this.issueID);
				}
			else ps.setInt(2, this.issueID); //just update the issue id

						
			
			ps.executeUpdate();
			System.out.println();
			
			
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				connection.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//adds the issue class to the database as a new row!
	public void addIssueToDB()
	{
		String insert = "INSERT INTO issues (id ,author, content, resolutiondetails, title, datecreated, dateresolved, catagory, status) values (?,?,?,?,?,?,?,?,?)";
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			
			//Database link
			connection = Config.getConnection();
			ps = connection.prepareStatement(insert); //safe statement
			 ps.setInt(1, this.issueID);
			 ps.setString(2, this.username); //set author
			 ps.setString(3, this.content); //set content
			 ps.setString(4, this.resolutionDetails); //set resolution details
			 ps.setString(5, this.title); //set resolution details
			 Date d = this.dateCreated;
			 ps.setTimestamp(6, new Timestamp(d.getTime())); //set date created as timestamp format
			
			 if (this.dateResolved != null) {
				d = this.dateResolved;
				ps.setTimestamp(7, new Timestamp(d.getTime())); //set date resolved as timestamp format
			}
			 else ps.setNull(7, Types.TIMESTAMP);
				
			ps.setString(8, this.catagory.toString()); //set category
			ps.setString(9, this.status.toString());
			ps.execute(); //push insert into the DB
			 
			 System.out.println("added to issue to the DB, id = " + this.issueID);

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//Adds a comment into the mySQL database so that it can later be retrieved.
	public void addComment(String Username, String body, boolean knowledgebase)
	{
		String insert = "INSERT INTO comments (author, body, date, issueid, knowledgebase) values (?,?,?,?,?)";
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			
			//Database Fetch
			connection = Config.getConnection(); //connect to DB
			ps = connection.prepareStatement(insert); //prepare the statement
			 
			 ps.setString(1, Username); //insert author into statement
			 ps.setString(2, body); //insert body into statement
			 Date d = new Date(); //prepare date
			 ps.setTimestamp(3, new Timestamp(d.getTime())); //insert date into statement as a timestamp
			 ps.setInt(4, this.issueID); //insert issueID into statement
			 ps.setBoolean(5, knowledgebase);
			 ps.execute(); //send the INSERT statement to the DB
			 
			 System.out.println("New comment added to issue, id = " + this.issueID);

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setResolution(String resolution)
	{
		String update;

		update = "UPDATE issues SET resolutiondetails=? WHERE id=?";
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			//Database link
			connection = Config.getConnection();
			ps = connection.prepareStatement(update);
			
			ps.setString(1, resolution);
			ps.setInt(2, this.issueID);
			ps.executeUpdate();
			this.setResolutionDetails(resolution);
			
			
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				connection.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	//Searches the database and generates a unique issueID that has not been used already.
	private int generateUniqueID() {
		//find the maximum primary key (id) in the database then add 1, this will be the unique ID.
		int count = 0;
		String query = "SELECT MAX(id) FROM issues";
		
		Connection connection = null;
		Statement s = null;
		ResultSet rs = null;
		
		
		try {
			
			//Database Fetch
			connection = Config.getConnection(); //Connect to DB
			 s = connection.createStatement(); //Configure statement
				       
			 rs = s.executeQuery(query); //execute the query
			 rs.next(); //move cursor to first row
			
			count = rs.getInt(1); //get the MAX value of the id primary key in the issues table
			count++; //increment by 1
			rs.beforeFirst(); //reset the cursor to the start of the table

			 System.out.println("new unique id generated " + count);

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				s.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public static List<Issue> getAllIssues()
	{
		List<Issue> issues = new ArrayList<>();
		String query = "SELECT * FROM issues ORDER BY status DESC, datecreated DESC";

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//Database Fetch
			 connection = Config.getConnection(); //connect to DB
			 ps = connection.prepareStatement(query); //prepare statement for DB
			 rs = ps.executeQuery(); //send the statement to the DB & store the results as a result set
			 rs.beforeFirst(); //Move cursor to the start of the table
			 
			 while(rs.next())  //Iterate through the result set, converting each row into a list element.
			 {
				 			 
				//load data into the class
				Issue issue = new Issue();
				 issue.setCatagory(IssueCatagories.valueOf(rs.getString("catagory")));
				 issue.setContent(rs.getString("content"));
				 issue.setDateCreated(rs.getTimestamp("datecreated"));
				 issue.setDateResolved(rs.getTimestamp("dateresolved"));
				 issue.setIssueID(rs.getInt("id"));
				 issue.setResolutionDetails(rs.getString("resolutiondetails"));
				 issue.setStatus(IssueStatus.valueOf(rs.getString("status")));
				 issue.setTitle(rs.getString("title"));
				 issue.setUsername(rs.getString("author"));
				
				issues.add(issue); //append the class to the list
			 }

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return issues;
	}
	
	//This method will return a list of issues that have a matching username in the database.
	public static List<Issue> getIssuesByUsername(String username)
	{
		List<Issue> issues = new ArrayList<>();
		
		String query = "SELECT * FROM issues WHERE author = ? ORDER BY status DESC, datecreated DESC";
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			//Database Fetch
			 connection = Config.getConnection(); //connect to DB
			 ps = connection.prepareStatement(query); //prepare statement for DB
			 ps.setString(1, username); //load issue id into the statement
			 rs = ps.executeQuery(); //send the statement to the DB & store the results as a result set
			 rs.beforeFirst(); //Move cursor to the start of the table
			 
			 while(rs.next())  //Iterate through the result set, converting each row into a list element.
			 {
				 			 
				//load data into the class
				Issue issue = new Issue();
				 issue.setCatagory(IssueCatagories.valueOf(rs.getString("catagory")));
				 issue.setContent(rs.getString("content"));
				 issue.setDateCreated(rs.getTimestamp("datecreated"));
				 issue.setDateResolved(rs.getTimestamp("dateresolved"));
				 issue.setIssueID(rs.getInt("id"));
				 issue.setResolutionDetails(rs.getString("resolutiondetails"));
				 issue.setStatus(IssueStatus.valueOf(rs.getString("status")));
				 issue.setTitle(rs.getString("title"));
				 issue.setUsername(rs.getString("author"));
				
				issues.add(issue); //append the class to the list
			 }

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return issues;
	}
	
	//gets every comment which has a matching id from the database, and returns each comment as an element in a list
	//if knowledgebase is false, then it only returns the comments that are for the knowledge base
	//if knowledgebase is true, then it only returns the comments that are for the issue;
	public List<Comment> getComments(boolean knowledgebase)
	{
		String query = "SELECT * FROM comments WHERE issueid = ? AND knowledgebase = ?";
		List<Comment> comments = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			//Database Fetch
			connection = Config.getConnection(); //connect to DB
			 ps = connection.prepareStatement(query); //prepare statement for DB
			 ps.setInt(1, this.issueID); //load issue id into the statement
			 ps.setBoolean(2, knowledgebase);
			 rs = ps.executeQuery(); //send the statement to the DB & store the results as a result set
			 rs.beforeFirst(); //Move cursor to the start of the table
			 
			 while(rs.next())  //Iterate through the result set, converting each row into a list element.
			 {
				Comment comment = new Comment(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getInt(4));
				comments.add(comment);
			 }

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return comments;
	}
	
	//get an issue from the database
	public static Issue getIssue(int issueID)
	{
		String query = "SELECT * FROM issues WHERE id = ?";
		Issue issue = new Issue();
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try  {
			
			connection = Config.getConnection(); //connect to DB
			ps = connection.prepareStatement(query); //prepare statement for DB
			ps.setInt(1, issueID); //load issue id into the statement
			rs = ps.executeQuery(); //send the statement to the DB & store the results as a result set
			rs.first(); //Reset cursor to first row

			//Load query results into instance variables
			String author = rs.getString("author");
			String content = rs.getString("content");
			String title = rs.getString("title");
			IssueCatagories catagory = IssueCatagories.valueOf(rs.getString("catagory"));
			String resolutionDetails = rs.getString("resolutiondetails");
			Date dateCreated = rs.getTimestamp("datecreated");
			Date dateResolved = rs.getTimestamp("dateresolved");
			IssueStatus status = IssueStatus.valueOf(rs.getString("status"));
					
			//prepare the object
			issue.setUsername(author);
			issue.setContent(content);
			issue.setTitle(title);
			issue.setCatagory(catagory);
			issue.setDateCreated(dateCreated);
			issue.setDateResolved(dateResolved);
			issue.setResolutionDetails(resolutionDetails);
			issue.setIssueID(issueID);
			issue.setStatus(status);
			
			System.out.println("object for issueID " + issueID + " retrieved from DB.");
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return issue;
	}

	//Getter & setters
	public int getIssueID() { return issueID; }
	public void setIssueID(int id) {this.issueID = id;}
	public String getUsername() { return username; }
	public void setUsername(String author) { this.username = author; }
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	public String getResolutionDetails() { return resolutionDetails; }
	public void setResolutionDetails(String resolutionDetails) { this.resolutionDetails = resolutionDetails; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public Date getDateCreated() { return dateCreated; }
	public void setDateCreated(Date dateCreated) {this.dateCreated = dateCreated;}
	public Date getDateResolved() { return dateResolved; }
	public void setDateResolved(Date dateResolved) { this.dateResolved = dateResolved; }
	public IssueCatagories getCatagory() { return catagory; }
	public void setCatagory(IssueCatagories catagory) { this.catagory = catagory; }
	public IssueStatus getStatus() { return status; }
	public void setStatus(IssueStatus status) { this.status = status; }
		
	




	//Composition Comment class
	//this class sets and defines the comments that are related to an issue;
	public class Comment {

		//Private variables
		private String author; //person who created the comment.
		private String body; //the body of the comment.
		private Date dateCreated; //Date the comment was created.
		private int issueID; //the issueID this comment is linked too.
		
		//Constructors
		public Comment(String author, String body, int issueID) {
			this.author = author;
			this.body = body;
			this.dateCreated = new Date();
			this.issueID = issueID;
		}
		
		private Comment(String author, String body, Date date, int issueID) {
			this.author = author;
			this.body = body;
			this.dateCreated = date;
			this.issueID = issueID;
		}
		
		//Getters & Setters
		public String getAuthor() { return author; }
		public void setAuthor(String author) { this.author = author; }
		public String getBody() { return body; }
		public void setBody(String body) { this.body = body; }
		public Date getDateCreated() { return dateCreated; }
		public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
		public void setIssueID(int id) {this.issueID = id; }
		public int getIssueID() {return this.issueID;}
	}
}
