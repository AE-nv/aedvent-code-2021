export class Command {
  movement: Movement;
  amount: number;

  public constructor(commandStr: string) {
    const split = commandStr.split(" ");
    this.movement = split[0] as Movement;
    this.amount = parseInt(split[1]);
  }
}

export enum Movement {
  FORWARD = "forward",
  UP = "up",
  DOWN = "down",
}
