# vigilant-funicular
This repository primarily contains tests written in two styes viz. using the traditional approach with Selenium (Java) + TestNG and using Applitools Selenium JAVA SDK.

## Prerequisites
1. The path for Chromedriver is set in your system. In ```TradtionalTests.java```, modify the line #41 to reflect the path from your system.
2. Certified on Chrome 78 and the corresponding Chromedriver https://chromedriver.chromium.org/downloads.

## Steps to run
1. Clone the repository.
2. Open the project in your favorite editor (Eclipse, IntelliJ IDEA).
3. To run the traditional tests, right click on the file TraditionalTests.java -> Run As -> TestNG Test (Feel free to use the shortcut when you figure it out).
4. Observe that the Chrome browser opens up and all methods annotated as @Test start executing.
5. The test results summary will be displayed in the console. Alternatively, it can also be viewed in the browser by accessing the html files created in the test-output directory.
6. In order to run the Visual AI tests, right click on the file VisualAITests.java -> Run As -> TestNG Test
7. Observe that the Chrome browser opens up and all methods annotated as @Test start executing.
8. Navigate to Applitools dashboard to view the results.

## Note for evaluators
1. For both the approaches, I have created a TestNG test (@Test) for each of the 5 tasks mentioned on the Hackathon instructions page.
2. For Visual AI tests, I have captured both baseline and checkpoint screenshots and annotated bugs and used Layout region wherever appropriate.
