package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.ShoppingCartModel;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.request.ProductRequest;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.assembler.ShoppingCartEntryModelAssembler;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.ProductRepository;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.ShoppingCartRepository;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.ShoppingCartProductEntry;

@RestController
public class ShoppingCartController {

	Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ShoppingCartEntryModelAssembler shoppingCartEntryModelAssembler;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(path = "mycart", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestHeader(name = "X-SESSION-ID", required = true) String sessionId, @RequestBody ProductRequest addProduct) {

		Product product = this.productRepository.findByCode(addProduct.getProductCode());
		if (product == null) {
			String noProductError = messageSource.getMessage("product.not.found", new String[] {addProduct.getProductCode()}, Locale.getDefault());
			logger.error(noProductError);
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, noProductError, new Exception(noProductError));
		}

		logger.info("Found one product {}", product);

		// ShoppingCartProductEntry productEntry = shoppingCartRepository.findBySessionIdAndProduct(sessionId, product);
        ShoppingCartProductEntry productEntry = ShoppingCartProductEntry.with(sessionId, product, addProduct.getQuantity());
		productEntry = shoppingCartRepository.save(productEntry);

		return ResponseEntity.ok(shoppingCartEntryModelAssembler.toModel(productEntry));
	}

    @RequestMapping(path = "mycart", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestHeader(name = "X-SESSION-ID", required = true) String sessionId, @RequestBody ProductRequest updateProduct) {

		Product product = this.productRepository.findByCode(updateProduct.getProductCode());
		if (product == null) {
			String noProductError = messageSource.getMessage("product.not.found", new String[] {updateProduct.getProductCode()}, Locale.getDefault());
			logger.error(noProductError);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, noProductError,
					new Exception(noProductError));
		}

		logger.info("Found one product {}", product);

		ShoppingCartProductEntry productEntry = shoppingCartRepository.findBySessionIdAndProduct(sessionId, product);
        if (productEntry == null) {
            String noProductEntryError = messageSource.getMessage("product.entry.not.found", new String[] {sessionId, product.getCode()}, Locale.getDefault());
            logger.error(noProductEntryError);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, noProductEntryError,
                new Exception(noProductEntryError));
        }

        if (updateProduct.getQuantity() > 0) {
            productEntry.setQuantity(updateProduct.getQuantity());
            productEntry = shoppingCartRepository.save(productEntry);

            return ResponseEntity.ok(shoppingCartEntryModelAssembler.toModel(productEntry));
        } else {
            shoppingCartRepository.delete(productEntry);
            logger.info("Product deleted {}", product);

            return ResponseEntity.noContent().build();
        }
	}

    @RequestMapping(path = "mycart", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-SESSION-ID", required = true) String sessionId) {
        List<ShoppingCartProductEntry> productEntries = shoppingCartRepository.findBySessionId(sessionId);
        if (productEntries.size() == 0) {
            String noProductEntryError = messageSource.getMessage("cart.not.found", new String[] {sessionId}, Locale.getDefault());
            logger.error(noProductEntryError);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, noProductEntryError,
                new Exception(noProductEntryError));
        }

        ShoppingCartModel cartModel = ShoppingCartModel.builder().productEntries(shoppingCartEntryModelAssembler.toCollectionModel(productEntries)).sessionId(sessionId).build();

        return ResponseEntity.ok(cartModel);
    }
}
