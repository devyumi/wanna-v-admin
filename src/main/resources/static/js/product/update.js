document.addEventListener("DOMContentLoaded", function () {

  // 상품 ID 가져오기
  const productId = document.getElementById('productId').innerText.replace('# ',
      '');

  // 상품 수정 정보
  const form = document.getElementById('productUpdateForm');

  // 모달창에서의 수정 재확인 버튼
  const btnModify = document.getElementById('btn-modify');

  // 모달창
  const modalElement = document.getElementById('confirmModal');
  const modal = new bootstrap.Modal(modalElement);

  // 모달 백드롭 제거 함수
  function hideModalWithCleanup(modal) {
    modal.hide();
    const backdrops = document.querySelectorAll('.modal-backdrop');
    backdrops.forEach((backdrop) => backdrop.remove());
  }

  /**
   * 상품 수정 버튼 클릭 시 submit 이벤트 방지
   */
  const btnSave = document.querySelector(
      '.btn-primary[data-bs-toggle="modal"]');
  btnSave.addEventListener('click', function (event) {
    event.preventDefault(); // 기본 폼 제출 동작 방지
    const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
    modal.show(); // 모달 열기
  });

  /**
   * 폼 제출 이벤트 처리
   */
  form.addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 폼 제출 동작 방지
  });

  /**
   * 모달에서 수정 버튼 클릭 시 실제로 수정 요청
   */
  btnModify.addEventListener('click', async function () {
    // 폼 데이터 준비
    const formData = new FormData(form);

    const productUpdateData = {
      name: formData.get('name'),
      costPrice: formData.get('cost-price'),
      sellingPrice: formData.get('selling-price'),
      discountRate: formData.get('discount-rate'),
      category: formData.get('category'),
      stock: formData.get('stock'),
      isActive: formData.get('isActive') === 'active',
      // image: formData.get('image'),
      // description: formData.get('description')
      image: ['이미지'],
      description: ['설명']
    }

    try {
      const response = await axios.patch(`/api/v1/products/${productId}`,
          productUpdateData, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
      console.log('상품 수정 성공:', response.data);
      alert('상품이 수정되었습니다.');

      /**
       * 수정 후 상품 상세 페이지로 리다이렉트
       */
      window.location.href = `/products/${productId}`;
    } catch (error) {
      console.error('상품 수정 실패:', error);
      alert('상품 수정에 실패했습니다.');
      hideModalWithCleanup(modal);
    }
  });

});
