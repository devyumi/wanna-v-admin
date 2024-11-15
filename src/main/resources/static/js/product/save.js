document.addEventListener("DOMContentLoaded", function () {

  document.getElementById("productForm").addEventListener('submit',
      async function (event) {
        event.preventDefault();

        const formData = new FormData(this);
        const productData = {
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
          const response = await axios.post('/api/products', productData, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          console.log('상품 등록 성공:', response.data);
          alert('상품이 등록되었습니다.');
        } catch (error) {
          console.error('상품 등록 실패:', error);
          alert('상품 등록에 실패했습니다.');
          console.log(productData)
        }
      })
})