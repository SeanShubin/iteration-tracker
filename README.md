Iteration Tracker
===

Tracks time for an iteration

- Burndown chart
- Time spent chart

Dependencies

- [http values](https://github.com/SeanShubin/http-values)
- [web sync](https://github.com/SeanShubin/web-sync)

Usage assuming port 4000 and javascript overrides at gui/src/main/resources/

- mvn clean install
- java -jar console/target/iteration-tracker.jar 4000 gui/src/main/resources/

To build with dependency analysis

- build [dependency analyzer](https://github.com/SeanShubin/dependency-analyzer)
- mvn clean install -Pdependency
