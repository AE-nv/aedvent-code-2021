const LifeSupportRatingCalculator = require("./life-support-rating-calculator.js");
console.log(new LifeSupportRatingCalculator("./data/reference-data.json").calculateOxygenGeneratorRating() === 23);
console.log(new LifeSupportRatingCalculator("./data/reference-data.json").calculateCO2ScrubberRating() === 10);
console.log(new LifeSupportRatingCalculator("./data/reference-data.json").calculateLifeSupportRating() === 230);