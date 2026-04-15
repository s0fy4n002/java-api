package programmer.yans.spring.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.Valid;
import programmer.yans.spring.core.dto.ResponseData;
import programmer.yans.spring.core.dto.SearchData;
import programmer.yans.spring.core.model.entity.Product;
import programmer.yans.spring.core.model.entity.Supplier;
import programmer.yans.spring.core.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Product>>> findAll() {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(productService.getAll());
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors) {

        ResponseData<Product> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                if (error instanceof FieldError fieldError) {
                    String field = fieldError.getField(); // name
                    String message = fieldError.getDefaultMessage(); // name is required

                    System.out.println(field + " => " + message);
                    responseData.getMessages().add(field + " => " + message);
                }
            }
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }

        responseData.getMessages().add("Product created successfully");
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> findById(@PathVariable("id") Long id) {
        ResponseData<Product> responseData = new ResponseData<>();
        Product product = productService.getById(id);
        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Product not found");
        }
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ResponseData<Iterable<Product>>> findByName(@PathVariable("name") String name) {
        ResponseData<Iterable<Product>> responseData = new ResponseData<>();
        Iterable<Product> products = productService.findByName(name);
        if (products != null) {
            responseData.setStatus(true);
            responseData.setPayload(products);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("No products found");
        }
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Product>> update(@PathVariable("id") Long id,
            @Valid @RequestBody Product product) {
        product.setId(id);
        ResponseData<Product> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable("id") Long id) {
        ResponseData<Void> responseData = new ResponseData<>();
        productService.deleteById(id);
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/{id}/add-supplier")
    public ResponseEntity<ResponseData<Void>> addSupplierToProduct(
            @PathVariable("id") Long productId,
            @RequestBody Supplier supplier) {

        productService.addSupplierToProduct(supplier, productId);

        ResponseData<Void> responseData = new ResponseData<>();
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/name")
    public ResponseEntity<ResponseData<Product>> getProductByName(@RequestBody SearchData searchData) {
        Product product = productService.findByProductName(searchData.getSearchKeyword());
        ResponseData<Product> responseData = new ResponseData<>();
        if (product != null) {
            responseData.setStatus(true);
            responseData.setPayload(product);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Product not found");
        }

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/like-name")
    public ResponseEntity<ResponseData<List<Product>>> getProductByNameLike(@RequestBody SearchData searchData) {
        List<Product> products = productService.findByProductNameLike(searchData.getSearchKeyword());
        ResponseData<List<Product>> responseData = new ResponseData<>();
        if (products != null && !products.isEmpty()) {
            responseData.setStatus(true);
            responseData.setPayload(products);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("No products found");
        }

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search/category/{categoryId}")
    public ResponseEntity<ResponseData<List<Product>>> getProductByCategory(
            @RequestBody @PathVariable("categoryId") Long categoryId) {
        List<Product> products = productService.findByCategory(categoryId);
        ResponseData<List<Product>> responseData = new ResponseData<>();
        if (products != null && !products.isEmpty()) {
            responseData.setStatus(true);
            responseData.setPayload(products);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("No products found");
        }

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search/supplier/{supplierId}")
    public ResponseEntity<ResponseData<List<Product>>> getProductBySupplier(
            @PathVariable("supplierId") Long supplierId) {
        List<Product> products = productService.findBySupplier(supplierId);
        ResponseData<List<Product>> responseData = new ResponseData<>();
        if (products != null && !products.isEmpty()) {
            responseData.setStatus(true);
            responseData.setPayload(products);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("No products found");
        }

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/like-name-order-by-id-desc")
    public ResponseEntity<ResponseData<List<Product>>> searchLikeNameOrderByIdDesc(@RequestBody JsonNode json) {
        ResponseData<List<Product>> responseData = new ResponseData<>();
        List<Product> products = productService.searchLikeNameOrderByIdDesc(json.get("name").asText());
        responseData.setStatus(true);
        responseData.setPayload(products);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/like-name-order-by-id-asc")
    public ResponseEntity<ResponseData<List<Product>>> searchLikeNameOrderByIdAsc(@RequestBody JsonNode json) {
        ResponseData<List<Product>> responseData = new ResponseData<>();
        List<Product> products = productService.searchLikeNameOrderByIdAsc(json.get("name").asText());
        responseData.setStatus(true);
        responseData.setPayload(products);
        return ResponseEntity.ok(responseData);
    }

}
