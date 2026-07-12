// Toggle password visibility on the signup page
document.addEventListener('DOMContentLoaded', function () {
    const toggle = document.querySelector('.toggle-eye');
    const passwordInput = document.getElementById('password');

    if (!toggle || !passwordInput) return;

    toggle.addEventListener('click', function () {
        const isHidden = passwordInput.getAttribute('type') === 'password';
        passwordInput.setAttribute('type', isHidden ? 'text' : 'password');
        toggle.textContent = isHidden ? '🙈' : '👁️';
    });
});
