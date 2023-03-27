class AppPage {
    static BASE_CLOUDIARY_SERVER = "https://res.cloudinary.com/df2stwuvw";
    static BASE_CLOUDIARY_IMAGE_URL = this.BASE_CLOUDIARY_SERVER + "/image/upload";
    static renderProductItem(obj,avatar) {
        return `
        <div class="card mx-3 col-3" style="padding: 0;width: 22%;margin-top: 30px">
  <img src="${this.BASE_CLOUDIARY_IMAGE_URL}/${avatar.fileFolder}/${avatar.fileName}" class="card-img-top" alt="..." style="height: 200px;">
  <div class="card-body">
    <h5 style="text-align: center">${obj.name}</h5>
    <p class="card-text" style="text-align: center">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(obj.price)}</p>
     <button class="btn btn-outline-primary fas fa-cart-plus add-cart" style="margin: 0px 100px;padding: 10px 20px;" data-id="${obj.id}"></button>
  </div>
</div>
        `
    }

    static renderCartItem(obj) {
        return `
            <div id="ci_${obj.id}" class="card mb-3" style="max-width: 100%;height: 120px;background-color: #ca9999;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="${obj.avatar}" class="img-fluid rounded-start" alt="..." style="width:150px;height:100px">
                    </div>
                    <div class="col-md-8">
                      <button class="fa fa-times" data-id = "${obj.id}" type="button"  style="border-radius: 40%;margin-left: 300px;color: red;border-color: white;">
                      </button>
                        <div class="card-body">
                            <h5 class="card-title">${obj.productName}</h5>
                            <p class="card-text">
                                <span class="price">${obj.productPrice}</span>
                                <span>
                                    <button class="btn btn-danger minus" data-id="${obj.id}">-</button>
                                </span>
                                <span>
                                    <input type="text" class="form-control quantity" data-id="${obj.id}" value="${obj.quantity}">
                                </span>
                                <span>
                                    <button class="btn btn-success add" data-id="${obj.id}">+</button>
                                </span>
                                <span class="amount">${obj.amount}</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

}