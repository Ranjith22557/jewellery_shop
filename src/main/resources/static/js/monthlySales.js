document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/sales/monthlySales')
        .then(response => {
            if (!response.ok) {
                if(response.status === 204) {
                    // No content, clear table or show message
                    document.getElementById('monthlySalesBody').innerHTML = '<tr><td colspan="3" class="text-center">No sales data available</td></tr>';
                    return [];
                }
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const body = document.getElementById('monthlySalesBody');
            body.innerHTML = '';

            if(data.length === 0){
                body.innerHTML = '<tr><td colspan="3" class="text-center">No sales data available</td></tr>';
                return;
            }

            data.forEach(sale => {
                const monthName = new Date(sale.year, sale.month - 1).toLocaleString('default', { month: 'long' });
                const formattedAmount = Number(sale.totalAmount).toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${monthName}</td>
                    <td>${sale.year}</td>
                    <td>${formattedAmount}</td>
                `;

                body.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Fetch error:', error);
            document.getElementById('monthlySalesBody').innerHTML = '<tr><td colspan="3" class="text-center text-danger">Failed to load data</td></tr>';
        });
});
