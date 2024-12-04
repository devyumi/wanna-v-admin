import { updatePagination, changePage } from "../../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
    };

    async function eventList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
        });

        try {
            const response = await axios.get(`/api/v1/events?${params.toString()}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const { total, last, start, end } = response.data.data;
            const events = response.data.data.events;


            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';

            events.forEach(event => {
                const row = document.createElement('tr');

                row.innerHTML = `
                  <td class="text-align: center">${event.id}</td>
                  <td class="text-align: center"><a href="/events/${event.id}">${event.title}</a></td>
                  <td class="text-align: center">${event.startDate}</td>
                  <td class="text-align: center">${event.endDate}</td>
                  `;

                tbody.appendChild(row);
            });

            const changePageCallback = changePage(searchData.page, function(page) {
                searchData.page = page;
                eventList(searchData);
            });

            updatePagination(searchData.page, last, total, searchData.size, start, end, changePageCallback);

        } catch (error) {
            console.error('이벤트 조회 실패:', error);
            alert('이벤트 조회에 실패했습니다.');
        }
    }

    eventList(searchData);
});
