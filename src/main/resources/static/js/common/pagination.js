export function updatePagination(currentPage, lastPage, total, size, start, end, changePageCallback) {
  const pagination = document.querySelector('.pagination');
  const paginationInfo = document.getElementById('pagination-info');

  paginationInfo.textContent = `Showing ${start} to ${end} of ${total} entries`;

  let pages = '';
  for (let i = 1; i <= lastPage; i++) {
    pages += `<li class="page-item ${i === currentPage ? 'active' : ''}">
              <a class="page-link" href="#!" data-page="${i}">${i}</a>
            </li>`;
  }

  pagination.innerHTML = `
    <li class="page-item ${currentPage > 1 ? '' : 'disabled'}">
      <a class="page-link" href="#!" data-page="prev">Previous</a>
    </li>
    ${pages}
    <li class="page-item ${currentPage < lastPage ? '' : 'disabled'}">
      <a class="page-link" href="#!" data-page="next">Next</a>
    </li>
  `;

  const pageLinks = pagination.querySelectorAll('.page-link');
  pageLinks.forEach(link => {
    link.addEventListener('click', function (event) {
      const page = event.target.getAttribute('data-page');
      if (page === 'prev' || page === 'next') {
        changePageCallback(page);
      } else {
        changePageCallback(Number(page));
      }
    });
  });
}

export function changePage(currentPage, setPageCallback) {
  return function(page) {
    if (page === 'prev' && currentPage > 1) {
      setPageCallback(currentPage - 1);
    } else if (page === 'next') {
      setPageCallback(currentPage + 1);
    } else if (typeof page === 'number') {
      setPageCallback(page);
    }
  };
}
