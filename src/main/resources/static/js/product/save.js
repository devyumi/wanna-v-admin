document.addEventListener("DOMContentLoaded", function () {

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

  document.getElementById("productForm").addEventListener('submit',
      async function (event) {
        event.preventDefault();

        let formData = new FormData(this);

        const productData = {
          name: formData.get('name'),
          costPrice: formData.get('cost-price'),
          sellingPrice: formData.get('selling-price'),
          discountRate: formData.get('discount-rate'),
          category: formData.get('category'),
          stock: formData.get('stock'),
          isActive: formData.get('is-active') === 'active'
        }

        formData.append("product", new Blob([JSON.stringify(productData)],
            {type: "application/json"}));

        // 파일 데이터 추가
        const imageFile = document.getElementById('chooseImage').files[0];
        if (imageFile) {
          formData.append('image', imageFile);
        }

        const descriptionFiles = document.getElementById(
            'chooseDescription').files;
        for (let i = 0; i < descriptionFiles.length; i++) {
          formData.append('description' + i, descriptionFiles[i]);  // 각 파일마다 다른 키
        }

        try {
          const response = await axios.post('/api/v1/products', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
          console.log('상품 등록 성공:', response.data);
          alert('상품이 등록되었습니다.');
        } catch (error) {
          console.error('상품 등록 실패:', error);
          alert('상품 등록에 실패했습니다.');
        }
      });
})