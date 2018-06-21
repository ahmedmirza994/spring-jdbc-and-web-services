package com.ahmed.database.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ahmed.database.bean.Person;

@Repository
@RestController
public class PersonDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class PersonRowMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet resultSet, int numRows) throws SQLException {
			
			Person person = new Person();
			person.setId(resultSet.getInt("id"));
			person.setName(resultSet.getString("name"));
			person.setLocation(resultSet.getString("location"));
			person.setBirthDate(resultSet.getTimestamp("birth_date"));
			
			return person;
		}
		
	}
	
	@GetMapping("/persons")
	public List<Person> findAll() {
		return jdbcTemplate.query(
					"select * from person", 
					new PersonRowMapper()
				);
	}
	
	//@GetMapping(value = "/person", method = RequestMethod.GET)
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public Person findById(int id) {
		return jdbcTemplate.queryForObject(
					"select * from person where id=?",
					new Object[]{id}, 
					new BeanPropertyRowMapper<Person>(Person.class)
				);
	}
	
	public Person findByName(String name) {
		return jdbcTemplate.queryForObject(
					"select * from person where name=?",
					new Object[]{name}, 
					new BeanPropertyRowMapper<Person>(Person.class)
				);
	}
	
	public int deleteById(int id) {
		return jdbcTemplate.update(
					"delete from person where id=?",new Object[]{id}
				);
	}
	
	public int insert(Person person) {
		return jdbcTemplate.update(
				"insert into person  (id, name, location, birth_date)" 
				+ "values(?, ?, ?, ?)",new Object[]{
						person.getId(),
						person.getName(),
						person.getLocation(),
						new Timestamp(person.getBirthDate().getTime())
						}
				);
	}
	
	public int update(Person person) {
		return jdbcTemplate.update(
				"update person set name = ?, location = ?, birth_date = ? " 
				+"where id = ?",new Object[]{
						person.getName(),
						person.getLocation(),
						new Timestamp(person.getBirthDate().getTime()),
						person.getId()
						}
				);
	}
}
