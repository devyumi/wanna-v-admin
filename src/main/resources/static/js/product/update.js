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

  // dropFile 객체를 전역에서 사용 가능하도록 설정
  window.dropFile = {
    // 상품 이미지 파일 처리 (최대 1장)
    handleProductImgFiles: function (files) {
      const previewContainer = document.getElementById("image-previews");
      previewContainer.innerHTML = '';  // 기존 미리보기 삭제

      if (files.length > 1) {
        alert("상품 이미지는 1장만 업로드할 수 있습니다.");
        return;  // 1개 이상의 파일을 선택할 수 없도록 함
      }

      Array.from(files).forEach(file => {
        if (file && file.type.startsWith('image')) {
          const reader = new FileReader();
          reader.onload = function (event) {
            const img = document.createElement('img');
            img.src = event.target.result;
            img.classList.add('img-thumbnail');
            previewContainer.appendChild(img);
          };
          reader.readAsDataURL(file);
        }
      });
    },

    // 상품 설명 이미지 처리 (최대 3장)
    handleDescriptionFiles: function (files) {
      const previewContainer = document.getElementById("description-previews");
      previewContainer.innerHTML = '';  // 기존 미리보기 삭제

      if (files.length > 3) {
        alert("상품 설명 이미지는 최대 3장까지 업로드할 수 있습니다.");
        return;  // 3개 이상의 파일을 선택할 수 없도록 함
      }

      Array.from(files).forEach(file => {
        if (file && file.type.startsWith('image')) {
          const reader = new FileReader();
          reader.onload = function (event) {
            const img = document.createElement('img');
            img.src = event.target.result;
            img.classList.add('img-thumbnail');
            previewContainer.appendChild(img);
          };
          reader.readAsDataURL(file);
        }
      });
    },

    // 드래그 앤 드롭 처리
    setupDragAndDrop: function (dropAreaId, fileInputId, previewContainerId,
        maxFiles) {
      const dropArea = document.getElementById(dropAreaId);
      const previewContainer = document.getElementById(previewContainerId);
      const fileInput = document.getElementById(fileInputId);

      dropArea.addEventListener("dragenter", function (e) {
        e.preventDefault();
        dropArea.classList.add("highlight");
      });

      dropArea.addEventListener("dragover", function (e) {
        e.preventDefault();
      });

      dropArea.addEventListener("dragleave", function (e) {
        e.preventDefault();
        dropArea.classList.remove("highlight");
      });

      dropArea.addEventListener("drop", function (e) {
        e.preventDefault();
        dropArea.classList.remove("highlight");
        const files = e.dataTransfer.files;
        fileInput.files = files; // 드래그한 파일을 input에 설정

        if (files.length > maxFiles) {
          alert(`최대 ${maxFiles}개의 파일만 업로드할 수 있습니다.`);
          return;
        }

        if (dropAreaId === "drop-image") {
          dropFile.handleThumbnailFiles(files);  // 상품 이미지 파일 처리
        } else if (dropAreaId === "drop-description") {
          dropFile.handleDescriptionFiles(files);  // 설명 이미지 파일 처리
        }
      });
    }
  };

  // `DropFile` 초기화 - 드래그 앤 드롭 기능 설정
  dropFile.setupDragAndDrop("drop-image", "chooseImage", "image-previews", 1);  // 상품 이미지는 1개만
  dropFile.setupDragAndDrop("drop-description", "chooseDescription",
      "description-previews", 3);  // 설명 이미지는 최대 3개

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
  btnModify.addEventListener('click', async function (event) {
    event.preventDefault();

    // 폼 데이터 준비
    const formData = new FormData(form);

    const productUpdateData = {
      name: formData.get('name'),
      costPrice: formData.get('cost-price'),
      sellingPrice: formData.get('selling-price'),
      discountRate: formData.get('discount-rate'),
      category: formData.get('category'),
      stock: formData.get('stock'),
      isActive: formData.get('isActive') === 'active'
    }

    formData.append("product",
        new Blob([JSON.stringify(productUpdateData)],
            {type: "application/json"}));

    // 상품 이미지가 선택되었을 경우만 formData에 추가
    const imageFile = document.getElementById('chooseImage').files[0];
    if (imageFile) {
      formData.append('updateImage', imageFile);
    } else {
      formData.append('updateImage', null);  // 이미지가 없으면 null로 추가
    }

    // 상품 설명 이미지가 선택되었을 경우만 formData에 추가
    const descriptionFiles = document.getElementById('chooseDescription').files;
    if (descriptionFiles.length > 0) {
      for (let i = 0; i < descriptionFiles.length; i++) {
        formData.append('updateDescription' + i, descriptionFiles[i]);  // 각 파일마다 다른 키
      }
    } else {
      formData.append('updateDescription', null);  // 설명 이미지가 없으면 null로 추가
    }

    try {
      const response = await axios.patch(`/api/v1/products/${productId}`,
          formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
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
