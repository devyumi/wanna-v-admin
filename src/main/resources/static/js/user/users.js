import { updatePagination, changePage } from "../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
        username: '',
    };

    async function userList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
            username: searchData.username,
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
                  <td style="text-align: center">${user.id}</td>
                  <td style="text-align: center">
                  <a href="/users/${user.id}">${user.username}</a>
                  </td>
                  <td style="text-align: center">${user.email}</td>
                  <td style="text-align: center">${user.point}</td>
                  <td style="text-align: center">${user.code}</td>
                  <td style="text-align: center">${user.consent}</td>
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

    document.getElementById('searchButton').addEventListener('click', () => {
        const searchInput = document.getElementById('searchInput').value.trim();
        searchData.username = searchInput;
        searchData.page = 1;
        userList(searchData);
    });

    userList(searchData);
});
