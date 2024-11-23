# Mutation Testing with PIT

## Project Overview
This project aims to improve healthcare delivery by empowering field health workers with a tablet-based application. The app assists health workers by managing their schedules and sending alerts for follow-up visits, ensuring no patient is overlooked. 

### Links
- **GitHub Repository**: [MutationTestingUsingPIT](https://github.com/nishthapaul/MutationTestingUsingPIT)
- **Figma Design**: [AtyaNidan](https://www.figma.com/AtyaNidan)

## Introduction
Comprehensive testing ensures the reliability and robustness of software applications. This project focuses on rigorous testing at both unit and integration levels using mutation testing with the PIT tool. The goal is to evaluate the effectiveness of the test suite, uncover weaknesses in the codebase, and ensure high-quality performance under diverse scenarios.

## Mutation Testing
Mutation testing measures the quality of a test suite by evaluating its ability to detect faults in the code. It involves introducing deliberate changes (mutations) to the code to simulate potential errors. The primary objective is to "kill" the mutants (detect and fail them) through robust test cases.

### Key Concepts
- **Mutation Operators**: Rules to introduce changes in the code (e.g., replacing `+` with `-`, modifying logical conditions).
- **Mutant Killing**: A mutant is considered "killed" if a test case fails due to the mutation.
- **Surviving Mutants**: Mutants that pass all tests indicate potential gaps in test coverage.

Mutation testing provides deeper insights than traditional code coverage metrics by focusing on fault detection.

## Tools Used
- **PIT (Pitest)**: A popular mutation testing tool for Java.
- **Maven**: Used to manage dependencies and build the project.

## Class Diagram
(Include your class diagram image here.)

## Implementation Using PIT

### Project Setup
1. **Codebase**: A Java-based application with logically divided packages and classes.
2. **Unit Tests**: Required for PIT to evaluate how well the test suite detects errors.
3. **Build Tool**: Maven is used for build automation and managing dependencies.

### Adding PIT to Build Configuration
Add the PIT plugin to the `pom.xml` file under the `<build>` tag:
```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>VERSION</version>
    <configuration>
        <targetClasses>com.example.*</targetClasses>
        <targetTests>com.example.*Test</targetTests>
        <threads>4</threads>
    </configuration>
</plugin>
```

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
- **Mutation Score**: Percentage of mutants killed.
- **Mutant Details**: Type of mutation, location, and status (killed or surviving).
- **Surviving Mutants**: Highlight gaps in the test suite.

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
- **Dashboard Overview**: Graphs and percentages of overall mutation coverage.
- **Class-Level Views**: Statistics for each class with links to specific code sections.
- **Color-Coded Indicators**:
  - **Green**: Mutants killed by tests.
  - **Red**: Surviving mutants.
  - **Yellow**: Skipped or unexecuted mutants.

## Optimizing Test Cases
1. **Root Cause Analysis**: Identify why surviving mutants were not killed (e.g., missing tests, inadequate assertions).
2. **Enhancing Tests**: Add or modify test cases to cover edge cases and complex logic.
3. **Configuration Optimization**: Focus mutation testing on critical classes and exclude boilerplate code.

## Conclusion
By leveraging mutation testing, we ensure higher code quality and increased confidence in the application’s reliability, especially for critical healthcare use cases.
