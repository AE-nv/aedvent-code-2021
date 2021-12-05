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

        for(let i = 1; i < data.length; i++) {
            const current = data[i];
            const previous = data[i - 1];

            if(current > previous) {
                amount++;
            }
        }

        return amount;
    }
}

module.exports = MeasurementCounter;