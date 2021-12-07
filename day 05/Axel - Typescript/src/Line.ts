import Coordinate from "./Coordinate";

export default class Line {
  start: Coordinate;
  end: Coordinate;

  /**
   * Constructs a new line from it's description
   *
   * @param description Description of the line. This is of the format: x_start,y_start -> x_end,y_end
   */
  constructor(description: string) {
    const separator = " -> ";
    const edgePoints: string[] = description.split(separator);
    const startCoordinates = edgePoints[0].split(",");
    const endCoordinates = edgePoints[1].split(",");

    this.start = new Coordinate(
      parseInt(startCoordinates[0]),
      parseInt(startCoordinates[1])
    );
    this.end = new Coordinate(
      parseInt(endCoordinates[0]),
      parseInt(endCoordinates[1])
    );
  }

  getAllCoordinates(): Coordinate[] {
    if (this.start.x == this.end.x) {
      return this.getVerticalCoordinates();
    } else if (this.start.y == this.end.y) {
      return this.getHorizontalCoordinates();
    } else {
      return this.getDiagonalCoordinates();
    }
  }

  private getVerticalCoordinates(): Coordinate[] {
    const coordinates: Coordinate[] = [];

    const x = this.start.x;
    const startY: number = Math.min(this.start.y, this.end.y);
    const endY: number = Math.max(this.start.y, this.end.y);

    for (let y = startY; y <= endY; y++) {
      coordinates.push(new Coordinate(x, y));
    }
    return coordinates;
  }

  private getHorizontalCoordinates(): Coordinate[] {
    const coordinates: Coordinate[] = [];

    const y = this.start.y;
    const startX: number = Math.min(this.start.x, this.end.x);
    const endX: number = Math.max(this.start.x, this.end.x);

    for (let x = startX; x <= endX; x++) {
      coordinates.push(new Coordinate(x, y));
    }
    return coordinates;
  }

  private getDiagonalCoordinates(): Coordinate[] {
    const coordinates: Coordinate[] = [];

    let xStart: number;
    let xEnd: number;
    let yStart: number;
    let yEnd: number;
    if (this.start.x <= this.end.x) {
      xStart = this.start.x;
      yStart = this.start.y;

      xEnd = this.end.x;
      yEnd = this.end.y;
    } else {
      xStart = this.end.x;
      yStart = this.end.y;

      xEnd = this.start.x;
      yEnd = this.end.y;
    }

    const yIncrement = yEnd > yStart ? 1 : -1;

    let y = yStart;
    for (let x = xStart ; x <= xEnd ; x++) {
      coordinates.push(new Coordinate(x, y));
      y += yIncrement;
    }

    return coordinates;
  }
}
