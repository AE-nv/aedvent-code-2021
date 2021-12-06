import Coordinate from "../src/Coordinate";
import Line from "../src/Line";

describe("constructor", () => {
  test("Extracts start and end coordinate", () => {
    const description = "1,1 -> 54,67";

    const result = new Line(description);

    expect(result.start).toEqual(new Coordinate(1, 1));
    expect(result.end).toEqual(new Coordinate(54, 67));
  });
});

describe("getAllCoordinates", () => {
  test("When X equal, return all in Y range", () => {
    const description = "1,1 -> 1,3";
    const line = new Line(description);

    const expected: Coordinate[] = [
      new Coordinate(1, 1),
      new Coordinate(1, 2),
      new Coordinate(1, 3),
    ];

    expect(line.getAllCoordinates()).toEqual(expected);
  });

  test("When Y equal, return all in X range", () => {
    const description = "9,4 -> 3,4";
    const line = new Line(description);

    const expected: Coordinate[] = [
      new Coordinate(3, 4),
      new Coordinate(4, 4),
      new Coordinate(5, 4),
      new Coordinate(6, 4),
      new Coordinate(7, 4),
      new Coordinate(8, 4),
      new Coordinate(9, 4),
    ];

    expect(line.getAllCoordinates()).toEqual(expected);
  });

  test("When diagonal, return all in range", () => {
    const description = "1,1 -> 3,3";
    const line = new Line(description);

    const expected: Coordinate[] = [
      new Coordinate(1, 1),
      new Coordinate(2, 2),
      new Coordinate(3, 3),
    ];

    expect(line.getAllCoordinates()).toEqual(expected);
  });

  test("When diagonal descending, return all in range", () => {
    const description = "9,7 -> 7,9";
    const line = new Line(description);

    const expected: Coordinate[] = [
      new Coordinate(7, 9),
      new Coordinate(8, 8),
      new Coordinate(9, 7),
    ];

    expect(line.getAllCoordinates()).toEqual(expected);
  });
});
