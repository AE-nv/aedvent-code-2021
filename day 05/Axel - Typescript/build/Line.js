"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const Coordinate_1 = __importDefault(require("./Coordinate"));
class Line {
    /**
     * Constructs a new line from it's description
     *
     * @param description Description of the line. This is of the format: x_start,y_start -> x_end,y_end
     */
    constructor(description) {
        const separator = " -> ";
        const edgePoints = description.split(separator);
        const startCoordinates = edgePoints[0].split(",");
        const endCoordinates = edgePoints[1].split(",");
        this.start = new Coordinate_1.default(parseInt(startCoordinates[0]), parseInt(startCoordinates[1]));
        this.end = new Coordinate_1.default(parseInt(endCoordinates[0]), parseInt(endCoordinates[1]));
    }
    getAllCoordinates() {
        if (this.start.x == this.end.x) {
            return this.getVerticalCoordinates();
        }
        else if (this.start.y == this.end.y) {
            return this.getHorizontalCoordinates();
        }
        else {
            return this.getDiagonalCoordinates();
        }
    }
    getVerticalCoordinates() {
        const coordinates = [];
        const x = this.start.x;
        const startY = Math.min(this.start.y, this.end.y);
        const endY = Math.max(this.start.y, this.end.y);
        for (let y = startY; y <= endY; y++) {
            coordinates.push(new Coordinate_1.default(x, y));
        }
        return coordinates;
    }
    getHorizontalCoordinates() {
        const coordinates = [];
        const y = this.start.y;
        const startX = Math.min(this.start.x, this.end.x);
        const endX = Math.max(this.start.x, this.end.x);
        for (let x = startX; x <= endX; x++) {
            coordinates.push(new Coordinate_1.default(x, y));
        }
        return coordinates;
    }
    getDiagonalCoordinates() {
        const coordinates = [];
        let xStart;
        let xEnd;
        let yStart;
        let yEnd;
        if (this.start.x <= this.end.x) {
            xStart = this.start.x;
            yStart = this.start.y;
            xEnd = this.end.x;
            yEnd = this.end.y;
        }
        else {
            xStart = this.end.x;
            yStart = this.end.y;
            xEnd = this.start.x;
            yEnd = this.end.y;
        }
        const yIncrement = yEnd > yStart ? 1 : -1;
        let y = yStart;
        for (let x = xStart; x <= xEnd; x++) {
            coordinates.push(new Coordinate_1.default(x, y));
            y += yIncrement;
        }
        return coordinates;
    }
}
exports.default = Line;
//# sourceMappingURL=Line.js.map