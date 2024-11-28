import {formatPriceElements} from "../common/format.js";

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

      const products = response.data.data.products;

      // 상품 목록을 담을 tbody 요소를 선택
      const tbody = document.querySelector('tbody');

      // 기존 테이블 내용 비우기
      tbody.innerHTML = '';

      // 상품 목록을 반복하며 테이블에 추가
      products.forEach(product => {
        const row = document.createElement('tr');
        row.classList.add('product-item');

        // 상품 데이터 셀 추가
        row.innerHTML = `
        <td class="id">
          <a href="/products/${product.id}" th:text="${product.id}">${product.id}</a>
        </td>
        <td class="name" th:text="${product.name}">${product.name}</td>
        <td class="final-price price" th:data-price="${product.finalPrice}">${product.finalPrice}</td>
        <td th:text="${product.category.name}">${product.category.name}</td>
        <td class="stock" th:text="${product.stock}">${product.stock}</td>
        <td class="is-active" th:text="${product.isActive ? 'O'
            : 'X'}">${product.isActive ? 'O' : 'X'}</td>
        <td class="create-at" th:text="${product.createdAt}">${new Date(
            product.createdAt).toLocaleString()}</td>
      `;

        // tbody에 새로운 행 추가
        tbody.appendChild(row);
      });

    } catch (error) {
      console.error('상품 목록 조회 실패:', error);
      alert('상품 목록 조회에 실패했습니다.');
    }

  }

  productList(searchData);

  document.getElementById('sort').addEventListener('change', (event) => {
  searchData.sort = event.target.value;
  productList(searchData);
});

})