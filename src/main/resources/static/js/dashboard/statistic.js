document.addEventListener('DOMContentLoaded', function () {

  /**
   * 월별 통계
   */
  const monthlyStats = function () {
    /**
     * 매출 통계 (category => SALES)
     */
    const salesMonthlyData = monthlyStatistics.filter(
        item => item.category === "SALES");

    // x축 (월)과 y축 (value) 데이터 준비
    const period = salesMonthlyData.map(item => `${item.year}-${item.month}`);
    const salesValues = salesMonthlyData.map(item => item.value);

    // 차트 생성
    const salesMonthlyChart = document.getElementById(
        'salesMonthlyChart').getContext(
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
        plugins: {
          datalabels: { // 데이터 라벨 옵션
            anchor: 'end', // 데이터 라벨의 위치
            align: 'top',  // 막대 위쪽에 정렬
            color: '#50565C', // 라벨 색상
            font: {
              size: 8,    // 라벨 글꼴 크기
              // weight: 'bold'
            },
            formatter: (value) => `${value.toLocaleString()}` // 값 포맷팅
          }
        },
        scales: {
          y: {
            beginAtZero: true,  // y축이 0에서 시작하도록 설정
            suggestedMax: Math.max(...salesValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}원`; // y축 값 포맷팅
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels] // 플러그인 활성화
    });

    /**
     * 활성 회원 통계 (category => ACTIVE_MEMB)
     */
    const activeUserMonthlyData = monthlyStatistics.filter(
        item => item.category === "ACTIVE_MEMB");
    const activeUserMonthlyValues = activeUserMonthlyData.map(
        item => item.value);

    // 차트 생성
    const activeUserChart = document.getElementById(
        'activeUserMonthlyChart').getContext(
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
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...activeUserMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 신규 회원 통계 (category => NEW_MEMB)
     */
    const newUserMonthlyData = monthlyStatistics.filter(
        item => item.category === "NEW_MEMB");
    const newUserMonthlyValues = newUserMonthlyData.map(item => item.value);

    // 차트 생성
    const newUserChart = document.getElementById(
        'newUserMonthlyChart').getContext(
        '2d');
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
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...newUserMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 탈퇴 회원 통계 (category => DELETED_MEMB)
     */
    const deletedUserMonthlyData = monthlyStatistics.filter(
        item => item.category === "DELETED_MEMB");
    const deletedUserMonthlyValues = deletedUserMonthlyData.map(
        item => item.value);

    // 차트 생성
    const deletedChart = document.getElementById(
        'deletedUserMonthlyChart').getContext(
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
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...deletedUserMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 식당 통계 (category => RESTAURANT)
     */
    const restaurantMonthlyData = monthlyStatistics.filter(
        item => item.category === "RESTAURANT");
    const restaurantMonthlyValues = restaurantMonthlyData.map(
        item => item.value);

    // 차트 생성
    const restaurantChart = document.getElementById(
        'restaurantMonthlyChart').getContext(
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
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...restaurantMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}개`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
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
    const reviewChart = document.getElementById(
        'reviewMonthlyChart').getContext('2d');
    new Chart(reviewChart, {
      type: 'line',  // 선 그래프
      data: {
        labels: period,  // x축 레이블: 각 년도 + 월
        datasets: [{
          label: '신규 리뷰',
          data: reviewMonthlyValues, // y축 값: 신규 리뷰 데이터
          fill: false,  // 선 아래를 채우지 않음
          borderColor: 'rgba(153, 102, 255, 1)',  // 선 색상
          tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
          borderWidth: 2,  // 선의 두께
          datalabels: {  // 리뷰에 대한 데이터 레이블 옵션
            anchor: 'end',  // 막대의 끝에 레이블 표시
            align: 'top',  // 값이 막대 위쪽에 표시
            color: '#50565C',
            font: {
              size: 10
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        }, {
          label: '고유 신규 리뷰',
          data: uniqueReviewMonthlyValues, // y축 값: 고유 리뷰 데이터
          fill: false,  // 선 아래를 채우지 않음
          borderColor: 'rgba(255, 159, 64, 1)',  // 선 색상
          tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
          borderWidth: 2,  // 선의 두께
          datalabels: {  // 고유 리뷰에 대한 데이터 레이블 옵션
            anchor: 'start',  // 막대의 시작 지점에 레이블 표시
            align: 'bottom',  // 값이 막대 아래쪽에 표시
            color: '#50565C',
            font: {
              size: 10
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...reviewMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}개`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
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
        'reservationMonthlyChart').getContext('2d');
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
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...reservationMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}건`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
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
        'cancelledResMonthlyChart').getContext('2d');
    new Chart(cancelledResChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '식당 예약 취소',
          data: cancelledResMonthlyValues,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...cancelledResMonthlyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}건`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });
  };

  /**
   * 주별 통계
   */
  const weeklyStats = function () {
    /**
     * 매출 통계 (category => SALES)
     */
    const salesWeeklyData = weeklyStatistics.filter(
        item => item.category === "SALES");

    // x축 (주)과 y축 (value) 데이터 준비
    const period = salesWeeklyData.map(item => `${item.year}-W${item.week}`);
    const salesValues = salesWeeklyData.map(item => item.value);

    // 차트 생성
    const salesWeeklyChart = document.getElementById(
        'salesWeeklyChart').getContext(
        '2d');
    new Chart(salesWeeklyChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '매출',
          data: salesValues,
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: { // 데이터 라벨 옵션
            anchor: 'end', // 데이터 라벨의 위치
            align: 'top',  // 막대 위쪽에 정렬
            color: '#50565C', // 라벨 색상
            font: {
              size: 8,    // 라벨 글꼴 크기
              // weight: 'bold'
            },
            formatter: (value) => `${value.toLocaleString()}` // 값 포맷팅
          }
        },
        scales: {
          y: {
            beginAtZero: true,  // y축이 0에서 시작하도록 설정
            suggestedMax: Math.max(...salesValues) * 1.1, // 가장 큰 값에 10% 여유 추가
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels] // 플러그인 활성화
    });

    /**
     * 활성 회원 통계 (category => ACTIVE_MEMB)
     */
    const activeUserWeeklyData = weeklyStatistics.filter(
        item => item.category === "ACTIVE_MEMB");
    const activeUserWeeklyValues = activeUserWeeklyData.map(
        item => item.value);

    // 차트 생성
    const activeUserChart = document.getElementById(
        'activeUserWeeklyChart').getContext(
        '2d');
    new Chart(activeUserChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '활성 유저',
          data: activeUserWeeklyValues,
          backgroundColor: 'rgba(153, 102, 255, 0.2)',
          borderColor: 'rgba(153, 102, 255, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...activeUserWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 신규 회원 통계 (category => NEW_MEMB)
     */
    const newUserWeeklyData = weeklyStatistics.filter(
        item => item.category === "NEW_MEMB");
    const newUserWeeklyValues = newUserWeeklyData.map(item => item.value);

    // 차트 생성
    const newUserChart = document.getElementById(
        'newUserWeeklyChart').getContext(
        '2d');
    new Chart(newUserChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '신규 유저',
          data: newUserWeeklyValues,
          backgroundColor: 'rgba(255, 159, 645, 0.2)',
          borderColor: 'rgba(255, 159, 64, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...newUserWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 탈퇴 회원 통계 (category => DELETED_MEMB)
     */
    const deletedUserWeeklyData = weeklyStatistics.filter(
        item => item.category === "DELETED_MEMB");
    const deletedUserWeeklyValues = deletedUserWeeklyData.map(
        item => item.value);

    // 차트 생성
    const deletedChart = document.getElementById(
        'deletedUserWeeklyChart').getContext(
        '2d');
    new Chart(deletedChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '탈퇴 유저',
          data: deletedUserWeeklyValues,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...deletedUserWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}명`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 식당 통계 (category => RESTAURANT)
     */
    const restaurantWeeklyData = weeklyStatistics.filter(
        item => item.category === "RESTAURANT");
    const restaurantWeeklyValues = restaurantWeeklyData.map(
        item => item.value);

    // 차트 생성
    const restaurantChart = document.getElementById(
        'restaurantWeeklyChart').getContext(
        '2d');
    new Chart(restaurantChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '신규 식당',
          data: restaurantWeeklyValues,
          backgroundColor: 'rgba(255, 205, 86, 0.2)',
          borderColor: 'rgba(255, 205, 86, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...restaurantWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}개`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 리뷰 통계 (category => REVIEW, UNIQUE_REVIEW)
     */
    const reviewWeeklyData = weeklyStatistics.filter(
        item => item.category === "REVIEW");
    const uniqueReviewWeeklyData = weeklyStatistics.filter(
        item => item.category === "UNIQUE_REVIEW");

    const reviewWeeklyValues = reviewWeeklyData.map(item => item.value);
    const uniqueReviewWeeklyValues = uniqueReviewWeeklyData.map(
        item => item.value);

    // 차트 생성
    const reviewChart = document.getElementById('reviewWeeklyChart').getContext(
        '2d');
    new Chart(reviewChart, {
      type: 'line',  // 선 그래프
      data: {
        labels: period,  // x축 레이블: 각 년도 + 월
        datasets: [{
          label: '신규 리뷰',
          data: reviewWeeklyValues, // y축 값: 신규 리뷰 데이터
          fill: false,  // 선 아래를 채우지 않음
          borderColor: 'rgba(153, 102, 255, 1)',  // 선 색상
          tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
          borderWidth: 2,  // 선의 두께
          datalabels: {  // 리뷰에 대한 데이터 레이블 옵션
            anchor: 'end',  // 막대의 끝에 레이블 표시
            align: 'top',  // 값이 막대 위쪽에 표시
            color: '#50565C',
            font: {
              size: 10
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        }, {
          label: '고유 신규 리뷰',
          data: uniqueReviewWeeklyValues, // y축 값: 고유 리뷰 데이터
          fill: false,  // 선 아래를 채우지 않음
          borderColor: 'rgba(255, 159, 64, 1)',  // 선 색상
          tension: 0.1,  // 선의 곡률(0이면 직선, 1이면 부드러운 곡선)
          borderWidth: 2,  // 선의 두께
          datalabels: {  // 고유 리뷰에 대한 데이터 레이블 옵션
            anchor: 'start',  // 막대의 시작 지점에 레이블 표시
            align: 'bottom',  // 값이 막대 아래쪽에 표시
            color: '#50565C',
            font: {
              size: 10
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...reviewWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}개`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 예약 통계 (category => RESERVATION)
     */
    const reservationWeeklyData = weeklyStatistics.filter(
        item => item.category === "RESERVATION");
    const reservationWeeklyValues = reservationWeeklyData.map(
        item => item.value);

    // 차트 생성
    const reservationChart = document.getElementById(
        'reservationWeeklyChart').getContext('2d');
    new Chart(reservationChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '식당 예약',
          data: reservationWeeklyValues,
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...reservationWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}건`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });

    /**
     * 예약 취소 통계 (category => CANCELLED_RESERVATION)
     */
    const cancelledResWeeklyData = weeklyStatistics.filter(
        item => item.category === "CANCELLED_RESERVATION");
    const cancelledResWeeklyValues = cancelledResWeeklyData.map(
        item => item.value);

    // 차트 생성
    const cancelledResChart = document.getElementById(
        'cancelledResWeeklyChart').getContext('2d');
    new Chart(cancelledResChart, {
      type: 'bar',
      data: {
        labels: period,
        datasets: [{
          label: '식당 예약 취소',
          data: cancelledResWeeklyValues,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          datalabels: {
            anchor: 'end',
            align: 'top',
            color: '#50565C',
            font: {
              size: 10,
            },
            formatter: (value) => `${value.toLocaleString()}`
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            suggestedMax: Math.max(...cancelledResWeeklyValues) * 1.1,
            ticks: {
              callback: function(value) {
                return `${value.toLocaleString()}건`;
              }
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    });
  };

  monthlyStats();

  /**
   * [월별 통계] 탭 클릭 시
   */
  document.getElementById('monthly-tab').addEventListener('click',
      function (event) {
        monthlyStats();
      });

  /**
   * [주별 통계] 탭 클릭 시
   */
  document.getElementById('weekly-tab').addEventListener('click',
      function (event) {
        weeklyStats();
      });
});
