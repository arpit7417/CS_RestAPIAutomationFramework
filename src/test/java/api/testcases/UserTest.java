package api.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User userpayload;
	public static Logger logger;
	@BeforeClass
	public void generateTestData() {
		faker=new Faker();
		userpayload=new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		// obtain logger
		
		logger=LogManager.getLogger("RestAssuredAutomationFramework");
	}
	
	@Test(priority=1)
	public void testcreateUser() {
		Response response=UserEndPoints.createUser(userpayload);
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("create user executed");
	}
	
	@Test(priority=2)
	public void testgetUserData() {
		Response response=UserEndPoints.getUser(this.userpayload.getUsername());
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("Get user Data executed");
	}
	@Test(priority=3)
	public void testupdateUserData() {
		
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		Response response=UserEndPoints.updateUser(this.userpayload.getUsername(),userpayload);
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// validation
	    Response responsePostupdate = UserEndPoints.getUser(this.userpayload.getUsername());
	    responsePostupdate.then().log().all();
	    
	    logger.info("update user Data executed");
	}
	
	@Test(priority=4)
	public void testdeleteUserData() throws InterruptedException {
		
		Thread.sleep(1000);
		Response response=UserEndPoints.deleteUser(this.userpayload.getUsername());
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("Delete  user Data executed");
	}
	

}
