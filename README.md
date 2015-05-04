# Jboss-Automated-Metrics
This library is created to facilitate the creation, record and monitoring of metrics for any deployment.

## How to use
Just annotate the method of your ejb with the annotation @Metric(fieldName = {"field name of first metric","field name of second metric", etc})

## Example of usage
An example of usage can be found under the folder AutomatedMetricsApiTest.

Deploy the generated .war file on Wildfly server to see the metrics being printed.

<br/>

## JBoss-Automated-Metrics are now availbale using JAVA SE
Please, check the example in AutomatedMetricsJavaSeApiTest directory to see how you can use JBoss-Automated-Metrics with JAVA SE.

<br/>

##License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
