document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("salesTableBody");
    const modalElement = document.getElementById("editPendingModal");
    const modal = new bootstrap.Modal(modalElement);
    const editForm = document.getElementById("editForm");

    fetch("/api/sales/salesList")
        .then(response => response.json())
        .then(data => {
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
                    <td><a href="/api/sales/print/${sale.id}" class="btn btn-sm btn-outline-primary" target="_blank">Print</a>
                    </td>
                    <td>
                        <button type="button"
                        class="btn btn-sm btn-outline-danger edit-btn"
                        data-id="${sale.id}"
                        data-pending="${sale.pendingAmount || 0}">
                        Edit</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error fetching sales data:", error);
        });

        //Show value in edit popup
        tableBody.addEventListener("click", e=>{
            if(e.target.classList.contains("edit-btn")){
                const id = e.target.getAttribute("data-id");
                const pendingAmount = e.target.getAttribute("data-pending");

                document.getElementById("editPendingId").value = id;
                document.getElementById("editPendingAmount").value = pendingAmount;

                modal.show();
            }
        });

        //Update edited value
        editForm.addEventListener("submit",function(e) {
            e.preventDefault();

            const id = document.getElementById("editPendingId").value;
            const pendingAmount = parseFloat(document.getElementById("editPendingAmount").value);

            fetch("/api/sales/edit", {
                method :"POST",
                headers : {"Content-type" : "application/json" },
                body : JSON.stringify({id : id,pendingAmount: pendingAmount})
            })
            .then(response => {
                if(response.ok){
                    alert("Amount updated successfully");
                    window.location.reload();
                }else{
                    alert("Failed to update.");
                }
            })
            .catch(err => {
                console.error("Update error:"+err);
                alert("Error Updating amount.");
            });
        });
});
