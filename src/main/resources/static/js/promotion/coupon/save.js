document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('couponTypeSelect').addEventListener('change', function() {
        const selectedType = this.value;

        if (selectedType === 'FIXED') {
            document.getElementById('fixedAmountRow').style.display = 'block';
            document.getElementById('percentageRateRow').style.display = 'none';
        }
        else if(selectedType === 'PERCENTAGE'){
            document.getElementById('fixedAmountRow').style.display = 'none';
            document.getElementById('percentageRateRow').style.display = 'block';
        }
        else{
            document.getElementById('fixedAmountRow').style.display = 'none';
            document.getElementById('percentageRateRow').style.display = 'none';
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
                alert(response.data);
                window.location.href = "/coupons";
            }
        } catch (error) {
            if (error.response && error.response.data)
                alert(error.response.data);
            else
                console.error(error);
        }

    });
})