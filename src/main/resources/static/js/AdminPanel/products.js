$(document).ready(function () {
    loadProducts();
});

function loadProducts() {
    $('#productTable').DataTable({
        ajax: {
            url: '/products',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'name' },
            { data: 'description' },
            { data: 'price' },
            { data: 'quantity' },
            { data: 'category.name' },
            {
                data: 'imageUrl',
                render: function (data) {
                    return data ? `<img src="${data}" alt="Product Image" style="width: 50px; height: 50px;">` : 'No Image';
                }
            },
            {
                data: null,
                render: function (data) {
                    return `
                        <button class="btn btn-warning btn-sm" onclick="openProductModal(${data.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteProduct(${data.id})">Delete</button>
                    `;
                }
            }
        ]
    });
}


function openProductModal(productId = null) {
    $('#productId').val(productId);
    loadCategories();

    if (productId) {
        fetch(`/products/${productId}`)
            .then(response => response.json())
            .then(product => { // arrow funcc
            $('#productName').val(product.name);
            $('#productDescription').val(product.description);
            $('#productPrice').val(product.price);
            $('#productQuantity').val(product.quantity);
            $('#productCategory').val(product.categoryId);
            $('#productModalLabel').text('Edit Product');
        });

    } else {
        $('#productForm')[0].reset();
        $('#productModalLabel').text('Create Product');
    }
    $('#productModal').modal('show');
}

async function saveProduct() {
    const productId = $('#productId').val();
    const formData = new FormData();

    formData.append('name', $('#productName').val());
    formData.append('description', $('#productDescription').val());
    formData.append('price', parseFloat($('#productPrice').val()));
    formData.append('quantity', parseInt($('#productQuantity').val()));
    formData.append('categoryId', $('#productCategory').val());

    const imageFile = $('#productImage')[0].files[0];
    if (imageFile) {
        formData.append('imageFile', imageFile);
    }

    const method = productId ? 'PUT' : 'POST';
    const url = productId ? `/products/${productId}` : '/products';

    try {
        const response = await fetch(url, {
            method: method,
            body: formData
        });

        if (response.ok) {
            $('#productModal').modal('hide');
            $('#productTable').DataTable().ajax.reload();
            showSuccessMessage(productId ? 'Product updated successfully!' : 'Product created successfully!');
        } else {
            console.error('Error saving product:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}


async function deleteProduct(productId) {
    try {
        const response = await fetch(`/products/${productId}`, { method: 'DELETE' });
        if (response.ok) {
            $('#productTable').DataTable().ajax.reload();
            showSuccessMessage('Product deleted successfully!');
        } else {
            console.error('Error deleting product:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}

function showSuccessMessage(message) {
    $('#successMessage').text(message).show();
    setTimeout(() => $('#successMessage').hide(), 5000);
}

async function loadCategories() {
    try {
        const response = await fetch('/categories');
        const categories = await response.json();
        const categorySelect = $('#productCategory');
        categorySelect.empty();
        categorySelect.append('<option value="">Select Category</option>');

        categories.forEach(category => {
            categorySelect.append(`<option value="${category.id}">${category.name}</option>`);
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}
