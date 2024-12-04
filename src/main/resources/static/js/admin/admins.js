import { updatePagination, changePage } from "../common/pagination.js";
document.addEventListener('DOMContentLoaded', function () {

    let searchData = {
        page: 1,
        size: 10,
        role: '',
        name: '',
    };

    async function adminList(searchData) {
        const params = new URLSearchParams({
            page: searchData.page,
            size: searchData.size,
            role: searchData.role,
            name: searchData.name,
        });

        try {
            const response = await axios.get(`/api/v1/admins?${params.toString()}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const { total, last, start, end } = response.data.data;
            const admins = response.data.data.admins;

            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';

            admins.forEach(admin => {
                const row = document.createElement('tr');

                row.innerHTML = `
                  <td class="text-align: center">${admin.id}</td>
                  <td class="text-align: center"><a href="/admins/${admin.id}">${admin.name}</a></td>
                  <td class="text-align: center">${admin.username}</td>                
                  <td class="text-align: center">${admin.role}</td>                            
                  `;

                tbody.appendChild(row);
            });

            const changePageCallback = changePage(searchData.page, function(page) {
                searchData.page = page;
                adminList(searchData);
            });

            updatePagination(searchData.page, last, total, searchData.size, start, end, changePageCallback);

        } catch (error) {
            console.error('관리자 조회 실패:', error);
            alert('관리자 조회에 실패했습니다.');
        }
    }
    const roleFilter = document.getElementById('roleFilter');
    roleFilter.addEventListener('change', function () {
        searchData.role = this.value;
        searchData.page = 1;
        adminList(searchData);
    });

    document.getElementById('searchButton').addEventListener('click', () => {
        const searchInput = document.getElementById('searchInput').value.trim();
        searchData.name = searchInput;
        searchData.page = 1;
        adminList(searchData);
    });

    adminList(searchData);
});
