const fs = require('fs');

class MeasurementCounter {
    path;

    constructor(path) {
        this.path = path;
    }

    fetchData() {
        const rawData = fs.readFileSync(this.path);
        return JSON.parse(rawData);
    }

    countNumberOfTimesMeasurmentIncreases() {
        const data = this.fetchData();
        let amount = 0;

        let previousSum;
        let currentSum;

        for (let i = 2; i < data.length; i++) {
            const first = data[i - 2];
            const second = data[i - 1];
            const third = data[i];

            if (currentSum) {
                previousSum = currentSum;
            }

            currentSum = first + second + third;

            if (previousSum && currentSum && currentSum > previousSum) {
                amount++;
            }
        }

        return amount;
    }
}

module.exports = MeasurementCounter;