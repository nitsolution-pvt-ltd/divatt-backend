package com.ecom.ecomMongo.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.model.Role;
import com.ecom.ecomMongo.model.User;



/**
 * @author aryansh
 *
 */
@Service
public class DbInit implements CommandLineRunner 
{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception 
	{
		
		User user1 = new User("arya@ecom.com", passwordEncoder.encode("Arya123"),Role.USR.toString());
		User user2 = new User("situ@ecom.com", passwordEncoder.encode("Seetu123"),Role.USR.toString());
		User mgr = new User("boddu@ecom.com", passwordEncoder.encode("Boddu123"),Role.MGR.toString());
		User admin = new User("sanjib@ecom.com", passwordEncoder.encode("Sanjib123"),Role.ADMIN.toString());
		
		List<User> users = Arrays.asList(user1,user2,mgr,admin);
        this.userRepository.saveAll(users);
		

	}
}
