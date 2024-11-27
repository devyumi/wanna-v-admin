document.addEventListener('DOMContentLoaded', function () {

  /**
   * 식당 리뷰 감정 분석 통계
   */
      // "POSITIVE" sentiment에 해당하는 count 값 추출
  const positiveCnt = sentiment.filter(item => item.sentiment === "POSITIVE")
      .map(item => item.count)[0];

  // "NEGATIVE" sentiment에 해당하는 count 값 추출
  const negativeCnt = sentiment.filter(item => item.sentiment === "NEGATIVE")
  .map(item => item.count)[0];

  // 차트 생성
  const sentimentChart = document.getElementById('sentimentChart').getContext(
      '2d');
  new Chart(sentimentChart, {
    type: 'doughnut',
    data: {
      labels: ['POSITIVE', 'NEGATIVE'],
      datasets: [{
        data: [positiveCnt, negativeCnt],
        backgroundColor: ['#36A2EB', '#FF5733'],
        borderColor: ['#36A2EB', '#FF5733'],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        datalabels: {
          anchor: 'center',
          align: 'center',
          color: '#fff',
          font: {
            size: 20,
          },
          formatter: (value) => `${value.toLocaleString()}`
        }
      }
    },
    plugins: [ChartDataLabels]
  });
});