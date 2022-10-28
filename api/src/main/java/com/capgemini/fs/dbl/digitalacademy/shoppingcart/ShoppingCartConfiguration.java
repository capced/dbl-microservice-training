package com.capgemini.fs.dbl.digitalacademy.shoppingcart;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
@Import(SpringDataRestConfiguration.class)
@EnableWebMvc
public class ShoppingCartConfiguration  {


}
