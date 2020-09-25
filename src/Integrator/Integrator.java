/*
 * - Name: Rafael Laya
 * - Integrator
 * - A class that provides useful functions that will allow to easily estimates definite integrals over an interval
 * (a, b) using common methods taught in a regular calculus II classroom
 */

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.special.Gamma;

import java.util.Random;

public class Integrator {

    private Expression expression;
    private double leftBound;
    private double rightBound;
    private int rectangles;
    private double tolerance;
    private int maxSteps;

    public Integrator(Expression expression, double leftBound, double rightBound) {
        this.setExpression(expression);
        this.setLeftBound(leftBound);
        this.setRightBound(rightBound);
        this.tolerance = Double.NaN;
        this.maxSteps = Constants.maxStepsNotSet;
        this.rectangles = Constants.rectanglesNotSet;
    }

    public Integrator(Expression expression, double leftBound, double rightBound, int rectangles) {
        this(expression, leftBound, rightBound);
        this.setRectangles(rectangles);
    }

    public Integrator(Expression expression, double leftBound, double rightBound, int rectangles,
                      double tolerance, int maxSteps) {
        this(expression, leftBound, rightBound, rectangles);
        this.setMaxSteps(maxSteps);
        this.setTolerance(tolerance);
    }


    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setRectangles(int rectangles) {
        if (rectangles < 0) {
            throw new IllegalArgumentException("Rectangles has to be a positive integer.");
        }
        this.rectangles = rectangles;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public double getLeftBound() {
        return this.leftBound;
    }

    public double getRightBound() {
        return this.rightBound;
    }

    public int getRectangles() {
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Cannot retrieve rectangles because it has not been set!");
        } else {
            return this.rectangles;
        }
    }

    public static Result leftRiemannSums(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through Left Riemann Sums
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator leftIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return leftIntegrator.leftRiemannSums();
    }

    public Result leftRiemannSums() {
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Rectangles Have not been set!");
        }
        if (this.leftBound == this.rightBound) {
            return new Result("Left Riemann Sums", 0, this.leftBound, this.rightBound, this.rectangles);
        }

        double deltaX = (this.rightBound - this.leftBound) / rectangles;

        if (deltaX < 0.0) {
            Result result = new Integrator(this.expression, this.rightBound, this.leftBound, this.rectangles).leftRiemannSums().mult(-1);
            result.leftBound = this.leftBound;
            result.rightBound = this.rightBound;
            return result;
        }

        double total = 0.0;

        for(int i = 0, j = this.rectangles; i < j; i++) {
            total +=  this.expression.setVariable("x", this.leftBound + i * deltaX).evaluate();
        }
        total *= deltaX;

        if (Double.isInfinite(total) || Double.isNaN(total)) {
            throw new IllegalArgumentException("Your function has to be continous over the desired interval!");
        }

        return new Result("Left Riemannn Sums", total, this.leftBound, this.rightBound, this.rectangles);
    }

    public void setAll(Expression expression, double leftBound, double rightBound, int rectangles) {
        this.setExpression(expression);
        this.setLeftBound(leftBound);
        this.setRightBound(rightBound);
        this.setRectangles(rectangles);
    }

    public void setAll(Expression expression, double leftBound, double rightBound, int rectangles, double tolerance) {
        this.setAll(expression, leftBound, rightBound, rectangles);
        this.setTolerance(tolerance);
    }

    public void setAll(Expression expression, double leftBound, double rightBound, int rectangles, double tolerance,
                       int maxSteps) {
        this.setAll(expression, leftBound, rightBound, rectangles, tolerance);
        this.setMaxSteps(maxSteps);
    }

    public static Result rightRiemannSums(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through Right Riemann Sums
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator rightIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return rightIntegrator.rightRiemannSums();
    }

    public Result rightRiemannSums() {
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Rectangles Have not been set!");
        }
        double deltaX = (this.rightBound - this.leftBound) / this.rectangles;

        if (deltaX < 0.0) {
            Result result = new Integrator(this.expression, this.rightBound, this.leftBound, this.rectangles).rightRiemannSums().mult(-1);
            result.leftBound = this.leftBound;
            result.rightBound = this.rightBound;
            return result;
        }

        Integrator rightIntegrator = new Integrator(this.expression, this.leftBound + deltaX, this.rightBound + deltaX, this.rectangles);
        Result result = rightIntegrator.leftRiemannSums();
        result.leftBound = this.leftBound;
        result.rightBound = this.rightBound;
        result.setMethod("Right Riemann Sums");
        return result;
    }

    public static Result trapezium(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through The trapezium rule
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator trapeziumIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return trapeziumIntegrator.trapezium();
    }

    public Result trapezium() {
        Result result = this.leftRiemannSums().add(this.rightRiemannSums()).div(2);
        result.setMethod("Trapezoidal Method");
        return result;
    }

    public static Result midpoint(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through the midpoint rule
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator midpointIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return midpointIntegrator.midpoint();
    }

    public Result midpoint() {
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Rectangles Have not been set!");
        }
        double deltaX = (this.rightBound - this.leftBound) / this.rectangles;

        if (deltaX < 0.0) {
            Result result = new Integrator(this.expression, this.rightBound, this.leftBound, this.rectangles).midpoint().mult(-1);
            result.leftBound = this.leftBound;
            result.rightBound = this.rightBound;
            return result;
        }

        Integrator midpointIntegrator = new Integrator(this.expression, this.leftBound + (deltaX / 2), this.rightBound + (deltaX / 2), this.rectangles);
        Result result = midpointIntegrator.leftRiemannSums();
        result.setMethod("Midpoint Approximation");
        result.leftBound = this.leftBound;
        result.rightBound = this.rightBound;
        return result;
    }

    public static Result simpson(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through Simpson's Rule
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator simpsonIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return simpsonIntegrator.simpson();
    }

    public Result simpson() {
        Result result = this.midpoint().mult(2).add(this.trapezium()).div(3);
        result.setMethod("Simpson's Method");
        return result;
    }

    public void setTolerance(double tolerance) {
        if (tolerance < 0.0) {
            throw new IllegalArgumentException("Tolerance cannot be a negative number!");
        }
        this.tolerance = tolerance;
    }

    public ResultWithTolerance tolerance(int jump) {
        /**
         * Approximates a definite integral of a function through Left and Right Riemann Sums
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return ResultWithTolerance holds information about the result, most importantly its value and whether
         * the integral is obtained to the precision given. For the tolerance to work it has to be assumed that
         * the function is either all-increasing or all-decreasing on the given interval.
         */
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Initial Rectangles Have not been set!");
        }

        if (Double.isNaN(tolerance)) {
            throw new IllegalStateException("Tolerance has not been set yet!");
        }

        this.rectangles = this.rectangles + jump;
        Integrator integrator = new Integrator(this.expression, this.leftBound, this.rightBound, this.rectangles);
        Result left = integrator.leftRiemannSums();
        Result right = integrator.rightRiemannSums();
        Result average = left.add(right).div(2);
        boolean meetsTolerance;

        return new ResultWithTolerance("Tolerance", average.value, this.leftBound, this.rightBound, this.rectangles,  Math.abs(left.value - right.value) < this.tolerance
                , left, right, average);
    }

    public Result average() {
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Rectangles Have not been set!");
        }
        Integrator integrator = new Integrator(this.expression, this.leftBound, this.rightBound, this.rectangles);
        Result left = integrator.leftRiemannSums();
        Result right = integrator.rightRiemannSums();
        Result simpson = integrator.simpson();
        Result midpoint = integrator.midpoint();
        Result result = left.add(right).add(simpson).add(midpoint).div(4);
        result.setMethod("Average of left and right sums, simpson's method and midpoint rule");
        return result;
    }

    public static Result average(Expression expression, double leftBound, double rightBound, int rectangles) {
        /**
         * Approximates a definite integral of a function through an average of other methods
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        Integrator averageIntegrator = new Integrator(expression, leftBound, rightBound, rectangles);
        return averageIntegrator.average();
    }

    public Result surprise() {
        /**
         * Approximates a definite integral of a function through a random method
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return Result holds information about the result, most importantly its value
         */
        if (this.rectangles == Constants.rectanglesNotSet) {
            throw new IllegalStateException("Rectangles Have not been set!");
        }

        if (Double.isNaN(tolerance)) {
            throw new IllegalStateException("Tolerance has not been set yet!");
        } else if (maxSteps == Constants.maxStepsNotSet) {
            throw new IllegalArgumentException("maxSteps has not been set yet!");
        }

        switch (new Random().nextInt(7) + 1) {
            case Constants.simpson: return this.simpson();
            case Constants.trapezium: return this.trapezium();
            case Constants.left: return this.leftRiemannSums();
            case Constants.right: return this.rightRiemannSums();
            case Constants.midpoint: return this.midpoint();
            case Constants.average: return this.average();
            case Constants.romberg: return this.romberg();
            default: return this.simpson();
        }
    }

    public void setMaxSteps(int maxSteps) {
        if(maxSteps < 1) {
            throw new IllegalArgumentException("Steps should be a positive integer!");
        } else {
            this.maxSteps = maxSteps;
        }
    }

    public ResultWithRomberg romberg() {
        /**
         * Approximates a definite integral of a function through Romberg's Method
         * @param expression is a function that will be evaluated using exp4j
         * @param leftBound and RightBound are the lower and upper bounds of the integral, i.e. a and b respectively
         * @param rectangles is the number of sub-intervals considered for the approximation
         * @return ResultWithRomberg holds information about the result, most importantly its value
         */
        if (Double.isNaN(tolerance)) {
            throw new IllegalStateException("Tolerance has not been set yet!");
        } else if (maxSteps == Constants.maxStepsNotSet) {
            throw new IllegalArgumentException("maxSteps has not been set yet!");
        }
        double[][] T = new double[this.maxSteps+1][this.maxSteps+1];
        int rectangles = 1;

        for (int j = 1; j <= this.maxSteps; j++) {
            T[j][1] = this.simpson(this.expression, this.leftBound, this.rightBound, rectangles).value;
            for (int k = 2; k <= j; k++) {
                T[j][k] = T[j][k - 1] + ((T[j][k - 1] - T[j - 1][k - 1]) / (Math.pow(4, k - 1) - 1));
            }
            rectangles *= 2;

            if (j > 2) {
                if (Math.abs(T[j][j] - T[j-1][j-1]) < this.tolerance && Math.abs(T[j][j] - T[j-2][j-2]) < this.tolerance) {
                    return new ResultWithRomberg("Romberg's Method", T[j][j], this.leftBound, this.rightBound, j, this.tolerance);
                }
            }
        }
        return new ResultWithRomberg("Romberg's Method", T[this.maxSteps][this.maxSteps], this.leftBound, this.rightBound,
                this.maxSteps, this.tolerance);
    }

    public static Expression buildExpression(String expression) {
        /**
         * Approximates a definite integral of a function through Left Riemann Sums
         * @param expression is a function that will be evaluated using exp4j
         * @return Expression an expression that can be evaluated with exp4j. This is usually the function whose
         * integral will be estimated. The overridden functions are functions that are added to exp4j's original
         * functionality
         */
        Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };

        Function ln = new Function("ln", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };

        Function arcsin = new Function("arcsin", 1) {
            @Override
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };

        Function arccos = new Function("arccos", 1) {
            @Override
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };

        Function arctan = new Function("arctan", 1) {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };

        Function sec = new Function("sec", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.cos(args[0]);
            }
        };

        Function csc = new Function("csc", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.sin(args[0]);
            }
        };

        Function cot = new Function("cot", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.tan(args[0]);
            }
        };

        Function arccot = new Function("arccot", 1) {
            @Override
            public double apply(double... args) {
                if (args[0] > 0.0) {
                    return Math.atan(1.0/args[0]);
                } else {
                    return Math.atan(1.0/args[0]) + Math.PI;
                }
            }
        };

        Function arcsec = new Function("arcsec", 1) {
            @Override
            public double apply(double... args) {
                return Math.acos(1.0/args[0]);
            }
        };

        Function arccsc = new Function("arccsc", 1) {
            @Override
            public double apply(double... args) {
                return Math.asin(1.0/args[0]);
            }
        };

        Function acot = new Function("acot", 1) {
            @Override
            public double apply(double... args) {
                if (args[0] > 0.0) {
                    return Math.atan(1.0/args[0]);
                } else {
                    return Math.atan(1.0/args[0]) + Math.PI;
                }
            }
        };

        Function asec = new Function("asec", 1) {
            @Override
            public double apply(double... args) {
                return Math.acos(1.0/args[0]);
            }
        };

        Function acsc = new Function("acsc", 1) {
            @Override
            public double apply(double... args) {
                return Math.asin(1.0/args[0]);
            }
        };

        Function sech = new Function("sech", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.cosh(args[0]);
            }
        };

        Function csch = new Function("csch", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.sinh(args[0]);
            }
        };

        Function coth = new Function("coth", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.tanh(args[0]);
            }
        };

        Function arcsinh = new Function("arcsinh", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0] + Math.sqrt(Math.pow(args[0], 2) + 1.0));
            }
        };

        Function asinh = new Function("asinh", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0] + Math.sqrt(Math.pow(args[0], 2) + 1.0));
            }
        };

        Function arccosh = new Function("arccosh", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0] + Math.sqrt(Math.pow(args[0], 2) - 1.0));
            }
        };

        Function acosh = new Function("acosh", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0] + Math.sqrt(Math.pow(args[0], 2) - 1.0));
            }
        };

        Function arctanh = new Function("arctanh", 1) {
            @Override
            public double apply(double... args) {
                return 0.5 * Math.log((1+args[0])/(1-args[0]));
            }
        };

        Function atanh = new Function("atanh", 1) {
            @Override
            public double apply(double... args) {
                return 0.5 * Math.log((1+args[0])/(1-args[0]));
            }
        };

        Function arcsech = new Function("arcsech", 1) {
            @Override
            public double apply(double... args) {
                return Math.log((1.0 / args[0]) + Math.sqrt(Math.pow(1.0 / args[0], 2) - 1.0));
            }
        };

        Function asech = new Function("asech", 1) {
            @Override
            public double apply(double... args) {
                return Math.log((1.0 / args[0]) + Math.sqrt(Math.pow(1.0 / args[0], 2) - 1.0));
            }
        };

        Function arccsch = new Function("arccsch", 1) {
            @Override
            public double apply(double... args) {
                return Math.log((1.0 / args[0]) + Math.sqrt(Math.pow(1.0 / args[0], 2) + 1.0));
            }
        };

        Function acsch = new Function("acsch", 1) {
            @Override
            public double apply(double... args) {
                return Math.log((1.0 / args[0]) + Math.sqrt(Math.pow(1.0 / args[0], 2) + 1.0));
            }
        };

        Function arccoth = new Function("arccoth", 1) {
            @Override
            public double apply(double... args) {
                return 0.5 * Math.log((1+(1.0 /args[0]))/(1.0-(1.0 / args[0])));
            }
        };

        Function acoth = new Function("acoth", 1) {
            @Override
            public double apply(double... args) {
                return 0.5 * Math.log((1+(1.0 /args[0]))/(1.0-(1.0 / args[0])));
            }
        };

        Function gamma = new Function("gamma", 1) {
            @Override
            public double apply(double... args) {
                return Gamma.gamma(args[0]);
            }
        };

        Function digamma = new Function("digamma", 1) {
            @Override
            public double apply(double... args) {
                return Gamma.digamma(args[0]);
            }
        };

        Function erf = new Function("erf", 1) {
            @Override
            public double apply(double... args) {
                return Erf.erf(args[0]);
            }
        };

        Function trigamma = new Function("trigamma", 1) {
            @Override
            public double apply(double... args) {
                return Gamma.trigamma(args[0]);
            }
        };

        return new ExpressionBuilder(expression.replace("phi", "φ"))
                .function(logb).function(ln).function(arccos).function(arcsin)
                .function(arctan).function(sec).function(csc).function(cot)
                .function(arcsec).function(arccsc).function(arccot).function(asec)
                .function(acsc).function(acot).function(sech).function(csch).function(coth)
                .function(arcsinh).function(asinh).function(arccosh).function(acosh)
                .function(arctanh).function(atanh).function(arcsech).function(asech)
                .function(arccsch).function(acsch).function(arccoth).function(acoth)
                .function(gamma).function(digamma).function(erf).function(trigamma)
                .variable("x").build();
    }

    public static ResultWithRomberg romberg(Expression expression, double leftBound, double rightBound
    , double tolerance, int maxSteps) {
        Integrator romberIntegrator = new Integrator(expression, leftBound, rightBound, 1, tolerance, maxSteps);
        return romberIntegrator.romberg();
    }
}