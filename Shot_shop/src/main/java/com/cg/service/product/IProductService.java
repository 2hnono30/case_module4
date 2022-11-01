package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    List<ProductDTO> getAllProduct();
    Product saveWithAvatar (Product product, MultipartFile avatarFile);
    List<ProductDTO> findProductByValue(String query);
//    List<ProductDTO> findProductDTOByTitle(String keySearch);
//    List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown);
}
