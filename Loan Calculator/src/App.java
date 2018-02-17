/*
* Date: 2/07/2018
* Author: Jared Dalessandro
*/

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

/*
Maybe implement another button to reset the values?
 */

public class App extends Application {

    //Will be used in getData()
    private final String INTEREST_RATES = "interestRates.txt";
    private final String LOAN_TERMS = "loanterms.txt";
    private ComboBox<String> termCombo = null;
    private ComboBox<String> interestCombo = null;
    public ArrayList<String> interestList = new ArrayList<String>();
    public ArrayList<String> termList = new ArrayList<String>();


    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        Button calcBtn = new Button("Calc Payment");
        Button cancelBtn = new Button("Cancel");
        getData();

        //MAX amount of income loan can take up
        final double PERCENT_INCOME = .25;

        //Pane SPECS
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20,20,20,20));
        pane.setStyle("-fx-background-color: LIGHTBLUE");

        /*
            Convert ArrayLists into ObservableLists for the Combo-boxes
        */
        ObservableList<String> termState = FXCollections.observableArrayList(termList);
        ObservableList<String> interestState = FXCollections.observableArrayList(interestList);
        termCombo = new ComboBox<String>();
        termCombo.setEditable(false);
        termCombo.setPromptText("Select Term Length");
        termCombo.setItems(termState);
        interestCombo = new ComboBox<String>();
        interestCombo.setEditable(false);
        interestCombo.setPromptText("Select Interest Rate");
        interestCombo.setItems(interestState);

        pane.add(new Label("Term In Years"), 3, 1);
        pane.add(interestCombo, 4,0);
        pane.add(new Label("Annual Interest Rate"), 3, 0);
        pane.add(termCombo, 4,1);


        /*
        Each TextField gets its own eventHandlerObj
        with itself assigned to the eventHandlerObj's parameter
        Each eventhandler uses the setOnKeyTyped registration method
        */

        //Person Values
        pane.add(new Label("Salary & Wages"), 0,0);
        TextField tfSalary = new TextField();
        tfSalary.setPromptText("- Required Field -");
        CheckKey checkKeyObj1 = new CheckKey(tfSalary);
        tfSalary.setOnKeyTyped(checkKeyObj1);
        pane.add(tfSalary, 1,0);


        pane.add(new Label("Interest Income"), 0 , 1);
        TextField tfInterest = new TextField();
        tfInterest.setPromptText("- Required Field -");
        CheckKey checkKeyObj2 = new CheckKey(tfInterest);
        tfInterest.setOnKeyTyped(checkKeyObj2);
        pane.add(tfInterest, 1,1);


        pane.add(new Label("Investment Income"), 0, 2);
        TextField tfInvestment = new TextField();
        tfInvestment.setPromptText("- Required Field -");
        CheckKey checkKeyObj3 = new CheckKey(tfInvestment);
        tfInvestment.setOnKeyTyped(checkKeyObj3);
        pane.add(tfInvestment, 1,2);


        pane.add(new Label("Other Income"), 0, 3);
        TextField tfOtherIncome = new TextField();
        tfOtherIncome.setPromptText("- Required Field -");
        tfOtherIncome.setOnKeyTyped(new CheckKey(tfOtherIncome));
        pane.add(tfOtherIncome, 1,3);

        pane.add(new Label("Total Income"), 0, 4);
        TextField tfTotalIncome = new TextField();
        tfTotalIncome.setEditable(false);
        pane.add(tfTotalIncome, 1,4);

        //Loan Values
        pane.add(new Label("Loan Amount"), 3, 2);
        TextField tfLoanAmount = new TextField();
        tfLoanAmount.setPromptText("- Required Field -");
        CheckKey checkKeyObj7 = new CheckKey(tfLoanAmount);
        tfLoanAmount.setOnKeyTyped(checkKeyObj7);
        pane.add(tfLoanAmount, 4,2);

        pane.add(new Label("Monthly Payment"), 3, 3);
        TextField tfMonthlyPayment = new TextField();
        tfMonthlyPayment.setEditable(false);
        pane.add(tfMonthlyPayment, 4,3);

        pane.add(new Label("Total Amount to be Paid"), 3, 4);
        TextField tfTotalPaid = new TextField();
        tfTotalPaid.setEditable(false);
        pane.add(tfTotalPaid, 4,4);


        //Text Eligibility altered by calculation
        Text txtEligibility = new Text();
        pane.add(txtEligibility, 3, 5);

        //Set Buttons, bottom left
        pane.add(calcBtn, 0, 5);
        pane.add(cancelBtn, 1, 5);

        //And Scene...
        Scene scene = new Scene(pane, 700, 250);

        primaryStage.setTitle("Loan Eligibility Calculator");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


        //****************************************** METHODS AND HANDLERS *****************************************


        ///Button handlers lambda style
        cancelBtn.setOnAction((ActionEvent e) -> {
            System.exit(0); //Kills application
        });

        /*
        Sends person related inputs to person obj, calculates totalIncome and setsText.
        Sends loan related inputs to loan obj, calcs monthly payment and total amount to be paid.
        Calculates Eligiblity with isEligible() being called.
        Alters Text Value based on eligibility.
        */

        calcBtn.setOnAction((ActionEvent e) -> {
            double salary = Double.parseDouble(tfSalary.getText());
            double interestIncome = Double.parseDouble(tfInterest.getText());
            double investmentIncome = Double.parseDouble(tfInvestment.getText());
            double otherIncome = Double.parseDouble(tfOtherIncome.getText());
            Person person = new Person(salary, interestIncome, investmentIncome, otherIncome);

            double annualInterestRate = Double.parseDouble(interestCombo.getValue());
            double termInYears = Double.parseDouble(termCombo.getValue());
            double loanAmount = Double.parseDouble(tfLoanAmount.getText());
            Loan loan = new Loan(annualInterestRate, termInYears, loanAmount);

            loan.calcPayments();

            //Fill in Values
            //Rounds values
            tfTotalIncome.setText(String.format("$%.2f", person.getTotalIncome()));
            tfMonthlyPayment.setText(String.format("$%.2f", loan.getMonthlyPayment()));
            tfTotalPaid.setText(String.format("$%.2f", loan.getTotalPayments()));

            //Alter Text indicating eligibility
            isEligible(person, loan, txtEligibility, PERCENT_INCOME);
        });

    }


    //Eligible if monthly loan payment is less than 25% of monthly income
    public void isEligible(Person person, Loan loan, Text txtEligibility, double PERCENT_INCOME){

        double monthlyIncome = (person.getTotalIncome() / 12);


        //Calcs percentage of income that would be taken by monthly loan payment
        double portionofIncome = loan.getMonthlyPayment() / monthlyIncome;

        //If loan payment is less than Percent_Income allowed they are eligible
        if(portionofIncome <= PERCENT_INCOME){

            txtEligibility.setText("Congrats!, you are eligible\n" +
                                   "Loan portion of income: " + String.format("$%.2f", portionofIncome * 100) + "%");
            txtEligibility.setFill(Color.GREEN);
        }
        else{
            txtEligibility.setText("Not eligible!\n" +
                                   "Loan portion of income: " + String.format("$%.2f", portionofIncome * 100) + "%");
            txtEligibility.setFill(Color.RED);
        }
    }

    /*
    Creates File objs, uses Scanner to read them line by line, and enters values into respective ArrayLists.
    */
    public void getData(){

        File interestFile = new File(INTEREST_RATES);
        File termFile = new File(LOAN_TERMS);
        String tempVal = null;

        try {
            Scanner interestScanner = new Scanner(interestFile);
            Scanner termScanner = new Scanner(termFile);


            while (interestScanner.hasNext()) {
                tempVal = interestScanner.nextLine();
                interestList.add(tempVal);
            }

            while (termScanner.hasNextLine()) {
                tempVal = termScanner.nextLine();
                termList.add(tempVal);
            }

        }
        catch(Exception e){
            System.out.println("ERROR HANDLING FILES");
        }
    }

    public static void main(String[] args){
        launch(args);
    }

}


/*
Checks for non-numeric keys
Uses the textfield as a parameter takes in constructor
checks if the text includes any non-numerics using regex
Syntax: tfOtherIncome.setOnKeyTyped(new CheckKey(tfOtherIncome))
*/
class CheckKey implements EventHandler<KeyEvent> {

    TextField tf;

    public CheckKey(TextField tf){
        this.tf = tf;
    }

    public void handle(KeyEvent e) {

        if(!(tf.getText().matches("[0-9]+"))) {
            //tf.setBackground();
            tf.setStyle("-fx-background-color: firebrick");
        }
        else {
            tf.setStyle("-fx-background-color: white");
        }
    }
}
