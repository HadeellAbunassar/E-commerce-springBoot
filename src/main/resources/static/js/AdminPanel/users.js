$(document).ready(function () {
    loadUsers();
});

function loadUsers() {
    $('#userTable').DataTable({
        ajax: {
            url: '/users',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'username' },
            { data: 'email' },
            {
                data: 'roles',
                render: function (data) {
                    return data.map(role => role.name).join(', ');
                }
            },
            {
                data: null,
                render: function (data) {
                    return `
                        <button class="btn btn-warning btn-sm" onclick="openUserModal(${data.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteUser(${data.id})">Delete</button>
                    `;
                }
            }
        ]
    });
}

function openUserModal(userId = null) {
    $('#UserId').val(userId);

    if (userId) {
        fetch(`/users/${userId}`)
            .then(response => response.json())
            .then(user => {
                $('#UserName').val(user.username);
                $('#UserEmail').val(user.email);
                $('#UserPassword').val('');

                $('input[name="roles"]').prop('checked', false);

                user.roles.forEach(role => {
                    $(`input[name="roles"][value="${role.id}"]`).prop('checked', true);
                });

                $('#UserModalLabel').text('Edit User');
            });
    } else {
        $('#UserForm')[0].reset();
        $('#UserModalLabel').text('Add User');
    }
    $('#UserModal').modal('show');
}

async function saveUser() {
    const userId = $('#UserId').val();
    const roles = $('input[name="roles"]:checked').map(function() {
        return $(this).val(); // Send only the role name (string)
    }).get();


    const userData = {
        username: $('#UserName').val(), // Changed 'name' to 'username' to match User entity
        email: $('#UserEmail').val(),
        password: $('#UserPassword').val(),
        roles: roles
    };

    console.log(JSON.stringify(userData, null, 2)); // Log the data for debugging

    const method = userId ? 'PUT' : 'POST';
    const url = userId ? `/users/${userId}` : '/users';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            $('#UserModal').modal('hide');
            $('#userTable').DataTable().ajax.reload();
            showSuccessMessage(userId ? 'User updated successfully!' : 'User created successfully!');
        } else {
            console.error('Error saving user:', response.statusText);
            const errorText = await response.text(); // Get error message for better debugging
            console.error('Response body:', errorText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}


async function deleteUser(userId) {
    try {
        const response = await fetch(`/users/${userId}`, { method: 'DELETE' });
        if (response.ok) {
            $('#userTable').DataTable().ajax.reload();
            showSuccessMessage('User deleted successfully!');
        } else {
            console.error('Error deleting user:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}

function showSuccessMessage(message) {
    $('#successMessage').text(message).show();
    setTimeout(() => $('#successMessage').hide(), 5000);
}
