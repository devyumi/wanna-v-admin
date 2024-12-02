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
                row.classList.add('event-item');

                row.innerHTML = `
                  <td>${event.id}</td>
                  <td><a href="/events/${event.id}">${event.title}</a></td>
                  <td>${event.startDate}</td>
                  <td>${event.endDate}</td>
                  `;

                tbody.appendChild(row);
            });

            const changePageCallback = changePage(searchData.page, function(page) {
                searchData.page = page;
                eventList(searchData);
            });

            updatePagination(searchData.page, last, total, searchData.size, start, end, changePageCallback);

        } catch (error) {
            console.error('유저 조회 실패:', error);
            alert('유저 조회에 실패했습니다.');
        }
    }

    eventList(searchData);
});
