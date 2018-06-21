package com.ahmed.database;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ahmed.database.bean.Person;
import com.ahmed.database.jdbc.PersonDao;

@SpringBootApplication
public class DataBaseApplication implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PersonDao personDao;
	public static void main(String[] args) {
		SpringApplication.run(DataBaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users -> {}",personDao.findAll());
		//logger.info("user -> {}",personDao.DeleteById(10001));
		logger.info("Inserting 10004 -> {}", 
				personDao.insert(new Person(10004, "Tara", "Berlin", new Date())));
		logger.info("Update 10003 -> {}", 
				personDao.update(new Person(10003, "Azeem", "Lahore", new Date())));
		logger.info("All users -> {}",personDao.findAll());
	}
}
