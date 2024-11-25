## Project Overview
This project aims to enhance financial management by creating a comprehensive system for tracking transactions, managing budgets, generating reports, and achieving financial goals. The system is tested rigorously using **mutation testing** to ensure its robustness and reliability.

---

## Introduction
Comprehensive testing ensures the reliability and robustness of software applications. This project focuses on rigorous testing at both unit and integration levels using mutation testing with the PIT tool. The goal is to evaluate the effectiveness of the test suite, uncover weaknesses in the codebase, and ensure high-quality performance under diverse scenarios.

## Mutation Testing
Mutation testing measures the quality of a test suite by evaluating its ability to detect faults in the code. It involves introducing deliberate changes (mutations) to the code to simulate potential errors. The primary objective is to "kill" the mutants (detect and fail them) through robust test cases.

<div style="background-color: #f9f9f9; padding: 10px; border-left: 5px solid #007acc;">
    <h3 style="color: #007acc;">Key Concepts</h3>
    <ul>
        <li><strong>Mutation Operators:</strong> Rules to introduce changes in the code (e.g., replacing <code>+</code> with <code>-</code>, modifying logical conditions).</li>
        <li><strong>Mutant Killing:</strong> A mutant is considered "killed" if a test case fails due to the mutation.</li>
        <li><strong>Surviving Mutants:</strong> Mutants that pass all tests indicate potential gaps in test coverage.</li>
    </ul>
    <p>Mutation testing provides deeper insights than traditional code coverage metrics by focusing on fault detection.</p>
</div>

## Tools Used
- <strong>PIT (Pitest):</strong> A popular mutation testing tool for Java.
- <strong>Maven:</strong> Used to manage dependencies and build the project.

## Implementation Using PIT

### Class Diagram

<img width="1440" alt="Screenshot 2024-11-24 at 12 43 50 AM" src="https://github.com/user-attachments/assets/03b2ee9d-cc51-4e05-bd59-b8043c92345d">

## Class Description

`account`: The `Account` class manages financial operations for a user. It supports income and expense transactions, budget management for specific categories, and maintains a wallet with deposit and withdrawal functionalities. The class also provides a history of all transactions and associated budgets.


`analytics`: `SpendingAnalytics.java` provides tools to analyze financial transactions. It calculates the percentage of expenses by category and identifies unusual transactions based on a statistical threshold. This class uses streams for data grouping and reduction, enabling detailed spending insights and anomaly detection.

`bills`: `Bill.java` represents a financial bill with properties such as amount, due date, category, and payment status. It supports marking a bill as paid, checking overdue status, calculating days until due, and generating the next bill for recurring payments based on the specified recurrence pattern. `BillManager.java` handles the management of bills, including adding bills, identifying overdue and upcoming bills, processing recurring bills, and sending reminders. It leverages a `NotificationService` to notify users about upcoming payments.

`budget`:`Budge.javat` represents a financial budget for a specific category. It tracks spending, calculates the remaining budget, and checks if spending has exceeded the set limit. It uses the `Money` class for currency management.

`goals`: `FinancialGoal.java` represents a financial target with a specific amount, date, and category. It tracks progress toward the goal, calculates the remaining amount, and provides insights such as the required monthly savings to meet the target.

`manager`: `FinanceManager.java` handles the management of financial accounts, budgets, and transactions. It acts as a central controller for creating accounts, transferring money, and accessing financial data.

`models`: `Currency.java`, `Money.java`, and `Wallet.java`,manage currencies, monetary values, and wallet operations. Currency handles conversions between different currencies using predefined factors. Money encapsulates monetary operations like addition, subtraction, and conversion while ensuring validity. Wallet manages deposits, withdrawals, and total amounts across different currencies.

`notifications`: These classes implement notification strategies for sending alerts via SMS and Email. `SMSNotification.java` sends notifications to a specified phone number. `EmailNotification.java` sends notifications to a specified email address. Both implement the `NotificationStrategy` interface for consistent behavior.

`reports`: `FianancialReports.javaa` generates a financial report for a specified period, summarizing income by source and expenses by category, while converting values to a given currency.

`savings`: `AutomaticSavingsManager.java` manages automatic savings rules, processing transactions and transferring savings to target accounts based on defined rules. `SavingsRule.java` defines savings rules such as percentage of income, round-up, and fixed amount, calculating savings based on transaction type and parameters.

`tags`: `TransactionTag.java` represents a tag for a transaction with a name, description, and color, and includes equality and hashcode methods based on the tag name. `TagManager.java` manages transaction tags, allowing for creating tags, tagging transactions, retrieving transactions by tag, and grouping transactions by tag.

`transactions`: `Transaction.java` abstract class representing a transaction with fields for amount, description, type, and date. It includes an abstract method to generate a transaction summary. `IncomeTransaction.java` extends `Transaction` to represent income transactions, with an additional field for the source of income. It overrides the method to return a formatted summary. `ExpenseTransaction.java` extends `Transaction` to represent expense transactions, with an additional field for the expense category. It overrides the method to return a formatted summary. `TransferTransaction.java` extends `Transaction` to represent transfer transactions between accounts. It includes source and target accounts and overrides the method to return a formatted summary.





### Project Setup
1. **Codebase:** A Java-based application with logically divided packages and classes.
2. **Unit Tests:** Required for PIT to evaluate how well the test suite detects errors.
3. **Build Tool:** Maven is used for build automation and managing dependencies.

<div style="background-color: #eef9ff; padding: 10px; border: 1px solid #cce7ff; border-radius: 5px;">
    <h4 style="color: #007acc;">Adding PIT to Build Configuration</h4>
    <pre style="background-color: #f4f4f4; padding: 10px; border-radius: 5px;">
<code>&lt;plugin&gt;
    &lt;groupId&gt;org.pitest&lt;/groupId&gt;
    &lt;artifactId&gt;pitest-maven&lt;/artifactId&gt;
    &lt;version&gt;1.17.1&lt;/version&gt;
    &lt;configuration&gt;
        &lt;mutators&gt;
            &lt;mutator&gt;ALL&lt;/mutator&gt;
        &lt;/mutators&gt;
    &lt;/configuration&gt;
&lt;/plugin&gt;
    </code></pre>
</div>

### Running PIT
Use the following Maven command to execute mutation testing:
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```
This command generates mutants and evaluates the test suite.

### Reviewing the Report
After execution, PIT generates a detailed HTML report located in:
```
target/pit-reports
```
Open the report in a browser to analyze:
- **Mutation Score:** Percentage of mutants killed.
- **Mutant Details:** Type of mutation, location, and status (killed or surviving).
- **Surviving Mutants:** Highlight gaps in the test suite.

## PIT Test Coverage Report
The report provides the following insights:

### Mutation Coverage Score
The percentage of mutants killed by the test suite.

### Types of Mutations
Common mutation types include:
- **Conditionals Boundary Mutator**
- **Increments Mutator**
- **Math Mutator**

### Interactive HTML Report
- **Dashboard Overview:** Graphs and percentages of overall mutation coverage.
- **Class-Level Views:** Statistics for each class with links to specific code sections.
- **Color-Coded Indicators:**
  - **Green:** Mutants killed by tests.
  - **Red:** Surviving mutants.
  - **Yellow:** Skipped or unexecuted mutants.
    
![WhatsApp Image 2024-11-24 at 6 55 12 PM](https://github.com/user-attachments/assets/7d1a86d0-09a5-4009-b8a3-6338a7e453cc)

![WhatsApp Image 2024-11-24 at 6 55 26 PM](https://github.com/user-attachments/assets/3b7d281c-3285-4c80-b7dc-ef9fb3c9aa1b)

## Optimizing Test Cases
1. **Root Cause Analysis:** Identify why surviving mutants were not killed (e.g., missing tests, inadequate assertions).
2. **Enhancing Tests:** Add or modify test cases to cover edge cases and complex logic.
3. **Configuration Optimization:** Focus mutation testing on critical classes and exclude boilerplate code.

## Conclusion
By leveraging mutation testing, we ensure higher code quality and increased confidence in the application’s reliability, especially for critical financial use cases.

