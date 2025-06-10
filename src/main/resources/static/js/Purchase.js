document.getElementById("productForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = {
        type: document.getElementById("type").value,
        quantity: document.getElementById("quantity").value,
        price: document.getElementById("price").value,
        gram: document.getElementById("gram").value,
        totalAmount: document.getElementById("totalAmount").value,
        date : document.getElementById("date").value
    };

    //date format
    const rawDate = formData.date; // yyyy-MM-dd
    if (rawDate) {
        const [year, month, day] = rawDate.split("-");
        formData.date = `${day}-${month}-${year}`; // dd-MM-yyyy
    }

    fetch("/api/saveProduct", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.text())
    .then(data => {
        const msgDiv = document.getElementById("successMessage");
        msgDiv.classList.remove("d-none");
        msgDiv.innerText = data;

        document.getElementById("productForm").reset();
    })
    .catch(error => console.error("Error:", error));
});

//Update Total Amount
document.addEventListener("DOMContentLoaded",function(){
    const priceInput =  document.getElementById("price");
    const quantityInput = document.getElementById("quantity");
    const totalAmount = document.getElementById("totalAmount");

    function updateTotal(){
        const price = parseFloat(priceInput.value) || 0;
        const qty = parseInt(quantityInput.value) || 0;
        totalAmount.value = (price * qty).toFixed(2);
    }

    priceInput.addEventListener("input",updateTotal);
    quantityInput.addEventListener("input",updateTotal);
});