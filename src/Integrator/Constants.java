import net.objecthunter.exp4j.Expression;

public class Constants {
    // sentinels
    public static final int maxStepsNotSet = -100;
    public static final int rectanglesNotSet = -101;
    public static final double leftBoundNotSet = Double.MIN_VALUE;
    public static final double rightBoundNotSet = Double.MIN_VALUE;
    public static final Expression functionNotSet = null;
    public static final double toleranceNotSet = -200.0;

    // menu
    public static final int simpson = 1;
    public static final int trapezium = 2;
    public static final int left = 3;
    public static final int right = 4;
    public static final int midpoint = 5;
    public static final int average = 6;
    public static final int tolerance = 7;
    public static final int romberg = 8;
    public static final int surprise = 9;
    public static final int instructions = 10;
    public static final int educate = 11;
    public static final int newFunction = 12;
    public static final int newBounds = 13;
    public static final int newRectangles = 14;
    public static final int newFuncBounRec = 15;
    public static final int newMaxSteps = 16;
    public static final int newTolerance = 17;
    public static final int quit = 18;

    // just a default value used in client
    public static final int defaultMaxSteps = 20;
}
