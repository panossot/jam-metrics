# How to Monitor the JBoss-Automated-Metrics using RHQ
This document is created to guide you through the steps that are required to be performed in order to monitor the JBoss-Automated-Metrics with RHQ:

* Firstly, download and configure the RHQ Server
* Change the rhq-plugin.xml located in MetricsRhqPlugin folder with the metrics that you want to monitor
* Build the plugin and load it on RHQ
* At this point, you should be able to see your MetricsServer under the Resources of RHQ (e.g. http://localhost:7080/rest/resource?ps=99999)
* Check the schedule ids of the metrics you want to monitor and add them to the jboss-automated-metrics.xml which you can find under the resources of the folder MetricsRhqPlugin.
* Add -rhqMonitoring=true- and -metricsXmlPath=(the path of jboss-automated-metrics.xml)- as a system properties.
* If your RHQ Server is running on localhost:7080 and has default Username:rhqadmin and Password:rhqadmin then you are done and you should be able to see your metrics monitored on RHQ Server. In another case, you should also set the system properties rest.server, rest.port, rhqUsername, rhqPassword according to the configuration of the RHQ Server.

#License 
* [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html)
