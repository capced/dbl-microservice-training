package com.capgemini.fs.dbl.digitalacademy.shoppingcart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.liquibase.enabled=false"
})
class ShoppingcartApplicationTests {

	@Test
    public void contextLoads() {

	}
}
