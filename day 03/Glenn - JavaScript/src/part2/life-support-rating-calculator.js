const fs = require('fs');

class LifeSupportRatingCalculator {
    data;

    constructor(path) {
        this.fetchData(path);
    }

    fetchData(path) {
        const rawData = fs.readFileSync(path);
        this.data = JSON.parse(rawData);
    }

    calculateLifeSupportRating() {
        return this.calculateOxygenGeneratorRating() * this.calculateCO2ScrubberRating();
    }

    calculateOxygenGeneratorRating() {
        return this.calculateRating(true);
    }

    calculateCO2ScrubberRating() {
        return this.calculateRating(false);
    }

    calculateRating(most) {
        return parseInt(this.getValue(most), 2);
    }

    getValue(most) {
        let filterData = [...this.data];

        for (let i = 0; i < filterData[0].length; i++) {
            filterData = [...this.getFilteredValues(filterData, most, i)];

            if (filterData.length === 1) {
                return filterData[0];
            }
        }
    }

    getFilteredValues(filterData, most, i) {
        let filterValue;
        const sum = this.addAllValuesAtIndex(filterData, i);

        if (most) {
            filterValue = sum >= filterData.length / 2 ? "1" : "0"
        } else {
            filterValue = sum >= filterData.length / 2 ? "0" : "1"
        }

        return filterData.filter(d => {
            return d.substring(i, i + 1) === filterValue;
        });
    }

    addAllValuesAtIndex(data, i) {
        return data
            .map(d => d.substring(i, i + 1))
            .reduce((prev, curr) => +prev + +curr, 0);
    }
}

module.exports = LifeSupportRatingCalculator;