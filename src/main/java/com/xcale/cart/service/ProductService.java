package com.xcale.cart.service;

import com.xcale.cart.model.ProductDTO;
import com.xcale.cart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    private final ModelMapper mapper;

    public ProductService(ProductRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(it -> mapper.map(it, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
