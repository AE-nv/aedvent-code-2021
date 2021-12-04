export class Board {
  size: number;
  values: number[][];
  marked: number[][] = [];
  bingoAfterTurns: number;
  score: number;

  constructor(values: number[][], size: number) {
    this.values = values;
    this.size = size;
  }

  isBingo(): boolean {
    for (let i = 0; i < this.size; i++) {
      if (this.marked.filter((m) => m[0] == i).length == this.size) {
        return true;
      }
      if (this.marked.filter((m) => m[1] == i).length == this.size) {
        return true;
      }
    }
    return false;
  }

  markValue(value: number) {
    for (let i = 0; i < this.size; i++) {
      for (let j = 0; j < this.size; j++) {
        if (this.values[i][j] == value) {
          this.marked.push([i, j]);
        }
      }
    }
  }

  sumRemaining(): number {
    let sum = 0;
    for (let i = 0; i < this.size; i++) {
      for (let j = 0; j < this.size; j++) {
        if (!this.isMarked(i, j)) {
          sum += this.values[i][j];
        }
      }
    }
    return sum;
  }

  isMarked(i: number, j: number): boolean {
    return this.marked.some((mark) => mark[0] == i && mark[1] == j);
  }
}
