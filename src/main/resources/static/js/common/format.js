/**
 * 가격을 한국 원화(KRW) 형식으로 포맷팅
 * @param price - 포맷팅할 가격
 *  @returns string - 원화 형식으로 포맷팅된 가격 (예: "1,000원")
 */
export function formatPrice(price) {
  return new Intl.NumberFormat("ko-KR").format(price) + "원";
}

/**
 * 가격을 한국 원화(KRW) 형식으로 포맷팅하여 요소에 적용
 */
export function formatPriceElements() {
  const priceElements = document.querySelectorAll(".price");
  priceElements.forEach(function (priceElement) {
    const price = parseInt(priceElement.getAttribute("data-price"));
    priceElement.textContent = formatPrice(price);
  });
}