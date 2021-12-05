const MeasurementCounter = require("./measurement-counter.js");

console.log(new MeasurementCounter("./data/reference-data.json").countNumberOfTimesMeasurmentIncreases() === 7);