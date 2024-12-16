document.addEventListener("DOMContentLoaded", function () {

// dropFile 객체를 전역에서 사용 가능하도록 설정
  window.dropFile = {
    // 상품 이미지 파일 처리 (최대 1장)
    handleEventImgFiles1: function (files) {
        const previewContainer1 = document.getElementById("image-previews1");
        previewContainer1.innerHTML = '';  // 기존 미리보기 삭제

      if (files.length > 1) {
        alert("이미지는 1장만 업로드할 수 있습니다.");
        return;  // 1개 이상의 파일을 선택할 수 없도록 함
      }

      Array.from(files).forEach(file => {
        if (file && file.type.startsWith('image')) {
          const reader = new FileReader();
          reader.onload = function (event) {
            const img = document.createElement('img');
            img.src = event.target.result;
            img.classList.add('img-thumbnail');
            previewContainer1.appendChild(img);
          };
          reader.readAsDataURL(file);
        }
      });
    },
      handleEventImgFiles2: function (files) {
          const previewContainer2 = document.getElementById("image-previews2");
          previewContainer2.innerHTML = '';  // 기존 미리보기 삭제

          if (files.length > 1) {
              alert("이미지는 1장만 업로드할 수 있습니다.");
              return;  // 1개 이상의 파일을 선택할 수 없도록 함
          }

          Array.from(files).forEach(file => {
              if (file && file.type.startsWith('image')) {
                  const reader = new FileReader();
                  reader.onload = function (event) {
                      const img = document.createElement('img');
                      img.src = event.target.result;
                      img.classList.add('img-detail');
                      previewContainer2.appendChild(img);
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
          alert(`${maxFiles}개 파일만 업로드할 수 있습니다.`);
          return;
        }

        if (dropAreaId === "drop-image") {
          dropFile.handleThumbnailFiles(files);
        } else if (dropAreaId === "drop-description") {
          dropFile.handleDescriptionFiles(files);
        }
      });
    }
  };

  // `DropFile` 초기화 - 드래그 앤 드롭 기능 설정
  dropFile.setupDragAndDrop("drop-image1", "chooseImage1", "image-previews1", 1);
  dropFile.setupDragAndDrop("drop-image2", "chooseImage2", "image-previews2", 1);

  document.getElementById("eventForm").addEventListener('submit',
      async function (event) {
        event.preventDefault();

        let formData = new FormData(this);

        const eventData = {
            title: formData.get('title'),
            startDate: formData.get('startDate'),
            endDate: formData.get('endDate'),
        }

        formData.append("event", new Blob([JSON.stringify(eventData)],
            {type: "application/json"}));

        // 파일 데이터 추가
        const imageFile = document.getElementById('chooseImage1').files[0];
        if (imageFile)
          formData.append('thumbnail', imageFile);

        const descriptionFile = document.getElementById('chooseImage2').files[0];
          if (descriptionFile)
              formData.append('detail', descriptionFile);

        try {
          const response = await axios.post('/api/v1/events', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
          alert('이벤트가 등록되었습니다.');
        } catch (error) {
          alert('이벤트 등록에 실패했습니다.');
        }
      });
})