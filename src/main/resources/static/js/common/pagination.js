export function updatePagination(currentPage, lastPage, total, size, start, end,
    changePageCallback) {
  const pagination = document.querySelector('.pagination');
  const paginationInfo = document.getElementById('pagination-info');

  paginationInfo.textContent = `Showing ${start} to ${end} of ${total} entries`;

  const maxVisiblePages = 10; // 최대 보여줄 페이지 번호 개수
  let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
  let endPage = Math.min(lastPage, startPage + maxVisiblePages - 1);

  if (endPage - startPage + 1 < maxVisiblePages) {
    startPage = Math.max(1, endPage - maxVisiblePages + 1);
  }

  let pages = '';
  for (let i = startPage; i <= endPage; i++) {
    pages += `<li class="page-item ${i === currentPage ? 'active' : ''}">
                <a class="page-link" href="#!" data-page="${i}">${i}</a>
              </li>`;
  }

  pagination.innerHTML = `
    <li class="page-item ${currentPage > 1 ? '' : 'disabled'}">
      <a class="page-link" href="#!" data-page="prev">
        <i class="bi bi-chevron-left"></i>
      </a>
    </li>
    ${pages}
    <li class="page-item ${currentPage < lastPage ? '' : 'disabled'}">
      <a class="page-link" href="#!" data-page="next">
        <i class="bi bi-chevron-right"></i>
      </a>
    </li>
  `;

  const pageLinks = pagination.querySelectorAll('.page-link');
  pageLinks.forEach(link => {
    link.addEventListener('click', function (event) {
      const targetLink = event.target.closest('.page-link');
      if (!targetLink) {
        return;
      }

      const page = targetLink.getAttribute('data-page');
      if (page === 'prev' || page === 'next') {
        changePageCallback(page);
      } else {
        changePageCallback(Number(page));
      }
    });
  });
}

export function changePage(currentPage, setPageCallback) {
  return function (page) {
    if (page === 'prev' && currentPage > 1) {
      setPageCallback(currentPage - 1);
    } else if (page === 'next') {
      setPageCallback(currentPage + 1);
    } else if (typeof page === 'number') {
      setPageCallback(page);
    }
  };
}
