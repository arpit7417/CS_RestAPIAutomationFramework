package api.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UsrTestDD {

	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testcreateUser(String userID,String userName,String fname,String lname,String useremail,String pwd,String ph) {
		
        User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		Response response=UserEndPoints.createUser(userPayload);
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testdeleteUserData(String username) throws InterruptedException {
		
		Thread.sleep(500);
		
		Response response=UserEndPoints.deleteUser(username);
		
		// log response
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
}
