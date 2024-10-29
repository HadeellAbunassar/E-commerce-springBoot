$(document).ready(function() {
    const userId = $('#cartContainer').data('user-id');
    loadCartItems(userId);
});

function loadCartItems(userId) {

    $.ajax({
        url: `/api/carts/${userId}/items`,
        method: 'GET',
        success: function(cartItems) {
            // Log the entire cartItems array as a JSON string
            console.log('Cart Items:', JSON.stringify(cartItems, null, 2)); // Pretty-print the JSON

            const cartItemsContainer = $('#cartItemsContainer');
            cartItemsContainer.empty();

            cartItems.forEach(item => {
                const productImage = item.product.imageUrl || '/path/to/default/image.jpg'; // Use a default image if not provided
                const quantity = item.quantity;

                const cartItem = `
                    <div class="cart-item">
                        <img src="${productImage}" alt="${item.product.name}" style="width: 100px; height: auto;" />
                        <h5>${item.product.name}</h5>
                        <p>Price: $${item.product.price} (Quantity: ${quantity})</p>
                        <button class="btn btn-danger" onclick="removeFromCart(${userId}, ${item.product.id})">Remove</button>
                    </div>
                `;
                cartItemsContainer.append(cartItem);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error loading cart items:', error);
            alert('Failed to load cart items. Please try again later.');
        }
    });
}


function removeFromCart(userId, productId) {

    $.ajax({
        url: `/api/carts/${userId}/items/${productId}`, // Use the correct endpoint for deletion
        method: 'DELETE',
        success: function(response) {
            alert('Item removed from cart successfully!');
            loadCartItems(userId); // Refresh the cart items after removal
        },
        error: function(xhr, status, error) {
            console.error('Error removing item from cart:', error);
            alert('Failed to remove item from cart. Please try again.');
        }
    });
}
