package com.sc.bankapp;

import com.sc.bankapp.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class BankappApplicationTests {

	

	@BeforeAll
	static void  beforeAllM(){
		System.out.println("this is before all");
	}

//	@BeforeEach
//	static  void setup(){
//		System.out.println( "this is before each");
//	}
//
//	@AfterAll
//	static  void  afterAll(){
//		System.out.println("this is after all");
//	}

//	@AfterEach
//	static  void  afterEach(){
//		System.out.println("this is after each");
//	}


	@Test
	void contextLoads() {
		System.out.println("Welcome to Tests");
	}

	@Test
	void contextLoadsTwo() {
		System.out.println("Welcome to Tests two");
	}

	@Test
	void contextLoadsThree() {
		System.out.println("Welcome to Tests three");
	}




}
