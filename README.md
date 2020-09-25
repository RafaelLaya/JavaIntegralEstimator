# Integral Estimator (API, CLI, GUI)

This was written right after taking an introduction to programming class when I first got into college (late 2017, early 2018). The class is CS121 and it is taught in python. The class that follows is CS141 and it is taught in Java. I wrote the program right after taking CS121 and before taking CS141 (I made the program in the short break after summer quarter and before fall quarter). 

The instructions to use the program are simple: Download the .java files under the src directory, compile and run!

# Core Dependencies
* You will need exp4j (tested on 0.4.8)
* You will need Commons Math (tested on 3.6.1)
* This was tested using java jdk 1.8.0

The function provided must be of continous behaviour. If using the tolerance option then it is also assumed that the function is either always increasing or decreasing in the desired interval. Improper integrals are not supported.

Integrator allows the programmer to either set the variables one by one and then do the integration (Example: integrator.simpson()) or to supply the parameters to the Integrator using static functions.

This program uses the algorithms that students usually learn in a regular calculus II classroom (plus Romberg's Method).

# Integrator API Example
1. Get a String (Or build an exp4j expression yourself) and use the buildExpression method.

Expression e = Integrator.buildExpression("3x^2+cosh(x)-sin(x)");

2. Set up the needed variables for your desired method of integration and an integrator
```python
double low = 1;

double high = 5;

int rec = 5;

Integrator integrator = new Integrator(e, low, high, rec);
```

3. Calculate the integral and then print the result (you can also just get the value).

System.out.println(integrator.simpson().toString());


this prints: 
```
Method: Simpson's Method

Value: 196.78152370008988

First Bound: 1.0

Second Bound: 5.0

rectangles: 5
```

# CLI Example
[![LINK TO VIDEO](https://i.ytimg.com/vi/34m-d6_MDw4/hqdefault.jpg)](https://youtu.be/34m-d6_MDw4)

# GUI Example
[![LINK TO VIDEO](https://i.ytimg.com/vi/AO1Pq1rV828/hqdefault.jpg)](https://youtu.be/AO1Pq1rV828)

And last but not least: Keep in mind I wrote this years ago as a total newbie :) 
