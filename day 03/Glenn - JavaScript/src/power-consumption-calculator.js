const fs = require('fs');

class PowerConsumptionCalculator {
    data;

    constructor(path) {
        this.fetchData(path);
    }

    fetchData(path) {
        const rawData = fs.readFileSync(path);
        this.data = JSON.parse(rawData);
    }

    calculatePowerConsumption() {
        return this.calculateGammaRate() * this.calculateEpsilonRate();
    }

    calculateGammaRate() {
        return this.calculateRate(true);
    }

    calculateEpsilonRate() {
        return this.calculateRate(false);
    }

    calculateRate(most) {
        return parseInt(this.calculateBinaryValue(most), 2);
    }

    calculateBinaryValue(most) {
        return this.addAllValues()
            .split(" ")
            .map(d => d > this.data.length / 2 ? most ? 1 : 0 : most ? 0 : 1)
            .join("");
    }

    addAllValues() {
        let result = "";

        for (let i = 0; i < this.data[i].length; i++) {
            const sum = this.addAllValuesAtIndex(this.data, i);

            if (i !== 0) {
                result += " ";
            }

            result += sum.toString();

        }

        return result;
    }

    addAllValuesAtIndex(data, i) {
        return data
            .map(d => d.substring(i, i + 1))
            .reduce((prev, curr) => +prev + +curr, 0);
    }
}

module.exports = PowerConsumptionCalculator;