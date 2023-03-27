class App {
    static DOMAIN = location.origin;
    static DOMAIN_API = "http://localhost:8080";
    static BASER_URL = this.DOMAIN + "/api/";

    static BASE_URL_USER = this.DOMAIN_API + "/api/users";
    static BASE_URL_USER_DELE = this.DOMAIN_API + "/api/users/deleAccList";
    static BASE_URL_LOGIN = this.DOMAIN_API + "/api/auth";
    static BASE_URL_PRODUCT = this.DOMAIN_API + "/api/products";
    static BASE_CLOUDIARY_SERVER = "https://res.cloudinary.com/df2stwuvw";
    static BASE_CLOUDIARY_IMAGE_URL = this.BASE_CLOUDIARY_SERVER + "/image/upload";
    static SCALE_W320_H320_Q100 = "c_limit,w_320,h_320,q_100";
    static BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";

    static AlertMessageEn = class {

        static ERROR_400 = "The operation failed, please check the data again.";
        static ERROR_401 = "Unauthorized - Your access token has expired or is not valid.";
        static ERROR_403 = "Forbidden - You are not authorized to access this resource.";
        static ERROR_404 = "Not Found - The resource has been removed or does not exist";
        static ERROR_500 = "Internal Server Error - The server system is having problems or cannot be accessed.";
    }

    static AlertMessageVi = class {
        static SUCCESS_CREATED = "Thêm sản phẩm thành công !";
        static SUCCESS_UPDATED = "Cập nhật dữ liệu thành công !";
        static SUCCESS_DEACTIVATE = "Xóa sản phẩm thành công !";
        static SUCCESS_DEACTIVATE_USER = "Xóa user thành công !";
        static SUCCESS_RECOVER_USER = "Khôi phục user thành công !";



        static ERROR_IMAGE = "Vui Lòng thêm ảnh sản phẩm !";

        static ERROR_400 = "Thao tác không thành công, vui lòng kiểm tra lại dữ liệu.";
        static ERROR_401 = "Unauthorized - Access Token của bạn hết hạn hoặc không hợp lệ.";
        static ERROR_403 = "Forbidden - Bạn không được quyền truy cập tài nguyên này.";
        static ERROR_404 = "Not Found - Tài nguyên này đã bị xóa hoặc không tồn tại";
        static ERROR_500 = "Internal Server Error - Hệ thống Server đang có vấn đề hoặc không truy cập được.";

    }

    static SweetAlert = class {

        static showAlertSuccess(t) {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: t,
                showConfirmButton: false,
                timer: 2000,
                // position: 'bottomLeft', icon: 'success', title: t, showConfirmButton: false, timer: 1500
            })
        }

        static showAlertError(t) {
            Swal.fire({
                position: 'bottomLeft',
                icon: 'error',
                title: t,
                showConfirmButton: true
            })
        }

        static showSuspendedConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có muốn xóa sản phẩm này ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
            })
        }

        static showSuspendedConfirmDialogUser() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có muốn xóa tài khoản này ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
            })
        }

        static showSuspendedConfirmDialogRecoverUser() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có muốn khôi phục tài khoản này ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
            })
        }

        static showSuspendedConfirmDialogProCard(cartItemId) {
            return Swal.fire({
                text: "Bạn chắc chắn muốn bỏ sản phẩm này ra khỏi giỏ hàng?",
                icon: 'information',
                showCancelButton: true,
                cancelButtonText: "Hủy",
                confirmButtonColor: '#d33',
                confirmButtonText: 'Đồng ý'
            }).then((result) => {
                if (result.isConfirmed) {
                    removeCartItem(cartItemId);
                }
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'OK',
                position: 'bottomLeft',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'bottomLeft',
                timeout: 2500,
                message: m
            });
        }
    }

    static renderRowUser(obj) {
        let str = `
            <tr id="tr_${obj.id}">
                <td class="text-center">${obj.id}</td>
                <td class="text-center">${obj.fullName}</td>
                <td class="text-center">${obj.email}</td>
                <td class="text-center">${obj.phone}</td>
                <td class="text-center">${obj.locationRegion.address}</td>
                <td class="text-center">${obj.roleDTO.code}</td>               
                <td class="text-center">
                      <a class="btn btn-outline-secondary edit" data-id="${obj.id}" title="" data-bs-toggle="tooltip" data-bs-original-title="Edit">
                            <i class="fas fa-edit"></i>
                      </a>
                </td>
                <td class="text-center">
                      <a class="btn btn-outline-danger delete" data-id="${obj.id}" title="" data-bs-toggle="tooltip" data-bs-original-title="Delete">
                            <i class="fas fa-trash-alt"></i>
                      </a>
                </td>

            </tr>
        `;

        return str;
    }

    static renderRowUserDeleList(obj) {
        let str = `
            <tr id="tr_${obj.id}">
                <td class="text-center">${obj.id}</td>
                <td class="text-center">${obj.fullName}</td>
                <td class="text-center">${obj.email}</td>
                <td class="text-center">${obj.phone}</td>
                <td class="text-center">${obj.locationRegion.address}</td>
                <td class="text-center">${obj.roleDTO.code}</td>               
                <td class="text-center">
                      <a class="btn btn-outline-secondary recover" data-id="${obj.id}" title="" data-bs-toggle="tooltip" data-bs-original-title="Recover">
                            <i class="mdi mdi-autorenew"></i>
                      </a>
                </td>
            </tr>
        `;

        return str;
    }

    static renderRowProduct(obj, avatar) {
        let str = `
            <tr id="tr_${obj.id}">
                <td class="text-center">${obj.id}</td>
                <td class="text-center num-space ">
                    <img src="${this.BASE_CLOUDIARY_IMAGE_URL}/${this.BASE_SCALE_IMAGE}/${avatar.fileFolder}/${avatar.fileName}" alt="">
                </td>
                <td class="text-center">${obj.name}</td>
                <td class="text-end">${obj.amount}</td>
                <td class="text-end">${obj.price}</td>
                <td class="text-center">${obj.description}</td>
             
                <td class="text-center">
                    <a class="btn btn-outline-secondary edit" data-id="${obj.id}" title="" data-bs-toggle="tooltip"  data-bs-original-title="Edit" data-avatar-folder="${avatar.fileFolder}" data-avatar-file-name="${avatar.fileName}">
                        <i class="fas fa-edit"></i>
                    </a>
                </td>
                <td class="text-center">
                    <a class="btn btn-outline-danger delete" data-id="${obj.id}" title="" data-bs-toggle="tooltip" data-bs-original-title="Delete">
                        <i class="fas fa-trash-alt"></i>
                    </a>
                </td>
            </tr>
        `;

        return str;
    }
}

class User {
    constructor(id, fullName, username, phone, address, role, locationRegion) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.locationRegion = locationRegion;
    }
}

class Product {
    constructor(id, name, amount, price, description, avatar) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.avatar = avatar;
    }
}

class Avatar {
    constructor(fileFolder, fileName, fileUrl) {
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Role {
    constructor(id = 2, name = "USER") {
        this.id = id;
        this.name = name;
    }
}



