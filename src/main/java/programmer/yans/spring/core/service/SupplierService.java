package programmer.yans.spring.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.TransactionScoped;
import programmer.yans.spring.core.model.entity.Supplier;
import programmer.yans.spring.core.model.repository.SupplierRepository;

@Service
@TransactionScoped
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("supplier tidak boleh kosong");
        }
        return supplierRepository.save(supplier);
    }

    public Supplier getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        return supplierRepository.findById(id).orElse(null);
    }

    public Iterable<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        supplierRepository.deleteById(id);
    }

    public Supplier getByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email tidak boleh kosong");
        }
        return supplierRepository.findByEmail(email);
    }

    public List<Supplier> searchLikeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name tidak boleh kosong");
        }
        return supplierRepository.findByNameContains(name);
    }

    public List<Supplier> searchLikeNameOrEmail(String name, String email) {
        if ((name == null || name.isEmpty()) && (email == null || email.isEmpty())) {
            throw new IllegalArgumentException("name atau email harus diisi");
        }
        return supplierRepository.findByNameContainsOrEmailContains(name, email);
    }

}
