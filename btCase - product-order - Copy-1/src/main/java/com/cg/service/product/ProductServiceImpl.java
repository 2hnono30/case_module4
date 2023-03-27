package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.Avatar;
import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.model.enums.FileType;
import com.cg.repository.AvatarRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.upload.UploadService;
import com.cg.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private AvatarRepository avatarRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.getAllProduct();
    }

    @Override
    public Product saveWithAvatar(Product product, MultipartFile avatarFile) {
        Avatar avatar = new Avatar();
        String fileType = avatarFile.getContentType();
        assert fileType != null;
        fileType = fileType.substring(0, 5);

        avatar.setFileType(fileType);

        Avatar newAvatar = avatarRepository.save(avatar);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            uploadAndSaveProductImage(avatarFile, newAvatar);
        }
        product.setAvatar(newAvatar);
        return productRepository.save(product);
    }

    @Override
    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.getProductDTOById(id);
    }

    @Override
    public void deleteProductDTO(long productId) {
        productRepository.deleteProductDTO(productId);
    }

    @Override
    public List<ProductDTO> getAllProductByDeletedIsFalse() {
        return productRepository.getAllProductByDeletedIsFalse();
    }


    public void uploadAndSaveProductImage(MultipartFile avatarFile, Avatar avatar) {
        try {
            Map uploadResult = uploadService.uploadImage(avatarFile, uploadUtil.buildImageUploadParams(avatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            avatar.setFileName(avatar.getId() + "." + fileFormat);
            avatar.setFileUrl(fileUrl);
            avatar.setFileFolder(UploadUtil.IMAGE_UPLOAD_FOLDER);
            avatar.setCloudId(avatar.getFileFolder() + "/" + avatar.getId());
            avatarRepository.save(avatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
