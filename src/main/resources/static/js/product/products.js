import {updatePagination, changePage} from "../common/pagination.js";
import {formatPriceElements} from "../common/format.js";

document.addEventListener('DOMContentLoaded', function () {

  formatPriceElements();

  let searchData = {
    page: 1,
    size: 10,
    sort: document.getElementById('sort').value,
    keyword: ''
  };

  async function productList(searchData) {

    const params = new URLSearchParams({
      page: searchData.page,
      size: searchData.size,
      sort: searchData.sort,
      keyword: searchData.keyword || ''
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

      const changePageCallback = changePage(searchData.page, function (page) {
        if (searchData.page !== page) {
          searchData.page = page;
          productList(searchData);
        }
      });

      updatePagination(searchData.page, last, total, searchData.size, start,
          end, changePageCallback);

    } catch (error) {
      console.error('상품 목록 조회 실패:', error);
      alert('상품 목록 조회에 실패했습니다.');
    }
  }

  productList(searchData);

  document.getElementById('search-input').addEventListener('keypress',
      function (event) {
        if (event.key === 'Enter') {
          event.preventDefault();
          searchData.keyword = event.target.value;
          searchData.page = 1;
          productList(searchData);
        }
      })

  document.getElementById('sort').addEventListener('change', (event) => {
    searchData.sort = event.target.value;
    searchData.page = 1;
    productList(searchData);
  });
});
