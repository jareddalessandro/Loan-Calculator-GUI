//Holds user information
//Calculates total income

public class Person {
    double salaryIncome;
    double interestIncome;
    double investmentIncome;
    double otherIncome;
    double totalIncome;

    //Calculates total income in constructor
    public Person(double salaryIncome, double interestIncome, double investmentIncome, double otherIncome) {
        this.salaryIncome = salaryIncome;
        this.interestIncome = interestIncome;
        this.investmentIncome = investmentIncome;
        this.otherIncome = otherIncome;
        this.totalIncome = (salaryIncome + interestIncome + investmentIncome + otherIncome);
    }


    public Person(){

    }

    //Constructor does this anyways, but in case we need to use with default constructor
    public void calcTotalIncome(){
        totalIncome = (salaryIncome + interestIncome + investmentIncome + otherIncome);
        setTotalIncome(totalIncome);
    }

    public double getSalaryIncome() {
        return salaryIncome;
    }

    public void setSalaryIncome(double salaryIncome) {
        this.salaryIncome = salaryIncome;
    }

    public double getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(double interestIncome) {
        this.interestIncome = interestIncome;
    }

    public double getInvestmentIncome() {
        return investmentIncome;
    }

    public void setInvestmentIncome(double investmentIncome) {
        this.investmentIncome = investmentIncome;
    }

    public double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(double otherIncome) {
        this.otherIncome = otherIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

}
