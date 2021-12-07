"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Fish {
    constructor(timer) {
        if (timer) {
            this.internalTimer = timer;
        }
        else {
            this.internalTimer = 8;
        }
    }
    /**
     * Decrements the internal timer of the fish.
     *
     * @returns true if the fish will spawn an offspring, false otherwise
     */
    decrementTimer() {
        if (this.internalTimer == 0) {
            this.internalTimer = 6;
            return true;
        }
        this.internalTimer--;
        return false;
    }
}
exports.default = Fish;
//# sourceMappingURL=Fish.js.map