document.addEventListener('DOMContentLoaded', function () {

  /**
   * 매출 통계 (category => SALES)
   */
  const salesMonthlyData = monthlyStatistics.filter(
      item => item.category === "SALES");

  // x축 (월)과 y축 (value) 데이터 준비
  const period = salesMonthlyData.map(item => `${item.year}-${item.month}`);
  const salesValues = salesMonthlyData.map(item => item.value);

  // 차트 생성
  const salesMonthlyChart = document.getElementById('salesChart').getContext(
      '2d');
  new Chart(salesMonthlyChart, {
    type: 'bar',  // 차트 타입: 막대 그래프
    data: {
      labels: period,  // x축 레이블: 각 년도 + 월
      datasets: [{
        label: '매출',
        data: salesValues,  // y축 값: 매출 데이터
        backgroundColor: 'rgba(75, 192, 192, 0.2)',  // 막대 색상
        borderColor: 'rgba(75, 192, 192, 1)',  // 막대 테두리 색상
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true  // y축이 0에서 시작하도록 설정
        }
      }
    }
  });

  /**
   * 활성 회원 통계 (category => ACTIVE_MEMB)
   */
  const activeUserMonthlyData = monthlyStatistics.filter(
      item => item.category === "ACTIVE_MEMB");
  const activeUserMonthlyValues = activeUserMonthlyData.map(item => item.value);

  // 차트 생성
  const activeUserChart = document.getElementById('activeUserChart').getContext(
      '2d');
  new Chart(activeUserChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '활성 유저',
        data: activeUserMonthlyValues,
        backgroundColor: 'rgba(153, 102, 255, 0.2)',
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  /**
   * 신규 회원 통계 (category => NEW_MEMB)
   */
  const newUserMonthlyData = monthlyStatistics.filter(
      item => item.category === "NEW_MEMB");
  const newUserMonthlyValues = newUserMonthlyData.map(item => item.value);

  // 차트 생성
  const newUserChart = document.getElementById('newUserChart').getContext('2d');
  new Chart(newUserChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '신규 유저',
        data: newUserMonthlyValues,
        backgroundColor: 'rgba(255, 159, 645, 0.2)',
        borderColor: 'rgba(255, 159, 64, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  /**
   * 탈퇴 회원 통계 (category => DELETED_MEMB)
   */
  const deletedUserMonthlyData = monthlyStatistics.filter(
      item => item.category === "DELETED_MEMB");
  const deletedUserMonthlyValues = deletedUserMonthlyData.map(
      item => item.value);

  // 차트 생성
  const deletedChart = document.getElementById('deletedUserChart').getContext(
      '2d');
  new Chart(deletedChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '탈퇴 유저',
        data: deletedUserMonthlyValues,
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  /**
   * 식당 통계 (category => RESTAURANT)
   */
  const restaurantMonthlyData = monthlyStatistics.filter(
      item => item.category === "RESTAURANT");
  const restaurantMonthlyValues = restaurantMonthlyData.map(item => item.value);

  // 차트 생성
  const restaurantChart = document.getElementById('restaurantChart').getContext(
      '2d');
  new Chart(restaurantChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '신규 식당',
        data: restaurantMonthlyValues,
        backgroundColor: 'rgba(255, 205, 86, 0.2)',
        borderColor: 'rgba(255, 205, 86, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  /**
   * 리뷰 통계 (category => REVIEW, UNIQUE_REVIEW)
   */
  const reviewMonthlyData = monthlyStatistics.filter(
      item => item.category === "REVIEW");
  const uniqueReviewMonthlyData = monthlyStatistics.filter(
      item => item.category === "UNIQUE_REVIEW");

  const reviewMonthlyValues = reviewMonthlyData.map(item => item.value);
  const uniqueReviewMonthlyValues = uniqueReviewMonthlyData.map(
      item => item.value);

  // 차트 생성
  const reviewChart = document.getElementById('reviewChart').getContext('2d');
  new Chart(reviewChart, {
    type: 'line',  // 선 그래프
    data: {
      labels: period,  // x축 레이블: 각 년도 + 월
      datasets: [{
        label: '신규 리뷰',
        data: reviewMonthlyValues, // y축 값: 매출 데이터
        fill: false,  // 선 아래를 채우지 않음
        borderColor: 'rgba(153, 102, 255, 1)',  // 선 색상
        tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
        borderWidth: 2  // 선의 두께
      }, {
        label: '고유 신규 리뷰',
        data: uniqueReviewMonthlyValues, // y축 값: 매출 데이터
        fill: false,  // 선 아래를 채우지 않음
        borderColor: 'rgba(255, 159, 64, 1)',  // 선 색상
        tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
        borderWidth: 2  // 선의 두께
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true  // y축이 0에서 시작하도록 설정
        }
      }
    }
  });

  /**
   * 예약 통계 (category => RESERVATION)
   */
  const reservationMonthlyData = monthlyStatistics.filter(
      item => item.category === "RESERVATION");
  const reservationMonthlyValues = reservationMonthlyData.map(
      item => item.value);

  // 차트 생성
  const reservationChart = document.getElementById(
      'reservationChart').getContext('2d');
  new Chart(reservationChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '식당 예약',
        data: reservationMonthlyValues,
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

  /**
   * 예약 취소 통계 (category => CANCELLED_RESERVATION)
   */
  const cancelledResMonthlyData = monthlyStatistics.filter(
      item => item.category === "CANCELLED_RESERVATION");
  const cancelledResMonthlyValues = cancelledResMonthlyData.map(
      item => item.value);

  // 차트 생성
  const cancelledResChart = document.getElementById(
      'cancelledResChart').getContext('2d');
  new Chart(cancelledResChart, {
    type: 'bar',
    data: {
      labels: period,
      datasets: [{
        label: '식당 예약 취소',
        data: cancelledResMonthlyValues,
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
});
