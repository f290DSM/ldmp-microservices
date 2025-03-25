package dev.sdras.microservices.core.product.application;

import dev.sdras.microservices.core.product.domain.Product;
import dev.sdras.microservices.core.product.utils.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ServiceUtil serviceUtil;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Integer id) {
        return Product.builder()
                .id(id)
                .name("iPad Pro M4")
                .weight(500)
                .serviceAddress(serviceUtil.getServiceAddress())
                .build();
    }
}
