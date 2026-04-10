package programmer.yans.spring.core.service;

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

}
