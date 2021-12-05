function part1() {
    let x = 0, depth = 0;

    var lineReader = require('readline').createInterface({
        input: require('fs').createReadStream('data.txt')
    });

    lineReader
        .on("line", (line) => {
            const [direction, amount] = line.split(" ");

            switch (direction) {
                case "forward":
                    x += +amount;
                    break;
                case "up":
                    depth += -amount;
                    break;
                case "down":
                    depth -= -amount;
                    break;
            }
        })
        .on("close", () => console.log(x * depth));
}

function part2() {
    let x = 0, depth = 0, aim = 0;

    var lineReader = require('readline').createInterface({
        input: require('fs').createReadStream('data.txt')
    });

    lineReader
        .on("line", (line) => {
            const [direction, amount] = line.split(" ");

            switch (direction) {
                case "forward":
                    x += +amount;
                    depth -= +amount * aim;
                    break;
                case "up":
                    aim -= -amount;
                    break;
                case "down":
                    aim += -amount;
                    break;
            }
        })
        .on("close", () => console.log(x * depth));
}

part2();