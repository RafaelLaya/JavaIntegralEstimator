/*
   * - Name: Rafael Laya
   * - Integral Estimator Program
   * - Estimates definite integrals over an interval (a, b) using common methods taught in a regular calculus II
   * classroom
   */

import net.objecthunter.exp4j.Expression;

import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        // Welcome the user and prepare variables
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- Welcome to My Integral Estimator -----");

        Expression function = Constants.functionNotSet;
        double lowerBound = Constants.rightBoundNotSet;
        double higherBound = Constants.leftBoundNotSet;
        int selection = 0;
        int rectangles = Constants.rectanglesNotSet;
        double tolerance = Constants.toleranceNotSet;
        int maxSteps = Constants.maxStepsNotSet;

        // Until the user quits
        while (true) {
            System.out.println("Choose a technique for the estimation of your integral: ");
            // Menu
            System.out.println(Constants.simpson + ". Simpson's Rule");
            System.out.println(Constants.trapezium + ". Trapezium Rule");
            System.out.println(Constants.left + ". Left Riemann Sums");
            System.out.println(Constants.right + ". Right Riemann Sums");
            System.out.println(Constants.midpoint + ". Midpoint Rule");
            System.out.println(Constants.average + ". Average Simpson's, Trapezium, Left, Right and Midpoint");
            System.out.println(Constants.tolerance + ". Left and Right Riemann Sums with tolerance (functions must have monotone behaviour over the considered interval).");
            System.out.println(Constants.romberg + ". Romberg's Method");
            System.out.println(Constants.surprise + ". Surprise me!");
            System.out.println(Constants.instructions + ". Instructions and Examples");
            System.out.println(Constants.educate + ". Educate me");
            System.out.println(Constants.newFunction + ". Another Function");
            System.out.println(Constants.newBounds + ". New Bounds");
            System.out.println(Constants.newRectangles + ". Change Number of Rectangles to use");
            System.out.println(Constants.newFuncBounRec + ". Another Function, bounds and rectangles");
            System.out.println(Constants.newMaxSteps + ". Change Maximum Steps for Romberg's Method");
            System.out.println(Constants.newTolerance + ". Change tolerance for left/right tolerance and Romberg's Method");
            System.out.println(Constants.quit + ". Quit");
            System.out.println();

            try {
                System.out.print("Selection > ");
                selection = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                // if cannot parse then just go ahead to the menu again
                System.out.println(e.getMessage());
                continue;
            }

            // if the choice is in this range the user wants to calculate integrals right away
            if (Constants.simpson <= selection && selection <= Constants.surprise) {
                // so check for what has not been set up yet and whether is it needed given the user's selection
                // i.e.: Romberg does not use rectangles and simpson does not use tolerance
                if (function == Constants.functionNotSet) {
                    function = getFunction(scanner);
                }
                if (lowerBound == Constants.leftBoundNotSet) {
                    lowerBound = getLowerBound(scanner);
                }
                if (higherBound == Constants.rightBoundNotSet) {
                    higherBound = getHigherBound(scanner);
                }
                if (rectangles == Constants.rectanglesNotSet &&
                        (selection != Constants.romberg)) {
                    rectangles = getRectangles(scanner);
                }
                if (tolerance == Constants.toleranceNotSet &&
                        (selection == Constants.tolerance || selection == Constants.romberg || selection == Constants.surprise)) {
                                 tolerance = getTolerance(scanner);
                }
                if (maxSteps == Constants.maxStepsNotSet &&
                        (selection == Constants.romberg || selection == Constants.surprise)) {
                    maxSteps = getMaxSteps(scanner);
                }
                // after everything is set up go calculate the integral
                approxIntegral(selection, function, lowerBound, higherBound, rectangles, scanner, tolerance, maxSteps);
                // otherwise just go with the remaining options in the menu
            } else if (selection == Constants.instructions) {
                instructions(scanner);
            } else if (selection == Constants.educate) {
                educate(scanner);
            } else if (selection == Constants.newFunction) {
                function = getFunction(scanner);
            } else if (selection == Constants.newBounds) {
                lowerBound = getLowerBound(scanner);
                higherBound = getHigherBound(scanner);
            } else if (selection == Constants.newRectangles) {
                rectangles = getRectangles(scanner);
            } else if (selection == Constants.newFuncBounRec) {
                 function = getFunction(scanner);
                 lowerBound = getLowerBound(scanner);
                 higherBound = getHigherBound(scanner);
                 rectangles = getRectangles(scanner);
            } else if (selection == Constants.newMaxSteps){
                maxSteps = getMaxSteps(scanner);
            } else if (selection == Constants.newTolerance) {
                tolerance = getTolerance(scanner);
            } else if (selection == Constants.quit) {
                goodbye();
                break;
            }
        }
    }

    public static void instructions(Scanner scanner) {
        System.out.println("\n\t This is a definite integral estimator. That means" +
                "your function is continous on the desired interval.");
        System.out.println("\t Most of the elementary functions are provided as well as a" +
                "few special functions");
        System.out.println("\t Supports the functions sin(x), cos(x), tan(x), csc(x), sec(x), cot(x) and their inverses, " +
                "asin(x) or arcsin(x), acos(x) or arccos(x), atan(x) or arctan(x), acsc(x) or arccsc(x), asec(x) or " +
                "arcsec(x), acot(x) or arccot(x).");
        System.out.println("\t Also the hpyerbolics sinh(x), cosh(x), tanh(x), csch(x), sech(x), coth(x), and their inverses, " +
                "asinh(x) or arcsinh(x), arccosh(x) or acosh(x), arctanh(x) or atanh(x), arccsch(x) or acsch(x), " +
                "arcsech(x) or asech(x), arccoth(x) or acoth(x). ");
        System.out.println("\t Natural Log (base e) as ln(x) or log(x). base b as logb(x, b). log2(x) for base 2 " +
                "los10 for base 10");
        System.out.println("\t Roots usually as exponents. Also sqrt(x) for square root, cbrt(x) for cubic");
        System.out.println("\t Constants golden ratio as phi or φ, euler's number as e and pi ad pi or π");
        System.out.println("\t Special functions gamma(x), digamma(x), trigamma(x) and error function erf(x)");
        System.out.println("\t Absolute value as abs(x)");
        System.out.println("\t Modulo operator as %");
        System.out.println("\t Cubic root as cbrt");
        System.out.println("\t Signum(x) for sign function. floor(x) for floor function and ceil(x) for ceiling");
        System.out.println("\t Exponential function as e^x or exp(x)");
        System.out.println("\n Also implicit multiplication, 5x^2 and 5*x^2 are equal statements.");
        System.out.println("\n\t For example, 5x^2+cosh(x)-4ln(x)+2logb(x, 2)-3log2(x)+sinh(x) is a valid expression");
        System.out.println("\t Another example, 3x+pi+sin(x)-cos(x)+tan(x)");
        System.out.println("\n");
        System.out.println("Enter any key to continue");
        scanner.nextLine();
    }

    public static int getRectangles(Scanner scanner) {
        while (true) {
            System.out.print("Number of Rectangles: ");
            try {
                int rectangles = Integer.parseInt(scanner.nextLine());

                if (rectangles < 1) {
                    System.out.println("The number of rectangles should be a positive integer. Try again.");
                    continue;
                } else {
                    return rectangles;
                }
            } catch (Exception e){
                System.out.println("The number of rectangles should be a positive integer. Try again.");
                continue;
            }
        }
    }

    public static double getBound(Scanner scanner, String question) {
        while (true) {
            System.out.print(question);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("A bound has to be a real number. Try again.");
                continue;
            }
        }
    }

    public static double getLowerBound(Scanner scanner) {
        return getBound(scanner, "Lower Bound: ");
    }

    public static double getHigherBound(Scanner scanner) {
        return getBound(scanner, "Upper Bound: ");
    }

    public static Expression getFunction(Scanner scanner) {
        while (true) {
            System.out.print("Please enter an expression of the single variable 'x': ");
            try {
                return Integrator.buildExpression(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Your expression should only depend on 'x'. PLease try again.");
                System.out.println(e.getMessage());
            }
        }
    }

    public static void goodbye() {
        System.out.println("Thanks for using my integral estimator. See you later for your integration needs!");
    }

    public static void educate(Scanner scanner) {
        System.out.print("\nThis section assumes the user is comfortable with single " +
                "variable real valued functions. This program only supports proper " +
                "integrals which means these functions have to be continous on the given" +
                " interval\n");
        System.out.print("Suppose we have such a function called f(x).\n");
        System.out.print("Suppose also that this function is non-negative in the interval (a, b)\n");
        System.out.print("The integral from x=a to x=b of the function f(x) with" +
                " respect to x is the area enclosed by the graph of the curve y=f(x) " +
                "in the plane xy and between the horizontal line y=0 (the x-axis) within" +
                " this interval (a, b)\n");
        System.out.print("Another interpretation is that if the integral of the velocity v(x) of a particle is the " +
                "displacement of the particle from timex x=a to x=b, here x represents " +
                "the time\n");
        System.out.print("Now consider the case where f(x) is negative in (a, b), then the integral is the negative of the area enclosed by the graph of the function and the x-axis.");
        System.out.print("If the function alternates sign then the function is split in intervals where it does not " +
                "change sign and the integral is the sum of all these pieces\n\n");
        System.out.print("For example: The integral from x = -5 to x = 5 of f(x)=x is the integral of the same" +
                " function from x=-5 to x=0 added to the integral from x=0 to " +
                "x=5. The first integral is the negative of the area of the triangle " +
                "of base and height five, and the second integral is the area of the " +
                "same triangle with positive sign. The total integral is the addition " +
                "of these numbers which have the same absolute value but different sign, " +
                "therefore the integral is zero (try!).\n\n");
        System.out.print("This program is aimed to estimate integrals of continous functions using the standard " +
                "numerical methods that students are usually taught in " +
                "a regular calculus II class\n\n");
        System.out.print("Recommended online resources are: Khan Academy, Coursera, Edx and MIT OpenCourseWare\n");
        System.out.print("Suggested textbooks can be found in any bookstore from the authors: James Stewart, Louis " +
                "Leithold, Edwards and Penney, Thomas, Apostol, Spivak\n");
        System.out.println("Enter any key to continue");
        scanner.nextLine();
    }

    public static void approxIntegral(int selection, Expression function, double lowerBound
    , double higherBound, int rectangles, Scanner scanner, double tolerance, int maxSteps) {
        // Create an integrator and set needed variables
        Integrator integrator = new Integrator(function, lowerBound, higherBound);
        integrator.setExpression(function);
        integrator.setLeftBound(lowerBound);
        integrator.setRightBound(higherBound);
        // For the given selection, choose the appropriate method and set up the variables needed
        // these variables were already obtained from the user in main
        // calculate the integral or show a message if there is an error
        switch(selection) {
            case Constants.simpson:
                integrator.setRectangles(rectangles);
                System.out.print("The result is...  ");
                try {
                    System.out.println(integrator.simpson().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println("Also make sure that your function is valid");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.trapezium:
                System.out.print("The result is...  ");
                integrator.setRectangles(rectangles);
                try {
                    System.out.println(integrator.trapezium().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.left:
                integrator.setRectangles(rectangles);
                System.out.print("The result is... ");
                try {
                    System.out.println(integrator.leftRiemannSums().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.right:
                integrator.setRectangles(rectangles);
                System.out.print("The result is... ");
                try {
                    System.out.println(integrator.rightRiemannSums().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.midpoint:
                integrator.setRectangles(rectangles);
                System.out.print("The result is... ");
                try {
                    System.out.println(integrator.midpoint().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.average:
                integrator.setRectangles(rectangles);
                System.out.print("The result is... ");
                try {
                    System.out.println(integrator.average().value);
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
                System.out.println("Enter any key to continue");
                scanner.nextLine();
                break;
            case Constants.tolerance:
                integrator.setRectangles(rectangles);
                System.out.println("Initiating tolerance procedure...");
                try {
                    System.out.println(leftRightTolerance(integrator, tolerance, scanner));
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
            case Constants.romberg:
                integrator.setTolerance(tolerance);
                integrator.setMaxSteps(maxSteps);
                System.out.println("The result is... ");

                try {
                    System.out.println(integrator.romberg().value);
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
            case Constants.surprise:
                integrator.setRectangles(rectangles);
                integrator.setTolerance(tolerance);
                System.out.println("The result is... ");
                integrator.setMaxSteps(maxSteps);
                try {
                    Result result = integrator.surprise();
                    System.out.println("Using method: " + result.method);
                    System.out.println(result.value);
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("\nThere has been a problem computing your integral. Make sure that your integral is continous over the given interval.");
                    System.out.println(e.getMessage());
                    System.out.println("Enter any key to continue.");
                    scanner.nextLine();
                    return;
                }
        }
    }

    public static int getMaxSteps(Scanner scanner) {
        while (true) {
            System.out.print("Maximum order of the Romberg Integration (-1 for Default): ");

            try {
                int maxSteps = Integer.parseInt(scanner.nextLine());

                if (maxSteps == -1) {
                    return Constants.defaultMaxSteps;
                } else if (maxSteps <= 0) {
                    System.out.println("This should be a positive integer. Try again.");
                    continue;
                } else {
                    return maxSteps;
                }
            } catch (Exception e) {
                System.out.println("This should be a positive integer. Try again.");
                continue;
            }
        }
    }

    public static double getTolerance(Scanner scanner) {
        while (true) {
            System.out.print("Please enter tolerance: ");
            try {
                double tolerance = Double.parseDouble(scanner.nextLine());
                if (tolerance <= 0.0) {
                    System.out.println("Your input should be a positive real number. Try again.");
                    continue;
                } else {
                    return tolerance;
                }
            } catch (Exception e) {
                System.out.println("Your input should be a positive real number. Try again.");
                continue;
            }
        }
    }

    public static double leftRightTolerance(Integrator integrator, double tolerance, Scanner scanner) {
        /**
         * Approximates a definite integral of a function through Left and Right Riemann Sumns
         * @param integrator holds all the infromation needed to take the integrals
         * @param scanner for reading input
         * @return double is the result of the approximation, whether under the given tolerance or the user quit
         */
        Result left = integrator.leftRiemannSums();
        Result right = integrator.rightRiemannSums();

        // If the function is of monotonic behaviour and the difference between its left and right riemann sums is
        // smaller than some small number, then the error is smaller than tolerance
        while (Math.abs(left.value - right.value) > tolerance) {
            System.out.println("Rectangles: " + integrator.getRectangles());
            System.out.println("Left: " + left.value);
            System.out.println("Right: " + right.value);
            System.out.println("Difference: " + (Math.abs(left.value - right.value)));
            System.out.println("Average: " + ((left.value + right.value) / 2));
            System.out.println("We have not guaranteed your initial tolerance yet.");

            while (true) {
                try {
                    System.out.print ("How much would you like to jump? (type 'q' for quit): ");
                    String answer = scanner.nextLine();

                    if (answer.toLowerCase().indexOf("q") != -1) {
                        return (left.value + right.value) / 2;
                    } else {
                        int jump = Integer.parseInt(answer);
                        if (jump < 1) {
                            System.out.println("Your input should be either 'q' or a positive integer. Try again.");
                            continue;
                        } else {
                            integrator.setRectangles(integrator.getRectangles() + jump);
                            break;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Your input should be either 'q' or a positive integer.");
                    continue;
                }
            }
            left = integrator.leftRiemannSums();
            right = integrator.rightRiemannSums();
            System.out.println();
        }
        // the exact value has to be between left and right, return its average for better result
        return (left.value + right.value) / 2;
    }
}

