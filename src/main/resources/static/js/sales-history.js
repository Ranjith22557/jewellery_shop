document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/sales/salesList")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("salesTableBody");

            data.forEach(sale => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${sale.customerName}</td>
                    <td>${sale.phoneNo}</td>
                    <td>${sale.itemType}</td>
                    <td>${sale.gram}</td>
                    <td>${sale.quantity}</td>
                    <td>${sale.date ||''}</td>
                    <td>${sale.total || 0}</td>
                    <td>${sale.paidAmount || 0}</td>
                    <td>${sale.pendingAmount || 0}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching sales data:", error);
        });
});
