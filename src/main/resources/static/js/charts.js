document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById('expenseChart');
    if (!ctx) return; // Jika canvas tidak ada, stop (biar gak error di halaman lain)

    // 1. Ambil data JSON dari elemen HTML tersembunyi
    const dataDiv = document.getElementById('expenseData');
    const rawData = dataDiv ? dataDiv.innerText : "[]";
    
    let chartData = [];
    try {
        chartData = JSON.parse(rawData);
    } catch (e) {
        console.error("Gagal parsing data chart:", e);
        chartData = [];
    }

    // 2. Jika data kosong, tampilkan placeholder atau chart kosong
    if (!chartData || chartData.length === 0) {
        chartData = [["No Data", 1]]; // Placeholder
    }

    // 3. Pisahkan Label (Kategori) dan Data (Jumlah)
    // Format data dari Java: [["FOOD", 50000], ["TRANSPORT", 20000]]
    const labels = chartData.map(item => item[0]); 
    const dataValues = chartData.map(item => item[1]);

    // 4. Render Chart
    new Chart(ctx, {
        type: 'doughnut', // Bisa diganti 'pie' atau 'bar'
        data: {
            labels: labels,
            datasets: [{
                label: 'Pengeluaran (Rp)',
                data: dataValues,
                backgroundColor: [
                    '#3b82f6', '#10b981', '#f59e0b', '#ef4444', 
                    '#8b5cf6', '#ec4899', '#6366f1', '#14b8a6'
                ],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
});