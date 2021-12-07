import getCoordinateHeathmap from "../src/Day05";
import Line from "../src/Line";

describe("getCoordinateHeathmap", () => {
  test("When none overlapping, return map with all coordinates", () => {
    const lines: Line[] = [
      new Line("0,9 -> 2,9"),
      new Line("1,1 -> 3,3")
    ]

    const result: Map<string, number> = getCoordinateHeathmap(lines);
    expect(result.size).toBe(6);
    expect(result.get("x:0,y:9")).toBe(1);
  });

  test("When a point overlaps, value increases", () => {
    const lines: Line[] = [
      new Line("0,9 -> 2,9"),
      new Line("0,9 -> 0,7")
    ]

    const result: Map<string, number> = getCoordinateHeathmap(lines);
    expect(result.size).toBe(5);
    expect(result.get("x:0,y:9")).toBe(2);
  });

  test("When a point overlaps twice, value increases", () => {
    const lines: Line[] = [
      new Line("0,9 -> 2,9"),
      new Line("0,9 -> 0,7"),
      new Line("0,9 -> 1,8")
    ]

    const result: Map<string, number> = getCoordinateHeathmap(lines);
    expect(result.size).toBe(6);
    expect(result.get("x:0,y:9")).toBe(3);
  });
})