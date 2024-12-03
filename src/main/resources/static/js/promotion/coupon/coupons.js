import { updatePagination, changePage } from "../../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
        type: '',
    };

    async function couponList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
            type: searchData.type,
        });

        try {
            const response = await axios.get(`/api/v1/coupons?${params.toString()}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const { total, last, start, end } = response.data.data;
            const coupons = response.data.data.coupons;


            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';

            coupons.forEach(coupon => {
                const row = document.createElement('tr');

                row.innerHTML = `
                  <td class="text-align: center">${coupon.id}</td>
                  <td class="text-align: center"><a href="/coupons/${coupon.id}">${coupon.name}</a></td>
                  <td class="text-align: center">${coupon.code}</td>
                  <td class="text-align: center">${coupon.type}</td>
                  <td class="text-align: center">${coupon.discountAmount ? coupon.discountAmount : ''}</td>
                  <td class="text-align: center">${coupon.discountRate ? coupon.discountRate : ''}</td>
                  <td class="text-align: center">${coupon.active}</td>              
                  `;

                tbody.appendChild(row);
            });

            const changePageCallback = changePage(searchData.page, function(page) {
                searchData.page = page;
                eventList(searchData);
            });

            updatePagination(searchData.page, last, total, searchData.size, start, end, changePageCallback);

        } catch (error) {
            console.error('쿠폰 조회 실패:', error);
            alert('쿠폰 조회에 실패했습니다.');
        }
    }

    const typeFilter = document.getElementById('typeFilter');
    typeFilter.addEventListener('change', function () {
        searchData.type = this.value;
        searchData.page = 1;
        couponList(searchData);
    });

    couponList(searchData);
});
