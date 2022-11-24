package com.cg.util;

import com.cg.exception.DataInputException;
import com.cg.model.Avatar;
import com.cg.model.Product;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtil {
    public static final String IMAGE_UPLOAD_FOLDER = "avatar_images";

    public Map buildImageUploadParams(Avatar avatar) {
        if (avatar == null || avatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, avatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(Product product, String publicId) {
        if (product == null)
            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

}
