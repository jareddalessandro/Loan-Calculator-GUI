//Holds the loan information


public class Loan {
    double annualInterestRate;
    double termInYears;
    double loanAmount;
    double monthlyPayment;
    double totalPayments;

    //Only takes interestRate, termInYears, and loanAmount
    //Need to calc monthlyPayment and totalPayments
    public Loan(double annualInterestRate, double termInYears, double loanAmount) {
        this.annualInterestRate = annualInterestRate;
        this.termInYears = termInYears;
        this.loanAmount = loanAmount;
    }

    public Loan(){

    }

    //Calculates both monthly payment and totalPayments
    public void calcPayments(){

        //Assuming 8% is entered as 8
        double monthlyInterestRate = (annualInterestRate / 100) / 12;
        double monthsPaying = termInYears * 12;

        monthlyPayment = (loanAmount * monthlyInterestRate) /
                         (1 - (1 / Math.pow(1 + monthlyInterestRate, monthsPaying)));

        setMonthlyPayment(monthlyPayment);

        //Calculate the total amount of money paid
        totalPayments = (monthlyPayment * monthsPaying);
        setTotalPayments(totalPayments);
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public double getTermInYears() {
        return termInYears;
    }

    public void setTermInYears(double termInYears) {
        this.termInYears = termInYears;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(double totalPayments) {
        this.totalPayments = totalPayments;
    }
}
