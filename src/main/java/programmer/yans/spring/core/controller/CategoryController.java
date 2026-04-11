package programmer.yans.spring.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import programmer.yans.spring.core.dto.ResponseData;
import programmer.yans.spring.core.model.entity.Category;
import programmer.yans.spring.core.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody Category category, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();

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

        Category createdCategory = categoryService.save(category);

        responseData.setStatus(true);
        responseData.getMessages().add("Category created successfully");
        responseData.setPayload(createdCategory);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Category>>> findAll() {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(categoryService.getAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> findById(@PathVariable Long id) {
        ResponseData<Category> responseData = new ResponseData<>();
        Category category = categoryService.getById(id);
        if (category != null) {
            responseData.setStatus(true);
            responseData.setPayload(category);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Category not found");
        }
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> update(@PathVariable Long id, @Valid @RequestBody Category category, Errors errors) {
        ResponseData<Category> responseData = new ResponseData<>();

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

        Category updatedCategory = categoryService.save(category);

        responseData.setStatus(true);
        responseData.getMessages().add("Category updated successfully");
        responseData.setPayload(updatedCategory);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        ResponseData<Void> responseData = new ResponseData<>();
        categoryService.deleteById(id);
        responseData.setStatus(true);
        responseData.getMessages().add("Category deleted successfully");
        return ResponseEntity.ok(responseData);
    }


    
}
