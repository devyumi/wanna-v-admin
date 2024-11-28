import { formatPriceElements } from "../common/format.js";

document.addEventListener('DOMContentLoaded', function () {

  formatPriceElements();

  let searchData = {
    page: 1,
    size: 10,
    sort: document.getElementById('sort').value
  };

  async function productList(searchData) {

    const params = new URLSearchParams({
      page: searchData.page,
      size: searchData.size,
      sort: searchData.sort,
    });

    try {
      const response = await axios.get(`/api/v1/products?${params.toString()}`,
          {
            headers: {
              'Content-Type': 'application/json'
            }
          });

      const {total, last, start, end} = response.data.data;  // 응답에서 필요한 데이터 추출
      const products = response.data.data.products;

      const tbody = document.querySelector('tbody');
      tbody.innerHTML = '';

      products.forEach(product => {
        const row = document.createElement('tr');
        row.classList.add('product-item');

        row.innerHTML = `
          <td class="id">
            <a href="/products/${product.id}">${product.id}</a>
          </td>
          <td class="name">${product.name}</td>
          <td class="final-price price" data-price="${product.finalPrice}">${product.finalPrice}</td>
          <td>${product.category}</td>
          <td class="stock">${product.stock}</td>
          <td class="is-active">${product.isActive ? 'O' : 'X'}</td>
          <td class="create-at">${new Date(product.createdAt).toLocaleString()}</td>
         `;

        tbody.appendChild(row);
      });

      updatePagination(searchData.page, last, total, searchData.size, start, end);

    } catch (error) {
      console.error('상품 목록 조회 실패:', error);
      alert('상품 목록 조회에 실패했습니다.');
    }
  }

  function changePage(page) {
    if (page === 'prev' && searchData.page > 1) {
      searchData.page -= 1;
    } else if (page === 'next' && searchData.page < searchData.last) {
      searchData.page += 1;
    } else if (typeof page === 'number') {
      searchData.page = page;
    }
    productList(searchData);
  }

  function updatePagination(currentPage, lastPage, total, size, start, end) {
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
          changePage(page);
        } else {
          changePage(Number(page));
        }
      });
    });
  }

  productList(searchData);

  document.getElementById('sort').addEventListener('change', (event) => {
    searchData.sort = event.target.value;
    searchData.page = 1;
    productList(searchData);
  });
});
