let index = 0;

function addNewProduct() {
    //lay du lieu
    let name = $('#name').val();
    let price = $('#price').val();
    let quantity = $('#quantity').val();
    let description = $('#description').val();
    let newProduct = {
        name: name,
        price: price,
        quantity: quantity,
        description: description
    };
    // goi ajax
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newProduct),
        //tên API
        url: "http://localhost:8080/api/products",
        //xử lý khi thành công
        success: function () {
            getProduct();
        }

    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function editProduct(id) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/products/${id}`,
        //xử lý khi thành công
        success: function (data) {
            $('#name').val(data.name);
            $('#price').val(data.price);
            $('#quantity').val(data.quantity);
            $('#description').val(data.description);
            index = data.id;
            document.getElementById("form").hidden = false;
            document.getElementById("form-button").onclick = function () {
                editProduct1()
            };
        }
    });
}

function editProduct1() {
    //lay du lieu
    let name = $('#name').val();
    let price = $('#price').val();
    let quantity = $('#quantity').val();
    let description = $('#description').val();
    let newProduct = {
        name: name,
        price: price,
        quantity: quantity,
        description: description
    };
    // goi ajax
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(newProduct),
        //tên API
        url: `http://localhost:8080/api/products/${index}`,
        //xử lý khi thành công
        success: function () {
            getProduct()
        }
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function getProduct() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/products`,
        //xử lý khi thành công
        success: function (data) {
            // hien thi danh sach o day
            let content = '<tr>\n' +
                '<th>ProductName</th>\n' +
                '<th>Price</th>\n' +
                '<th>Quantity</th>\n' +
                '<th>Desciption</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayProduct(data[i]);
            }
            document.getElementById("productList").innerHTML = content;
            document.getElementById("form").hidden = true;
        }
    });
}

function deleteProduct(id) {
    $.ajax({
        type: "DELETE",
        //tên API
        url: `http://localhost:8080/api/products/${id}`,
        //xử lý khi thành công
        success: function () {
            getProduct()
        }
    });
}

function searchProduct() {
    let search = document.getElementById("search").value;
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/products/search?search=${search}`,
        //xử lý khi thành công
        success: function (data) {
            // hien thi danh sach o day
            let content = '<tr>\n' +
                '<th>ProductName</th>\n' +
                '<th>Price</th>\n' +
                '<th>Quantity</th>\n' +
                '<th>Desciption</th>\n' +
                '<th colspan="2">Action</th>\n' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += displayProduct(data[i]);
            }
            document.getElementById('productList').innerHTML = content;
            document.getElementById("searchForm").reset()
        }
    });
    event.preventDefault();
}

// $(document).ready(function () {
//     //sư kiện nào thực hiện Ajax
//     $('.deleteProduct').click(function (event) {
//         //lay du lieu
//         let a = $(this);
//         let productId = a.attr("href");
//         // goi ajax
//         $.ajax({
//             type: "DELETE",
//             //tên API
//             url: `http://localhost:8080/api/products/${productId}`,
//             //xử lý khi thành công
//             success: function () {
//                 a.parent().parent().remove();
//             }
//
//         });
//         //chặn sự kiện mặc định của thẻ
//         event.preventDefault();
//     });
// })

function displayProduct(product) {
    return `<tr><td >${product.name}</td><td>${product.price}</td><td >${product.quantity}</td><td >${product.description}</td>
                    <td><button class="btn btn-danger" onclick="deleteProduct(${product.id})">Delete</button></td>
                    <td><button class="btn btn-warning" onclick="editProduct(${product.id})">Edit</button></td>
<!--                    <td><a class="deleteProduct" href="${product.id}">Delete</a></td></tr>-->`;
}

function displayFormCreate() {
    document.getElementById("form").reset()
    document.getElementById("form").hidden = false;
    document.getElementById("form-button").onclick = function () {
        addNewProduct();
    }
}

getProduct()