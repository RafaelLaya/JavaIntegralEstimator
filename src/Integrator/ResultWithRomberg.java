public class ResultWithRomberg extends Result {
    private int steps;
    private double tolerance;

    public ResultWithRomberg(String method, double value, double leftBound, double rightBound, int steps, double tolerance) {
        super(method, value, leftBound, rightBound, -100);
        this.method = method;
        this.value = value;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.steps = steps;
        this.tolerance = tolerance;
    }

    public String toString() {
        return "\nMethod: " + this.method +
                "\nValue: " + this.value +
                "\nFirst Bound: " + this.leftBound +
                "\nSecond Bound: " + this.rightBound +
                "\nSteps: " + this.steps +
                "\nTolerance: " + this.tolerance;
    }

    public int getSteps() {
        return this.steps;
    }
}
