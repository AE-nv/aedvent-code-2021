package aeadvent.code2021.day11.istvan;

public class DumboOctopus {
    private final Point position;
    private int energyLevel;
    private boolean hasFlashed = false;

    public DumboOctopus(Point position, int energyLevel ) {
        this.position = position;
        this.energyLevel = energyLevel;
    }

    public Point getPosition() {
        return position;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }


    public boolean accumulateEnergy() {
        this.hasFlashed = false;
        this.energyLevel++;
        if (this.energyLevel > 9) {
            hasFlashed =  true;
            this.energyLevel = 0;
            return true;
        }
        return false;
    }

    public boolean absorbFlash() {
        if (hasFlashed)
            return false;

        return accumulateEnergy();
    }
}
