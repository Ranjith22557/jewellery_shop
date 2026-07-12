// Shared responsive sidebar toggle for mobile/tablet
document.addEventListener('DOMContentLoaded', function () {
    const toggleBtn = document.getElementById('menuToggle');
    const sidebar = document.querySelector('.sidebar');
    const overlay = document.getElementById('sidebarOverlay');

    // Highlight the sidebar link matching the current page
    if (sidebar) {
        const currentPath = window.location.pathname.replace(/\/$/, '') || '/';
        sidebar.querySelectorAll('a').forEach(function (link) {
            const linkPath = link.getAttribute('href').replace(/\/$/, '') || '/';
            if (linkPath === currentPath) {
                link.classList.add('active');
            }
        });
    }

    if (!toggleBtn || !sidebar || !overlay) return;

    function openSidebar() {
        sidebar.classList.add('active');
        overlay.classList.add('active');
    }

    function closeSidebar() {
        sidebar.classList.remove('active');
        overlay.classList.remove('active');
    }

    toggleBtn.addEventListener('click', function () {
        if (sidebar.classList.contains('active')) {
            closeSidebar();
        } else {
            openSidebar();
        }
    });

    overlay.addEventListener('click', closeSidebar);

    // Close sidebar automatically when a nav link is tapped (mobile)
    sidebar.querySelectorAll('a').forEach(function (link) {
        link.addEventListener('click', closeSidebar);
    });
});
