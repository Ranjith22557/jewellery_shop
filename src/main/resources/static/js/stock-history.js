document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/stock/stockList")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("stockTableBody");

            data.forEach(sale => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${sale.type}</td>
                    <td>${sale.gram}</td>
                    <td>${sale.quantity}</td>
                    <td>${sale.remainingQty}</td>
                    <td>${sale.date ||''}</td>
                    <td>${sale.totalAmount || 0}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching stock data:", error);
        });
});
