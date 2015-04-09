package com.restuth.rs;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.transaction.annotation.Transactional;

import com.restuth.entity.BlogPost;
import com.restuth.entity.TaskManager;
import com.restuth.entity.Statistics;
import com.restuth.entity.User;

@Path("/blog")
public class PostDesignPatternRS {

	@Context
	UriInfo uriInfo;

	// Example 1: POST as a resource Factory
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(@FormParam("fname") String fname,
			@FormParam("lname") String lname, @FormParam("email") String email) {
		// Rather than this DAO can be called and DB object can be created and
		// used
		User nuser = new User(fname, lname, email);
		nuser.setId("10");

		// Alternative if inserting in DB

		// Builds the revised URI for new resource
		URI useruri = uriInfo.getAbsolutePathBuilder().path(nuser.getId())
				.build();

		// This adds the location header with the provided URI and status code
		// as 201.
		Response res = Response.created(useruri).status(201).build();
		return res;

	}

	// Example 2:POST as resource Controller Processor
	@POST
	@Path("/posts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	// If there is a try catch inside the method, make sure to call out the
	// exception name for rollback in Transaction annotation
	@Transactional(rollbackFor = Exception.class)
	public Response publishPost(BlogPost blog) {
		Response res;
		try {
			// Helper method to perform syntactic validation on input
			TaskManager result = validateInput(blog);

			if (result.getStatus().equals("Error"))
				res = Response.status(400).entity(result).build();

			else {
				TaskManager aresult = activatePost(blog);
				TaskManager nresult = notifySubscribers(blog.getId());
				TaskManager presult = publishNewsFeed(blog.getId());

				// Everything is successful. Return 200 success code along with
				// Entity
				if (aresult.getStatus().equals("Success")
						&& nresult.getStatus().equals("Success")
						&& presult.getStatus().equals("Success"))
					res = Response.status(200).entity(blog).build();
				else {

					// If Input Validation is successful, however fails to
					// process due to the state of the resource, return 422.
					TaskManager eresult = new TaskManager("Publish POST",
							"Error", "IN1500", aresult.getErrorText() + " "
									+ nresult.getErrorText() + " "
									+ presult.getErrorText());
					res = Response.status(422).entity(eresult).build();
				}

			}
		}

		catch (Exception e) {
			// If there is any other exception, send a internal server error.
			TaskManager eresult = new TaskManager("Publish POST", "Error",
					"IN1600", e.getMessage());
			res = Response.status(500).entity(eresult).build();
		}
		return res;
	}

	// Example 3: Asynchronous Request Step1 triggering Asynchronous POST
	@POST
	@Path("/getstats")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateStats(TaskManager tm) {
		System.out.println("Inside");

		GregorianCalendar gcal = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("MMddyyyy_hhmmss");
		String curdate = fmt.format(gcal.getTime());

		System.out.println("Current Date: " + curdate);

		// Generate a new Task Resource URI.
		URI taskuri = uriInfo.getAbsolutePathBuilder()
				.path("/taskmgr/" + curdate).build();

		// Initiate Asychronous Report generation process with the return
		// estimated completion date
		GregorianCalendar escal = createReport(gcal);
		String esdate = fmt.format(escal.getTime());

		// Update Task Manager with the estimated Asynchronous task status
		// result
		tm.setId(curdate);
		tm.setStatus("Pending");
		tm.setMessage("Resouce is getting processed asynchronously");
		tm.setResource(taskuri.toString());
		tm.setResultavailable(esdate);

		// Add status Code =202, Header Content-Location with the URI of the new
		// resource, and body with Results details.
		Response res = Response.status(202).entity(tm)
				.header("Content-Location", taskuri.toString()).build();

		return res;

	}

	// Example 3: Asynchronous Request Step2: GET method to retrieve status of
	// Asynchronous POST Results
	@GET
	@Path("/getstats/taskmgr/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTaskStatus(@PathParam("id") String id) {
		Response res;

		// Gets the base URI http://hostname:port/restauth/rest
		URI staturi = uriInfo.getBaseUri();
		URI cururi = uriInfo.getRequestUri();

		// Backend activity is mocked in this method.
		// This method will return >0 if process completed, 0 if still
		// processing, -1 if failed
		int taskresult = isTaskCompleted(id);

		if (taskresult > 0) {
			TaskManager tasksts = new TaskManager("Generate Statistics",
					"Completed", "Processed Successfully", staturi.toString(),
					null);

			// Build a new URI for the newly generated resource. Here the result
			// has been mocked.
			// Ideally these
			int resourceid = taskresult;
			staturi = UriBuilder.fromPath(staturi.toString())
					.path("/blog/statistics/" + resourceid).build();

			// Build a response with Status code 303, with the location pointing
			// to the newly generated resource.
			// Browser/Client gets the TaskManager result and redirects the
			// request to the revised new resource.
			res = Response.created(staturi).status(303).entity(tasksts).build();

		}
		// Task Result ==0, still processing
		else if (taskresult == 0) {
			// If task is not completed yet,estimate the revised time of
			// completion of task.
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.add(Calendar.MINUTE, 2);
			SimpleDateFormat fmt = new SimpleDateFormat("MMddyyyy_hhmmss");
			String esdate = fmt.format(gcal.getTime());

			TaskManager result = new TaskManager("Generate Statistics",
					"InProgress",
					"Resource is still getting processed asynchronously", null,
					esdate);
			result.setId(id);

			// Send Status Code =200, with Task Manager detailing task is not
			// yet completed.
			res = Response.status(200).entity(result)
					.header("Content-Location", cururi.toString()).build();

		}
		// task result -1, task failed
		else {
			// Send Status Code =500, with the reason for failure
			TaskManager result = new TaskManager("Generate Statistics",
					"Error", "IN1700", "Not able to connect to DB");
			result.setId(id);
			res = Response.status(500).entity(result).build();

		}

		return res;

	}

	// Example 3: Asynchronous Request Step3: GET method to retrieve the
	// generated resource .Browser will redirect to this location automatically
	@GET
	@Path("/statistics/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Statistics retriveStats(@PathParam("id") String id) {
		// created statistics resource. Mocking the business logic
		Statistics s = new Statistics(id, "Statistics Report", 100, 1000,
				10000, 500, 2500, 12000);
		LinkedHashMap<String, BlogPost> hm = new LinkedHashMap<String, BlogPost>();
		hm.put("REST Design Pattern", new BlogPost("100",
				"REST Design Pattern", "Sri"));
		hm.put("Mongo DB Quick Reference", new BlogPost("200",
				"MongoDB Quick Reference", "Sri"));
		s.setReportdate("04032015");
		s.setPopularPost(hm);
		return s;
	}

	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {
		User u = new User("John", "Pat", "test@gmail.com");
		Response res = Response.status(200).entity(u).build();
		return res;
	}

	/* --------------Helper Mocking Methods --------------- */
	private TaskManager activatePost(BlogPost blog) {
		return new TaskManager("Activate Post", "Success");
	}

	private TaskManager notifySubscribers(String postid) {
		return new TaskManager("Notify Subscribers", "Success");
	}

	private TaskManager publishNewsFeed(String postid) {
		return new TaskManager("PublishNewsFeed", "Success");
	}

	private int isTaskCompleted(String id) {
		int createdresourceid = 25;
		return createdresourceid;
	}

	private GregorianCalendar createReport(GregorianCalendar gcal) {
		gcal.add(Calendar.MINUTE, 5);
		return gcal;
	}

	private TaskManager validateInput(BlogPost blog) {
		TaskManager result;
		if (blog.getId() != null && blog.getTitle() != null) {
			result = new TaskManager("ValidateInput", "Success");

		} else {
			// Status Code 'IN1300' can be application specific codes to provide
			// more details
			result = new TaskManager("ValidateInput", "Error", "IN1300",
					"Missing Mandatory fields.");
		}
		return result;
	}

}
