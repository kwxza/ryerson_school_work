import java.util.Random;

public class Particle {

    double xCoord, yCoord, radAngle;

    private static final Random rng = new Random();
    private static final double BUZZY = 0.7;

    public Particle(int width, int height) {
        xCoord = width * rng.nextDouble();
        yCoord = height * rng.nextDouble();
        radAngle = Math.PI * 2 * rng.nextDouble();
    }

    public double getX() {
        return xCoord;
    }

    public double getY() {
        return yCoord;
    }

    public void move() {
        xCoord += Math.cos(radAngle);
        yCoord += Math.sin(radAngle);
        radAngle += rng.nextGaussian() * BUZZY;
    }
}
