package com.asie_back.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class ProductController {

    private boolean isDuplicatedName(Product newProduct){
        List<Product> products = repository.findAll();
        boolean duplicated = false;
        for (Product existingProduct:products) {
            if(existingProduct.getName().equalsIgnoreCase(newProduct.getName())){
                duplicated = true;
                break;
            }
        }
        return duplicated;
    }

    @Autowired
    private ProductRepository repository;

    @GetMapping("/products")
    List<Product> findAll() {
        return repository.findAll();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    Product newProduct(@Valid @RequestBody Product newProduct) {
        if(isDuplicatedName(newProduct)){
            throw new ResponseStatusException(CONFLICT, "Product with name '"+newProduct.getName()+"' already exists");
        }else {
            return repository.save(newProduct);
        }
    }

    @GetMapping("/products/{id}")
    Product findOne(@PathVariable @Min(1) Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product with id '"+id+"' not found"));
    }

    @PutMapping("/products/{id}")
    Product saveOrUpdate(@RequestBody Product newProduct, @PathVariable Integer id) {
        return repository.findById(id)
                .map(x -> {
                    x.setName(newProduct.getName());
                    x.setDescription(newProduct.getDescription());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }

    @DeleteMapping("/products/{id}")
    void deleteBook( @PathVariable Integer id) {
        repository.deleteById(id);
    }
}
