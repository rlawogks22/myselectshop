package com.sparta.myselectshop.service;

import com.sparta.myselectshop.Repository.ProductRepository;
import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public static final int MIN_MY_PRICCE = 100;
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productRepository.save(new Product(requestDto));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if(myprice < MIN_MY_PRICCE){
            throw new IllegalArgumentException("유효하지 않은 가격입니다." + MIN_MY_PRICCE + "원 이상으로 설정해주세요");
        }
        Product product = productRepository.findById(id).orElseThrow(()->
                new NullPointerException("해당 상품을 찾을 수 없습니다."));

        product.update(requestDto);
        return new ProductResponseDto(product);
    }
}
