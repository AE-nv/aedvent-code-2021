export default class Fish {
  private internalTimer: number;

  constructor(timer?: number) {
    if (timer) {
      this.internalTimer = timer;
    } else {
      this.internalTimer = 8;
    }
  }

  /**
   * Decrements the internal timer of the fish.
   *
   * @returns true if the fish will spawn an offspring, false otherwise
   */
  public decrementTimer(): boolean {
    if (this.internalTimer == 0) {
      this.internalTimer = 6;
      return true;
    }
    this.internalTimer--;
    return false;
  }
}
