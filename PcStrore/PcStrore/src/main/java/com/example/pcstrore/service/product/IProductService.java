package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {
    List<ProductDTO> getAllProduct();
    Product saveWithAvatar (Product product, MultipartFile avatarFile);
    Optional<ProductDTO> getProductDTOById(Long id);

    void deleteProductDTO(long productId);

    List<ProductDTO> getAllProductByDeletedIsFalse();


}
