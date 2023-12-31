'use strict'
{
    window.addEventListener('DOMContentLoaded', function() {
        const hamburgerIcon = document.getElementById('hamburgerIcon');
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.querySelector('.container');

        sidebar.classList.add('visible');
        mainContent.style.marginLeft = '220px';

        hamburgerIcon.addEventListener('click', () => {
            sidebar.classList.toggle('visible');
            if(sidebar.classList.contains('visible')) {
                mainContent.style.marginLeft = '220px';
            } else {
                mainContent.style.marginLeft = '50px';
            }
        });
    });
}
