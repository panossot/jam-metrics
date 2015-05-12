# How to Monitor the JBoss-Automated-Metrics using RHQ
This document is created to guide you through the steps that are required to be performed in order to monitor the JBoss-Automated-Metrics with RHQ:

* Firstly, download and configure the RHQ Server
* Change the rhq-plugin.xml located in MetricsRhqPlugin folder with the metrics that you want to monitor
* Build the plugin and load it on RHQ
* At this point, you should be able to see your MetricsServer under the Resources of RHQ (e.g. http://localhost:7080/rest/resource?ps=99999)
* Check the schedule ids of the metrics you want to monitor and configure jboss-automated-metrics using JBossAutomatedMeticsProperties api.
 
An example can be found here : https://github.com/panossot/jboss-automated-metrics/blob/master/AutomatedMetricsStandalone/AutomatedMetricsApiTest/AutomatedMetricsApiTest-web/src/main/java/org/jboss/metrics/PrintMetrics.java#L76

* At this point, you should be able to use the operations of the JBoss-Automated-Metrics that you have activated.

#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
