document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('couponTypeSelect').addEventListener('change', function() {
        const selectedType = this.value;

        if (selectedType === 'FIXED') {
            document.getElementById('fixedAmountRow').style.display = 'block';
            document.getElementById('percentageRateRow').style.display = 'none';
        }
        else{
            document.getElementById('fixedAmountRow').style.display = 'none';
            document.getElementById('percentageRateRow').style.display = 'block';
        }
    });

    document.addEventListener('DOMContentLoaded', function() {
        const selectedType = document.getElementById('couponTypeSelect').value;

        if (selectedType === 'FIXED') {
            document.getElementById('fixedAmountRow').style.display = 'block';
            document.getElementById('percentageRateRow').style.display = 'none';
        }
        else{
            document.getElementById('fixedAmountRow').style.display = 'none';
            document.getElementById('percentageRateRow').style.display = 'block';
        }
    });

    const saveButton = document.getElementById("saveCouponButton");
    saveButton.addEventListener("click", async (event) => {

        event.preventDefault();

        const couponData = {
            name: document.querySelector('input[name="name"]').value,
            type: document.querySelector('select[name="type"]').value,
            amount: document.querySelector('input[name="amount"]').value || null,
            rate: document.querySelector('input[name="rate"]').value || null,
            active: document.querySelector('select[name="active"]').value,
            endDate: document.querySelector('input[name="endDate"]').value,
            eventId: document.querySelector('select[name="eventId"]').value || null,
        };

        try {
            const response = await axios.post('/api/v1/coupons/save', couponData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 200 || response.status === 201) {
                alert("쿠폰이 성공적으로 저장되었습니다!");
                window.location.href = "/coupons";
            } else {
                alert("저장 중 문제가 발생했습니다.");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("저장 중 문제가 발생했습니다.");
        }
    });
})