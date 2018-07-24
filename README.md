# JAM-Application-Metrics 
--------------------------

## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
-------------------------------------

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

## JAM Presentation
[JAM Presentation](http://redhat.slides.com/panossot/jboss-automated-metrics-6?token=d93QLP5k)

<br/>

## JAM Possible Next Steps
- Memory management of the group metrics
- Integration of gaeger (another tracing system)
- Send the metrics to the JBoss Console

<br/>

## License 
* [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

#### This project uses a part of jmathplot library which is released under thE BSD license.

