package programmer.yans.spring.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
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

    // @Autowired
    // private SupplierMapper supplierMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody Supplier supplier, Errors errors) {
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
        
        Supplier createdSupplier = supplierService.save(supplier);

        // SupplierData supplierData = supplierMapper.toDTO(createdSupplier);

        responseData.setStatus(true);
        responseData.getMessages().add("Supplier created successfully");
        responseData.setPayload(createdSupplier);
        return ResponseEntity.ok(responseData);
    }
}
