$(document).ready( function () {
   loadCategories();
});


function loadCategories() {
    $('#categoryTable').DataTable({
        ajax: {
            url: '/categories',
             dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'name' },
            {
                data: null,
                render: function (data) {
                    return `
                        <button class="btn btn-warning btn-sm" onclick="openCategoryModal(${data.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteCategory(${data.id})">Delete</button>
                    `;
                }
            }
        ]
    });
}

function openCategoryModal(categoryId = null) {
    $('#categoryId').val(categoryId);

    if (categoryId) {
        fetch(`/categories/${categoryId}`)
            .then(response => response.json())
            .then(category => { // arrow funcc
            $('#categoryName').val(category.name);
            $('#categoryModalLabel').text('Edit Category');
        });

    } else {
        $('#categoryForm')[0].reset();
        $('#categoryModalLabel').text('Create Category');
    }
    $('#categoryModal').modal('show');
}

async function saveCategory() {
    const categoryId = $('#categoryId').val();
    const CategoryData = {
        name: $('#categoryName').val()
};

    const method = categoryId ? 'PUT' : 'POST';
    const url = categoryId ? `/categories/${categoryId}` : '/categories';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(CategoryData)
        });

        if (response.ok) {
            $('#categoryModal').modal('hide');
            $('#categoryTable').DataTable().ajax.reload();
            showSuccessMessage(categoryId ? 'Category updated successfully!' : 'Category created successfully!');
        } else {
            console.error('Error saving Category:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}

async function deleteCategory(categoryId) {
    try {
        const response = await fetch(`/categories/${categoryId}`, { method: 'DELETE' });
        if (response.ok) {
            $('#categoryTable').DataTable().ajax.reload();
            showSuccessMessage('Category deleted successfully!');
        } else {
            console.error('Error deleting Category:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
    }
}

function showSuccessMessage(message) {
    $('#successMessage').text(message).show();
    setTimeout(() => $('#successMessage').hide(), 5000);
}

