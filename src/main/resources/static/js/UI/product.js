$(document).ready(function() {
    loadProducts();
});

function loadProducts() {
    $.ajax({
        url: '/products',
        method: 'GET',
        success: function(products) {
            const productContainer = $('#productContainer');
            productContainer.empty();

            products.forEach(product => {
                const productCard = `
                    <div class="product-card">
    <img src="${product.imageUrl || '/imgs/default.jpg'}" class="product-img" alt="${product.name} Image" />
    <div class="product-details">
        <h5>${product.name}</h5>
        <p>${product.description}</p>
        <p class="product-price">${product.price} $</p>
        <div class="quantity-container">
            <label class="quantity-label" for="quantity-${product.id}">Quantity:</label>
            <input type="number" class="quantity-input" id="quantity-${product.id}" value="1" min="1" />
            <button class="btn btn-success btn-sm" onclick="addToCart(${product.id})">Add to Cart</button>
        </div>
    </div>
</div>

                `;
                productContainer.append(productCard);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error loading products:', error);
        }
    });
}

function addToCart(productId) {

    const cartId = $('#productContainer').data('user-id');
    const quantityInput = $(`#quantity-${productId}`);
    const quantity = quantityInput.val();


    $.ajax({
        url: `/api/carts/${cartId}/items/${productId}`,
        method: 'POST',
        data: { quantity: quantity },
        success: function(response) {
            alert('Product added to cart!');
        },
        error: function(xhr, status, error) {
            console.error('Error adding product to cart:', error);
            alert('Failed to add product to cart. Please try again.');
        }
    });
}