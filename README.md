# Jboss-Automated-Metrics
This library is created to facilitate the creation and record of metrics for any deployment.

# How to use
Just anotate the method of your ejb with the annotation @Metric(fieldName = {"field name of first metric","field name of second metric", etc})

# Example of use
An example of use can be found under the folder AutomatedMetricsApiTest.
Deploy the generated .war file at the server to see the metrics being printed.

#License 
GNU Lesser General Public License Version 2.1
