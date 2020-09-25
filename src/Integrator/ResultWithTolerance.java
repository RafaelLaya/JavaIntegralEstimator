public class ResultWithTolerance extends Result {
    private boolean meetsTolerance;
    private Result left;
    private Result right;
    private Result average;

    public ResultWithTolerance(String method, double value, double leftBound,
                               double rightBound, int rectangles, boolean meetsTolerance,
                               Result left, Result right, Result average){
        super(method, value, leftBound, rightBound, rectangles);
        this.meetsTolerance = meetsTolerance;
        this.left = left;
        this.right = right;
        this.average = average;
    }

    public Result getLeft() {
        return this.left;
    }

    public Result getRight() {
        return this.right;
    }

    public Result getAverage() {
        return this.average;
    }

    public boolean meetsTolerance() {
        return this.meetsTolerance;
    }

    public String toString() {
        return super.toString()   + "\n" +
                "Left: " + this.left.value + "\n" +
                "Right: " + this.right.value + "\n" +
                "Tolerance Met: " + this.meetsTolerance + "\n";
    }
}