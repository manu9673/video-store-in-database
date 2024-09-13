document.addEventListener('DOMContentLoaded', () => {
    // Form validation for upload page
    const uploadForm = document.querySelector('form[action="/videos/upload"]');
    if (uploadForm) {
        uploadForm.addEventListener('submit', (event) => {
            const title = document.getElementById('title').value.trim();
            const file = document.getElementById('file').files[0];

            if (!title) {
                alert('Please enter a title.');
                event.preventDefault();
            } else if (!file) {
                alert('Please select a file.');
                event.preventDefault();
            }
        });
    }

    // Confirmation for toggle privacy
    const toggleForms = document.querySelectorAll('.toggle-form');
    toggleForms.forEach(form => {
        form.addEventListener('submit', (event) => {
            if (!confirm('Are you sure you want to toggle the privacy status of this video?')) {
                event.preventDefault();
            }
        });
    });

    // Video details interactions
    const video = document.querySelector('.video-details video');
    if (video) {
        video.addEventListener('play', () => {
            console.log('Video is playing');
        });
    }
});
