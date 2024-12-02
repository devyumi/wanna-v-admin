import { updatePagination, changePage } from "../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
    };

    async function userList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
        });

        try {
            const response = await axios.get(`/api/v1/users?${params.toString()}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const { total, last, start, end } = response.data.data;
            const users = response.data.data.users;


            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';

            users.forEach(user => {
                const row = document.createElement('tr');
                row.classList.add('user-item');

                row.innerHTML = `
                  <td class="id">${user.id}</td>
                  <td>
                  <a href="/users/${user.id}">${user.username}</a>
                  </td>
                  <td class="id">${user.email}</td>
                  <td class="id">${user.point}</td>
                  <td class="id">${user.code}</td>
                  <td class="id">${user.consent}</td>
                  `;

                tbody.appendChild(row);
            });

            const changePageCallback = changePage(searchData.page, function(page) {
                searchData.page = page;
                userList(searchData);
            });

            updatePagination(searchData.page, last, total, searchData.size, start, end, changePageCallback);

        } catch (error) {
            console.error('유저 조회 실패:', error);
            alert('유저 조회에 실패했습니다.');
        }
    }

    userList(searchData);
});
