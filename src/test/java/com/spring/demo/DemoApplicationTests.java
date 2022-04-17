package com.spring.demo;

import com.spring.demo.persistence.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void test1() {
		Assertions.assertThat(userRepository.existsById(1)).isTrue();
	}
}
