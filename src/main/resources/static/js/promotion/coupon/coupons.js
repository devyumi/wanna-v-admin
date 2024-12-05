import { updatePagination, changePage } from "../../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
        type: '',
        active: '',
    };

    async function couponList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
            type: searchData.type,
            active: searchData.active,
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
                  <td style="text-align: center;">${coupon.id}</td>
                  <td style="text-align: center;"><a href="/coupons/${coupon.id}">${coupon.name}</a></td>
                  <td style="text-align: center;">${coupon.code}</td>
                  <td style="text-align: center;">${coupon.type}</td>
                  <td style="text-align: center;">${coupon.discountAmount ? coupon.discountAmount : ''}</td>
                  <td style="text-align: center;">${coupon.discountRate ? coupon.discountRate : ''}</td>
                  <td style="text-align: center;">${coupon.active}</td>              
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

    const activeFilter = document.getElementById('activeFilter');
    activeFilter.addEventListener('change', function () {
        searchData.active = this.value;
        searchData.page = 1;
        couponList(searchData);
    });

    couponList(searchData);
});
