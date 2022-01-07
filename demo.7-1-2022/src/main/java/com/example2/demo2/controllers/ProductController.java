package com.example2.demo2.controllers;

import com.example2.demo2.models.Product;
import com.example2.demo2.models.ResponseObject;
import com.example2.demo2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    //DI = Dependency Injection
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    List<Product> getAllProducts(){
        /*return List.of(
                new Product(1L,"iphone",2010,24.0,""),
                new Product(2L,"iphone",2010,24.0,"")
        );*/
        return repository.findAll();
    }

    //get detail product: data, message, status
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct=repository.findById(id);

        if(foundProduct.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Query product success",foundProduct)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed","Query product fail","")
            );
        }

        /*return foundProduct.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Query product success",foundProduct)
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed","Query product fail","")
                );*/
        //return repository.findById(id).orElseThrow(()->new RuntimeException("can't find"));
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        //validate same name?
        List<Product> foundProducts=repository.findByProductName(newProduct.getName().trim());
        if(foundProducts.size()>0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed","Query product fail", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("OK","Query product success", repository.save(newProduct))
        );
    }

    //UPDATE/UPLOAD NEW
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product updateProduct, @PathVariable Long id){
        Optional<Product> updatedProduct = Optional.of(repository.findById(id).map(
                product -> {
                    product.setName(updateProduct.getName());
                    product.setPrice(updateProduct.getPrice());
                    product.setUrl(updateProduct.getUrl());
                    product.setYear(updateProduct.getYear());
                    return repository.save(product);
                }).orElseGet(() -> {
            updateProduct.setId(id);
            return repository.save(updateProduct);
        }));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("OK","Query product success", updateProduct)
        );
    }

    //DELETE
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exists=repository.existsById(id);
        if(!exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Query product success","")
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed","Query product fail","")
            );
        }
    }
}
