import { updatePagination, changePage } from '/js/common/pagination.js';

// 현재 페이지 정보
let currentPage = 1;
const lastPage = 10; // 전체 페이지 수
const totalEntries = 100; // 총 항목 수
const entriesPerPage = 10;

// 페이지 변경 함수
const setPageCallback = (page) => {
    currentPage = page;
    console.log(`Current page is now: ${currentPage}`);
    renderPagination();
};

// 페이지네이션 렌더링
function renderPagination() {
    const start = (currentPage - 1) * entriesPerPage + 1;
    const end = Math.min(currentPage * entriesPerPage, totalEntries);
    updatePagination(
        currentPage,
        lastPage,
        totalEntries,
        entriesPerPage,
        start,
        end,
        changePage(currentPage, setPageCallback)
    );
}

// 초기 렌더링
renderPagination();
