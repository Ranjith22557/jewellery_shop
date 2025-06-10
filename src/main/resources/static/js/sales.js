document.addEventListener('DOMContentLoaded', () => {
    const exchangeCheckbox = document.getElementById('exchange');
    const exchangeFields = document.getElementById('exchangeFields');


    exchangeCheckbox.addEventListener('change', () => {
        exchangeFields.style.display = exchangeCheckbox.checked ? 'flex' : 'none';

    });

    // Trigger on page load in case checkbox is pre-checked
    exchangeCheckbox.dispatchEvent(new Event('change'));

    // Update Actual Price
    const type = document.getElementById("itemType");
        const gram = document.getElementById("gram");
        const actualPrice = document.getElementById("actualPrice");
        const remainingQty = document.getElementById("remainingQty");

        type.addEventListener("change", fetchPrice);
        gram.addEventListener("change", fetchPrice);

        function fetchPrice() {
            const selectedType = type.value;
            const selectedGram = gram.value;

            fetch(`/api/sales/totalAmount?type=${encodeURIComponent(selectedType)}&gram=${encodeURIComponent(selectedGram)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch price");
                    }
                    return response.json();
                })
                .then(data => {
                    actualPrice.value = data.totalAmount;
                    remainingQty.value = data.qty;
                })
                .catch(error => console.error("Error fetching price: " + error));
        }

    //Update Total Amount
    const qtyInput = document.getElementById("quantity");
    const rateInput = document.getElementById("rate");
    const totalAmount = document.getElementById("total");
    const netTotalAmountInput = document.getElementById("netTotalAmount");
    const discountInput = document.getElementById("discount");
    const oldPriceInput = document.getElementById("oldPrice");
    const pendingAmountInput = document.getElementById("pendingAmount");
    const paidAmountInput = document.getElementById("paidAmount");


    qtyInput.addEventListener("input",updateTotalAmount);
    rateInput.addEventListener("input",updateTotalAmount);
    oldPriceInput.addEventListener("input",updateTotalAmount);
    discountInput.addEventListener("input",updateTotalAmount);
    paidAmountInput.addEventListener("input",updateTotalAmount);

    function updateTotalAmount(){
        const qty = parseFloat(qtyInput.value) || 0;
        const rate = parseFloat(rateInput.value) || 0;
        const discount = parseFloat(discountInput.value) || 0;
        const oldPrice = parseFloat(oldPriceInput.value) || 0;
        const paidAmount = parseFloat(paidAmountInput.value) || 0;

        const netAmount = ((qty * rate) - oldPrice) || 0;
        const netTotalAmount = (netAmount - discount).toFixed(2);
        const pendingAmount = (netTotalAmount - paidAmount).toFixed(2);

        totalAmount.value = (qty * rate).toFixed(2);

        //update net total amount
        netTotalAmountInput.value = netTotalAmount;
        pendingAmountInput.value = pendingAmount;
    }

    //Save customer
    const form = document.getElementById("salesForm");

    form.addEventListener("submit",function(event) {
    event.preventDefault();

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        //Convert checkbox to boolean
        data.exchange = document.getElementById("exchange").checked;

        //date format
        const rawDate = data.date; // yyyy-MM-dd
        if (rawDate) {
            const [year, month, day] = rawDate.split("-");
            data.date = `${day}-${month}-${year}`; // dd-MM-yyyy
        }

        //qty Validation
        const qty = document.getElementById("quantity").value;
        const remainingQty = document.getElementById("remainingQty").value;

        if(qty > remainingQty){
            alert("Entered quantity exceeds the remaining quantity!");
            return false;
        }

        fetch("/api/sales/saveCustomer", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            .then(response => response.text())
            .then(data => {
                const msgDiv = document.getElementById("successMessage");
                msgDiv.classList.remove("d-none");
                msgDiv.innerText = data;

                form.reset();
            })
            .catch(error => console.error("Error:", error));
    });
});

