class AppPage {
    static BASE_CLOUDIARY_SERVER = "https://res.cloudinary.com/df2stwuvw";
    static BASE_CLOUDIARY_IMAGE_URL = this.BASE_CLOUDIARY_SERVER + "/image/upload";
    static renderProductItem(obj,avatar) {
        return `
        <div class="card mx-3 col-3" style="padding: 0;width: 22%">
  <img src="${this.BASE_CLOUDIARY_IMAGE_URL}/${avatar.fileFolder}/${avatar.fileName}" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 style="text-align: center">${obj.name}</h5>
    <p class="card-text" style="text-align: center">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(obj.price)}</p>
    <button style="margin: 0px 80px;background-color: #0dcaf0" type="button" class="btn btn-cart">Thêm Vào Giỏ</button>
  </div>
</div>
        `
    }
}