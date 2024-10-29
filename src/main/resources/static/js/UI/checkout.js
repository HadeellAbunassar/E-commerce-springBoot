  $(document).ready(function() {
    const userId = $('#regForm').data('user-id');
    });

    var currentTab = 0; // Current tab is set to be the first tab (0)
    showTab(currentTab); // Display the current tab

    function showTab(n) {
        var x = document.getElementsByClassName("tab");
        x[n].style.display = "block";


        if (n == 0) {
            document.getElementById("prevBtn").style.display = "none";
        } else {
            document.getElementById("prevBtn").style.display = "inline";
        }
        if (n == (x.length - 1)) {
            document.getElementById("nextBtn").innerHTML = "Submit";
        } else {
            document.getElementById("nextBtn").innerHTML = "Next";
        }
        fixStepIndicator(n);
    }

 function nextPrev(n) {
    var x = document.getElementsByClassName("tab");
    if (n == 1 && !validateForm()) return false;

    x[currentTab].style.display = "none"; // Hide the current tab
    currentTab = currentTab + n; // Increase or decrease the current tab by 1

    if (currentTab >= x.length) {
        // Prepare to send the data as JSON
        const formData = new FormData(document.getElementById("regForm"));
        const orderData = {
            userId: $('#regForm').data('user-id'),
            address: formData.get('address'),
            city: formData.get('city'),
            postalCode: formData.get('postalCode'),
            phoneNumber: formData.get('phoneNumber'),
            paymentDetails: {
                amount: formData.get('amount'),
                paymentMethod: formData.get('paymentMethod'),
            }
        };

        // Log orderData for debugging
        console.log('Order Data:', orderData);

        // Convert to JSON and send to the backend
        fetch('/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(orderData),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            // Redirect to "Thanks for your order" page
            window.location.href = '/done';
        })
        .catch((error) => {
            console.error('Error:', error);
        });

        return false; // Prevent the form from submitting the default way
    }
    showTab(currentTab); // Otherwise, display the correct tab
}



    function validateForm() {
        var x, y, i, valid = true;
        x = document.getElementsByClassName("tab");
        y = x[currentTab].getElementsByTagName("input");
        for (i = 0; i < y.length; i++) {
            if (y[i].value == "") {
                y[i].className += " invalid";
                valid = false;
            }
        }
        if (valid) {
            document.getElementsByClassName("step")[currentTab].className += " finish";
        }
        return valid;
    }

    function fixStepIndicator(n) {
        var i, x = document.getElementsByClassName("step");
        for (i = 0; i < x.length; i++) {
            x[i].className = x[i].className.replace(" active", "");
        }
        x[n].className += " active";
    }