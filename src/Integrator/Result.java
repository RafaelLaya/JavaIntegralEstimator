public class Result {
    public String method;
    public double value;
    public double leftBound;
    public double rightBound;
    public int rectangles;

    public Result(String method, double value, double leftBound, double rightBound, int rectangles) {
        this.method = method;
        this.value = value;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.rectangles = rectangles;
    }

    public String toString() {
        return "\nMethod: " + this.method +
                "\nValue: " + this.value +
                "\nFirst Bound: " + this.leftBound +
                "\nSecond Bound: " + this.rightBound +
                "\nrectangles: " + this.rectangles;
    }

    public Result add(Result other) {
        return new Result(this.method, this.value + other.value, this.leftBound, this.rightBound, this.rectangles);
    }

    public Result mult(double scalar) {
        return new Result(this.method, this.value * scalar, this.leftBound, this.rightBound, this.rectangles);
    }

    public Result div(double scalar) {
        return new Result(this.method, this.value / scalar, this.leftBound, this.rightBound, this.rectangles);
    }

    public void setMethod(String method) {
        this.method = method;
    }
}