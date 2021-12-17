package aeadvent.code2021.day17.istvan;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class ProbeTest {

    @Test
    void move_right() {
        Probe p = new Probe(new Point(0,0), new Velocity(7,2));
        p.move();
        assertThat(p.getPosition()).isEqualTo(new Point(7,2));
        assertThat(p.getHorizontalSpeed()).isEqualTo(6);
        assertThat(p.getVerticalSpeed()).isEqualTo(1);
    }

    @Test
    void move_Left() {
        Probe p = new Probe(new Point(0,0), new Velocity(-7,2));
        p.move();
        assertThat(p.getPosition()).isEqualTo(new Point(-7,2));
        assertThat(p.getHorizontalSpeed()).isEqualTo(-6);
        assertThat(p.getVerticalSpeed()).isEqualTo(1);
    }

    @Test
    void move_vertically() {
        Probe p = new Probe(new Point(0,0), new Velocity(0,-1));
        p.move();
        assertThat(p.getPosition()).isEqualTo(new Point(0,-1));
        assertThat(p.getHorizontalSpeed()).isEqualTo(0);
        assertThat(p.getVerticalSpeed()).isEqualTo(-2);
    }

    @Test
    void isAreMissed_tooLow() {
        Probe p = new Probe(new Point(0,0), new Velocity(0,-1));
        Area a = new Area(-5,5,5,10);
        assertThat(p.isTargetAreaMissed(a)).isTrue();
    }

    @Test
    void isAreMissed_toTheLeft() {
        Probe p = new Probe(new Point(-6,0), new Velocity(0,7));
        Area a = new Area(-5,5,5,10);
        assertThat(p.isTargetAreaMissed(a)).isTrue();
    }

    @Test
    void isAreMissed_toTheRight() {
        Probe p = new Probe(new Point(6,0), new Velocity(0,7));
        Area a = new Area(-5,5,5,10);
        assertThat(p.isTargetAreaMissed(a)).isTrue();
    }

    @Test
    void targetReached() {
        Probe p = new Probe(new Point(-5,5), new Velocity(99,77));
        Area a = new Area(-5,5,5,10);
        assertThat(p.isTargetAreaReached(a)).isTrue();
    }

    @Test
    void isValidSpeedToReachArea() {
        Probe p = new Probe(new Point(0,0), new Velocity(7,2));
        Area a = new Area(20,30,10,-5);
        Optional<Point> high = p.getHighestPointWhenReachingTarget(a);
        assertThat(high).isPresent();
    }
}
