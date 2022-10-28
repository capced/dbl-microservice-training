package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.core.ProductType;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.ProductRepository;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class ProductRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void given_mvc_controller_up_findall_products() throws Exception {
        Product newProduct = Product.builder()
            .code("TEST_1")
            .name("Test Un")
            .rawPrice(new BigDecimal(10.0))
            .type(ProductType.LIVRE).build();

        newProduct = productRepository.save(newProduct);

        mockMvc.perform(get("/products"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.products", hasSize(1)))
            .andExpect(jsonPath("$._embedded.products[0].id", equalTo(1)));
    }
}
