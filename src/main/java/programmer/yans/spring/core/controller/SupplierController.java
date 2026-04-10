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
import programmer.yans.spring.core.dto.SupplierData;
import programmer.yans.spring.core.mapper.SupplierMapper;
import programmer.yans.spring.core.model.entity.Supplier;
import programmer.yans.spring.core.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierMapper supplierMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplier, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();

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

        Supplier createdSupplier = supplierService.save(supplierMapper.toEntity(supplier, null));

        // SupplierData supplierData = supplierMapper.toDTO(createdSupplier);

        responseData.setStatus(true);
        responseData.getMessages().add("Supplier created successfully");
        responseData.setPayload(createdSupplier);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Supplier>>> findAll() {
        ResponseData<Iterable<Supplier>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(supplierService.getAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Supplier>> findById(@PathVariable Long id) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        Supplier supplier = supplierService.getById(id);
        if (supplier != null) {
            responseData.setStatus(true);
            responseData.setPayload(supplier);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Supplier not found");
        }
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Supplier>> update(@PathVariable Long id, @Valid @RequestBody SupplierData supplier, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();

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

        Supplier updatedSupplier = supplierService.save(supplierMapper.toEntity(supplier, id));

        responseData.setStatus(true);
        responseData.getMessages().add("Supplier updated successfully");
        responseData.setPayload(updatedSupplier);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        ResponseData<Void> responseData = new ResponseData<>();
        supplierService.deleteById(id);
        responseData.setStatus(true);
        responseData.getMessages().add("Supplier deleted successfully");
        return ResponseEntity.ok(responseData);
    }

}
