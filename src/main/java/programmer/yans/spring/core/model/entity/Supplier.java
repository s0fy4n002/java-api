package programmer.yans.spring.core.model.entity;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.HashSet;


@Entity
@Table(name = "suppliers")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Supplier name must not be empty")
    @Column(nullable = false, length = 150)
    private String name;

    @NotEmpty(message = "Supplier address must not be empty")
    @Column(nullable = false, length = 255)
    private String address;

    @NotEmpty(message = "Supplier email must not be empty")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products = new HashSet<>();

    

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
