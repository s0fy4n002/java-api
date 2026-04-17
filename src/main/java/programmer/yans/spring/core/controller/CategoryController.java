package programmer.yans.spring.core.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.Valid;
import programmer.yans.spring.core.dto.CategoryData;
import programmer.yans.spring.core.dto.ResponseData;
import programmer.yans.spring.core.mapper.CategoryMapper;
import programmer.yans.spring.core.model.entity.Category;
import programmer.yans.spring.core.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<ResponseData<CategoryData>> create(@Valid @RequestBody Category category, Errors errors) {
        ResponseData<CategoryData> responseData = new ResponseData<>();

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
        responseData.setPayload(categoryMapper.toDTO(createdCategory));
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

        category.setId(id);
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

    @PostMapping({"/search/{size}/{page}", "/search/{size}/{page}/{sort}"})
    public ResponseEntity<ResponseData<Iterable<Category>>> searchLikeNameWithPaging(
        @RequestBody JsonNode json, 
        @PathVariable int size, 
        @PathVariable int page, 
        @PathVariable(required = false) String sort
    ) {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        String name = json.get("name").asText();
        Pageable pageable = PageRequest.of(page, size);
        if(sort != null && (sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("desc"))){
            pageable = PageRequest.of(page, size, Sort.by(sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }
        responseData.setPayload(categoryService.searchLikeName(name, pageable));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping({"/search/{size}/{page}", "/search/{size}/{page}/{sort}"})
    public ResponseEntity<ResponseData<Iterable<Category>>> getSearchLikeNameWithPaging(
        @RequestParam String name, 
        @PathVariable int size, 
        @PathVariable int page, 
        @PathVariable(required = false) String sort
    ) {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        Pageable pageable = PageRequest.of(page, size);
        if(sort != null && (sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("desc"))){
            pageable = PageRequest.of(page, size, Sort.by(sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }
        responseData.setPayload(categoryService.searchLikeName(name, pageable));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/create-all")
    public ResponseEntity<ResponseData<Iterable<Category>>> createBatch(@RequestBody Category[] categories) {
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(categoryService.saveBatch(Arrays.asList(categories)));
        return ResponseEntity.ok(responseData);
    }

}
