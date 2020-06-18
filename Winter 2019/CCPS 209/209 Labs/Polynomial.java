import java.util.Arrays;

public class Polynomial implements Comparable<Polynomial> {

    //Instance variables
    int[] coefficients; //Array of the polynomial's coefficients
    int degree;         //The polynomial's degree

    //Constructor
    public Polynomial(int[] coefficients) {
        //The constructor will throw an IllegalArgumentException 
        //if given an empty array or a null value for its arguments.
        if (coefficients == null || coefficients.length == 0) {
            throw new IllegalArgumentException();
        }
        
        //It is assumed that the highest degree of the polynomial
        //is equal to the last index of the input coefficient array.
        this.degree = coefficients.length - 1;

        //The degree index must be checked for redundant leading zeroes.
        //If one is found, the while-loop decrements the degree index,
        // then checks the next coefficient until it finds a non-zero value.
        //This eliminates leading zeroes from the polynomial
        //formed from the input coefficient array.
        //If no non-zero is found, the while-loop will halt once
        //the degree index reaches 0.
        while (degree > 0 && coefficients[degree] == 0) {
            degree--;
        }

        //Copying the non-redundant range of  the input coefficient array into 
        //the constructor object's coefficient array ensures immutability.
        this.coefficients = Arrays.copyOfRange(coefficients, 0, degree + 1);
    }

    //Overridden Methods
    //**********************//
    @Override
    public int compareTo(Polynomial other) {
        //The comparison result is initially 
        //assumed to be equal
        int result = 0;

        //Degrees of the polynomials being compared.
        int degreeA = this.getDegree();
        int degreeB = other.getDegree();

        //If the polynomials are of equal degree,
        //the coefficients of each term will
        //be compared.
        if (degreeA == degreeB) {

            //The while-loop will compare the coefficients of each
            //equal-degree term of the polynomials.
            //If it finds inequal coefficients, it will set the
            //result to match the greater polynomial and 
            //then cease loop-execution.
            //If no unequal terms are found, the loop will cease
            //execution after the zeroeth terms have been compared,
            //thus leaving the result unchanged from its initial 
            //assumption of equality.
            while (result == 0 && degreeA >= 0) {

                //Coefficients of each term.
                int coeffA = this.getCoefficient(degreeA);
                int coeffB = other.getCoefficient(degreeA);

                //If the coefficients of equal-degree terms
                //are found to be different, their difference
                //will be used to set the result to indicate
                //which one is greater.
                if (coeffA - coeffB != 0) {
                    result = coeffA - coeffB > 0 ? 1 : -1;
                }
                //Decrementing the degree of the terms being compared 
                //before returning to the loop-condition.
                degreeA--;
            }
        } else {
            //If the polynomials are of different degrees, 
            //their difference in degree will be compared to
            //set the result to indicate which one is greater.
            result = degreeA - degreeB > 0 ? 1 : -1;
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        //Assumption is that the other object is not equal to 
        //the Polynomial object calling its equals method.
        boolean equal = false;
        //First checking if the other object is of type Polynomial
        if (other instanceof Polynomial) {

            //If the other object is a Polynomial, it is downcasted
            //to Polynomial type for the .compareTo() argument 
            //and compared to the calling Polynomial object.
            //The int value returned by the operation will be 
            //compared to 0, so as to set the boolean equal to
            //true if the polynomial comparison returns 0.
            equal = this.compareTo((Polynomial) other) == 0;
        }
        return equal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Arrays.hashCode(this.coefficients);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder polyStr = new StringBuilder();

        //Starting from the highest degree index.
        for (int i = coefficients.length - 1; i >= 0; i--) {

            //Degrees indexes with zero coefficients will be skipped,
            //unless the degree index is zero.
            //In the zero case, the zero must be output as
            //the coefficient of the zero polynomial.
            if (coefficients[i] != 0 || i == 0) {

                //This logic prevents the plus sign from appearing
                //before the first polynomial term in the string.
                if (polyStr.length() != 0) {
                    if (coefficients[i] > 0) {
                        polyStr.append("+ ");
                    }
                }
                //However, a negative sign can appear before
                //the first term in the string.
                if (coefficients[i] < 0) {
                    polyStr.append("- ");
                }

                //The coefficient can be omitted if it is equal to 1,
                //otherwise it will appear in the string.
                //The exception is when the degree is equal to 0,
                //as then a coefficient of 1 is a constant and must
                //appear in the String representation.
                if (coefficients[i] != 1 || i == 0) {
                    //Absolute value is used because the sign of the 
                    //coefficient has already been added to the string.
                    polyStr.append(Math.abs(coefficients[i]));
                }

                //The zero index of the coefficient array (constant 
                //term in the polynomial) will not have 'x'.
                if (i != 0) {
                    polyStr.append("x");

                    //Zero and first index will not have exponents
                    if (i != 1) {
                        polyStr.append("^").append(i);
                    }
                    //A space will be added for ideal formatting
                    polyStr.append(" ");
                }
            }
        }
        //Returning the string that has been formed.
        return polyStr.toString();
    }

    //Instance methods
    //**************************
    public int getDegree() {
        return degree;
    }

    public int getCoefficient(int k) {
        if (k < 0 || k > coefficients.length - 1) {
            return 0;
        }
        return coefficients[k];
    }

    public long evaluate(int x) {
        //Assuming that the evaluation is zero
        long eval = 0;
        //Method logic will run if there is no constant 
        //term and the variable is non-zero.
        //If these are conditions are not met, then
        //the evaluation will always be zero and 
        //is unnecessary.
        if (x == 0 && coefficients[0] == 0) {
            return eval;
        }
        //getDegree is used to find the highest index
        //to start from (in case the coefficient array
        //contains any leading zeroes).
        int degree = getDegree();
        do {
            //No evaluation is done on a coefficient of zero.
            if (coefficients[degree] != 0) {
                eval += coefficients[degree] * Math.pow(x, degree);
            }
            //After a coefficient is done/skipped, the degree
            //must be decremented to continue evaluating.
            degree--;
        } while (degree >= 0);

        return eval;
    }

    public Polynomial add(Polynomial other) {
        //The largest possible degree of the sum of two Polynomials
        //is the largest degree possessed by either one.
        //For this reason, the degree of the sum coefficient array will
        //be set to the highest degree of the two operands.
        int sumDegree = (this.getDegree() >= other.getDegree()) ? this.getDegree() : other.getDegree();
        int[] coeffSum = new int[sumDegree + 1];
        
         //**Note - this does not eliminate the possibility of 
        //redundant leading zeroes in the generated coefficient array.
        //(ie - the sum of a leading coefficient with its negation)
        for (int i = 0; i <= sumDegree; i++) {
            //The coefficients of the sum array are generated by adding
            //the coefficients for each degree of the polynomials.
            coeffSum[i] = this.getCoefficient(i) + other.getCoefficient(i);
        }
        //A new Polynomial object is returned to the calling method.
        //This Polynomial is generated by the Polynomial constructor,
        //using the coefficient array generated from the sum.
        return new Polynomial(coeffSum);
    }

    public Polynomial multiply(Polynomial other) {
        int factorA = this.getDegree();
        int factorB = other.getDegree();
        //The size of the coefficient array of the product
        //is equal to the sum of each polynomial's degree plus 1
        int product[] = new int[1 + factorA + factorB];
        
        //Multiplication will begin with multiplying every term in 
        //Polynomial B (other) by the highest term in 
        //Polynomial A (this). Once every term in B has been
        //multiplied, the multiplying term of A will be decremented
        //and the process will repeat.
        for (int i = factorA; i >= 0; i--) {
            for (int j = factorB; j >= 0; j--) {
                //The degree of the product of multiplied terms will be the sum
                //of the degrees of the terms.
                //Thus, the coefficient of the product of multiplied terms will be 
                //stored in the coefficient array at the index matching the sum
                //of the operands' degrees.
                //The product coefficent found from any term multiplication must be
                //added to the coefficients already found from previous term 
                //multiplication with a product of equal degree.
                product[i + j] += this.getCoefficient(i) * other.getCoefficient(j);
            }
        }
        return new Polynomial(product);
    }
}
