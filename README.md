# JAM-Application-Metrics 
=====================================

## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
=====================================

This library is created to facilitate the creation, record and monitoring of metrics for any deployment. (Initial project JBoss-Automated Metrics renamed)

## How to use
Just define in your deployment the operations of JAM-Application-Metrics that you would like to enable using the JBossAutomatedMeticsProperties api.

Then, annotate the method of your ejb with the annotation @Metric(fieldName = {"field name of first metric","field name of second metric", etc}, deploymentName="some name characteristic to the deployment") .

###### The annotation parameter deploymentName is used for grouping of metrics under the same metric-properties configuration.

## Example of usage
An example of usage can be found under the folder ApplicationMetricsStandalone/ApplicationMetricsApiTest.

Deploy the generated .war file on Wildfly server to see the metrics being printed.


<br/>

## JAM-Application-Metrics are now available as a Wildfly Subsystem Extension
Please, read WILDFLY_SUBSYSTEM_EXTENSION.md to see how you can add JAM-Application-Metrics as a Wildfly Subsystem Extension.


<br/>

## JAM-Application-Metrics are now availbale using JAVA SE
Please, check the example in JavaSeApplicationMetrics/ApplicationMetricsJavaSeApiTest directory to see how you can use JAM-Application-Metrics with JAVA SE.

<br/>

## License 
JAM-Application-Metrics are released under Apache License, Version 2.0 as described in the LICENSE document


Copyleft 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

#### This project uses a part of jmathplots library which is released under thE BSD license.

