document.addEventListener('DOMContentLoaded', function () {
    const createdAtInput = document.getElementById('createdAt');
    const date = new Date(createdAtInput.value);
    createdAtInput.value = date.toLocaleString();

    const updatedAtInput = document.getElementById('updatedAt');
    if (updatedAtInput.value) {
        const date = new Date(updatedAtInput.value);
        updatedAtInput.value = date.toLocaleString();
    } else {
        updatedAtInput.value = '';
    }
});
