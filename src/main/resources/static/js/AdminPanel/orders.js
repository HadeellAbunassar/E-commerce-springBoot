    $(document).ready(function() {
        loadOrders();
    });

    function loadOrders() {
        $('#orderTable').DataTable({
            ajax: {
                url: '/orders',
                dataSrc: function(json) {
                    console.log('Orders Data:', json);
                    return json;
                }
            },
            columns: [
                { data: 'id' },
                { data: 'status' },
                { data: 'totalPrice' },
                { data: 'paymentDetails' },
                { data: 'address' },
                { data: 'city' },
                { data: 'postalCode' },
                { data: 'phoneNumber' },
                {
                    data: 'orderItems',
                    render: function(data) {
                        if (!data || data.length === 0) {
                            return 'No items';
                        }
                        return data.map(item => `Product ID: ${item.productId} (Quantity: ${item.quantity})`).join(', ');
                    }
                },
                {
                    data: null,
                    render: function(data) {
                        return `<button class="btn btn-warning btn-sm" onclick="openOrderModal(${data.id})">Update Status</button>`;
                    }
                }
            ]
        });
    }

    function openOrderModal(orderId) {
        $('#orderId').val(orderId);
        $('#orderModal').modal('show');
    }

    async function updateOrderStatus() {
        const orderId = $('#orderId').val();
        const status = $('#orderStatus').val();

        try {
            const response = await fetch(`/orders/${orderId}?status=${status}`, { method: 'PUT' });
            if (response.ok) {
                $('#orderModal').modal('hide');
                $('#orderTable').DataTable().ajax.reload();
                showSuccessMessage('Order status updated successfully!');
            } else {
                console.error('Error updating order status:', response.statusText);
            }
        } catch (error) {
            console.error('Network error:', error);
        }
    }

    function showSuccessMessage(message) {
        $('#successMessage').text(message).show();
        setTimeout(() => $('#successMessage').hide(), 5000);
    }
