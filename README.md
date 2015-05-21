# Jboss-Automated-Metrics
This library is created to facilitate the creation, record and monitoring of metrics for any deployment.

## How to use
Just define in your deployment the operations of JBoss-Automated-Metrics that you would like to enable using the JBossAutomatedMeticsProperties api.

Then, annotate the method of your ejb with the annotation @Metric(fieldName = {"field name of first metric","field name of second metric", etc}, deploymentName="some name characteristic to the deployment") .

######The annotation parameter deploymentName is used for grouping of metrics under the same metric-properties configuration.

## Example of usage
An example of usage can be found under the folder AutomatedMetricsStandalone/AutomatedMetricsApiTest.

Deploy the generated .war file on Wildfly server to see the metrics being printed.


<br/>

## JBoss-Automated-Metrics are now availbale as a Wildfly Subsystem Extension
Please, read WILDFLY_SUBSYSTEM_EXTENSION.md to see how you can add JBoss-Automated-Metrics as a Wildfly Subsystem Extension.


<br/>

## JBoss-Automated-Metrics are now availbale using JAVA SE
Please, check the example in JavaSeAutomatedMetrics/AutomatedMetricsJavaSeApiTest directory to see how you can use JBoss-Automated-Metrics with JAVA SE.

<br/>

##License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
