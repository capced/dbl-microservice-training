package com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.core.ProductType;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void given_datasource_up_save_product_ok() {
        Product newProduct = Product.builder()
            .code("TEST_1")
            .name("Test Un")
            .rawPrice(new BigDecimal(10.0))
            .type(ProductType.LIVRE).build();

        newProduct = productRepository.save(newProduct);
        assertThat(newProduct, notNullValue());
        assertThat(newProduct.getId(), notNullValue());
        assertThat(newProduct.getVersion(), notNullValue());

        Product searchProduct = productRepository.findByCode(newProduct.getCode());
        assertThat(searchProduct, notNullValue());
    }

    @Test
    public void given_datasource_up_delete_product_ok() {
        Product newProduct = Product.builder()
            .code("TEST_2")
            .name("Test Deux")
            .rawPrice(new BigDecimal(10.0))
            .type(ProductType.LIVRE).build();

        newProduct = productRepository.save(newProduct);
        assertThat(newProduct, notNullValue());

        productRepository.delete(newProduct);

        Product searchProduct = productRepository.findByCode("TEST_2");
        assertThat(searchProduct, nullValue());
    }

    @Test
    public void given_datasource_up_save_duplicate_ko() {
        Product newProduct = Product.builder()
            .code("TEST_3")
            .name("Test Trois")
            .rawPrice(new BigDecimal(10.0))
            .type(ProductType.LIVRE).build();

        newProduct = productRepository.save(newProduct);
        assertThat(newProduct, notNullValue());


        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Product newProduct2 = Product.builder()
                .code("TEST_3")
                .name("Test Trois")
                .rawPrice(new BigDecimal(10.0))
                .type(ProductType.LIVRE).build();
            newProduct2 = productRepository.save(newProduct2);
        });
        assertTrue(exception.getMessage().contains("could not execute statement"));
    }
}
