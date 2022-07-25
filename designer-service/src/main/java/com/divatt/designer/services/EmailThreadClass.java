package com.divatt.designer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.UserProfileInfo;

@Service
public class EmailThreadClass extends Thread{

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// EmailService(List<Long>userIdList);
	}
	public void emailThreadRun(List<Long> userListIdList)
	{
//		EmailThreadClass emailThreadClass= new EmailThreadClass();
		System.out.println("Ok");
//		emailThreadClass.start();
	}
	public void EmailService(List<Long> userIdList)
	{
//		RestTemplate restTemplate= new RestTemplate();
//		for(int i=0;i<userIdList.size();i++)
//		{
//			ResponseEntity<UserProfileInfo> userProfileList= restTemplate.getForEntity("http://localhost:8080/dev/auth/info/USER/"+userIdList.get(i), UserProfileInfo.class);
//			System.out.println(userProfileList.getBody());
//		}
	}
}
