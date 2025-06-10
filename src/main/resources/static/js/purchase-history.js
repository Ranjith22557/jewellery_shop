document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/purchaseHistory")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("purchaseTableBody");

            data.forEach(sale => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${sale.type}</td>
                    <td>${sale.gram}</td>
                    <td>${sale.quantity}</td>
                    <td>${sale.remainingQty}</td>
                    <td>${sale.date ||''}</td>
                    <td>${sale.totalAmount || 0}</td>
                    <td style ="${!sale.isActive ? 'color :red; font-weight: bold;' :''}">
                        ${sale.isActive}
                    </td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching purchase data:", error);
        });
});
