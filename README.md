![Java CI with Maven](https://github.com/student2ua/schedule-vaaadin/workflows/Java%20CI%20with%20Maven/badge.svg)

# Skeleton Starter for Vaadin

This project can be used as a starting point to create your own Vaadin application.
It has the necessary dependencies and files to help you get started.

The best way to use it is via [vaadin.com/start](https://vaadin.com/start) - you can get only the necessary parts and choose the package naming you want to use.
There is also a [getting started tutorial](https://vaadin.com/tutorials/getting-started-with-flow) based on this project.

To access it directly from github, clone the repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in the browser.

If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.

+
[Supported clients and formats](https://help.github.com/en/packages/publishing-and-managing-packages/about-github-packages#supported-clients-and-formats)

[GitHub Actions and Maven releases](https://blog.frankel.ch/github-actions-maven-releases/)
+
[icon`s]
https://vaadin.com/docs/v14/flow/pwa/tutorial-pwa-icons.html

[jetty-runner]
java -jar jetty-runner-9.4.26.jar --host 192.168.XXX.XXX --port 80 --out log\yyyy_mm_dd-output.log --log log\yyyy_mm_dd-requests.log my.war
