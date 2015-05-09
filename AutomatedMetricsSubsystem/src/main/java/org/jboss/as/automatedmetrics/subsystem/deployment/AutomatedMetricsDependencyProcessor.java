/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.automatedmetrics.subsystem.deployment;

import org.jboss.as.ee.weld.WeldDeploymentMarker;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.filter.PathFilters;

/**
 * Deployment processor which adds a module dependencies for modules needed for metrics deployments.
 *
 * @author panos
 */
public class AutomatedMetricsDependencyProcessor implements DeploymentUnitProcessor {

    private static final ModuleIdentifier ORG_JBOSS_METRICS = ModuleIdentifier.create("org.jboss.metrics.AutomatedMetrics");
    private static final ModuleIdentifier ORG_JBOSS_METRICS_API = ModuleIdentifier.create("org.jboss.metrics.AutomatedMetricsApi");
    private static final ModuleIdentifier ORG_JBOSS_METRICS_PROPERTIES = ModuleIdentifier.create("org.jboss.metrics.JBossAutomatedMetricsProperties");
    private static final ModuleIdentifier ORG_JBOSS_METRICS_LIBRARY = ModuleIdentifier.create("org.jboss.metrics.JbossAutomatedMetricsLibray");

    /**
     * Add dependencies for modules required for metric deployments
     *
     */
    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        final ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);
        final ModuleLoader moduleLoader = Module.getBootModuleLoader();

    //    ResourceRoot deploymentRoot = deploymentUnit.getAttachment(Attachments.DEPLOYMENT_ROOT);
        //   final VirtualFile rootBeansXml = deploymentRoot.getRoot().getChild("META-INF/beans.xml");
        //   final boolean rootBeansXmlPresent = rootBeansXml.exists() && rootBeansXml.isFile();
        //   System.out.println("rootBeansXmlPresent " + rootBeansXmlPresent);
    /*    Map<ResourceRoot, ExplicitBeanArchiveMetadata> beanArchiveMetadata = new HashMap<>();
         PropertyReplacingBeansXmlParser parser = new PropertyReplacingBeansXmlParser(deploymentUnit);

         ResourceRoot classesRoot = null;
         List<ResourceRoot> structure = deploymentUnit.getAttachmentList(Attachments.RESOURCE_ROOTS);
         for (ResourceRoot resourceRoot : structure) {
         if (ModuleRootMarker.isModuleRoot(resourceRoot) && !SubDeploymentMarker.isSubDeployment(resourceRoot)) {
         if (resourceRoot.getRootName().equals("classes")) {
         // hack for dealing with war modules
         classesRoot = resourceRoot;
         deploymentUnit.putAttachment(WeldAttachments.CLASSES_RESOURCE_ROOT, resourceRoot);
         } else {
         VirtualFile beansXml = resourceRoot.getRoot().getChild("META-INF/beans.xml");
         if (beansXml.exists() && beansXml.isFile()) {
         System.out.println("rootBeansXmlPresent found");
         beanArchiveMetadata.put(resourceRoot, new ExplicitBeanArchiveMetadata(beansXml, resourceRoot, parseBeansXml(beansXml, parser, deploymentUnit), false));
         }
         }
         }
         }
         */
        //   BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
        if (!WeldDeploymentMarker.isPartOfWeldDeployment(deploymentUnit)) {
            return; // Skip if there are no beans.xml files in the deployment
        }

        ModuleDependency dep = new ModuleDependency(moduleLoader, ORG_JBOSS_METRICS, false, false, true, false);
        dep.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep);

        ModuleDependency dep2 = new ModuleDependency(moduleLoader, ORG_JBOSS_METRICS_API, false, false, true, false);
        dep2.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep2.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep2);

        ModuleDependency dep3 = new ModuleDependency(moduleLoader, ORG_JBOSS_METRICS_PROPERTIES, false, false, true, false);
        dep3.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep3.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep3);

        ModuleDependency dep4 = new ModuleDependency(moduleLoader, ORG_JBOSS_METRICS_LIBRARY, false, false, true, false);
        dep4.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep4.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep4);
    }

    private void addDependency(ModuleSpecification moduleSpecification, ModuleLoader moduleLoader,
            ModuleIdentifier moduleIdentifier) {
        moduleSpecification.addSystemDependency(new ModuleDependency(moduleLoader, moduleIdentifier, false, false, true, false));
    }
    /*
     private BeansXml parseBeansXml(VirtualFile beansXmlFile, PropertyReplacingBeansXmlParser parser, final DeploymentUnit deploymentUnit) throws DeploymentUnitProcessingException {
     try {
     return parser.parse(beansXmlFile.asFileURL());
     } catch (MalformedURLException e) {
     throw WeldLogger.ROOT_LOGGER.couldNotGetBeansXmlAsURL(beansXmlFile.toString(), e);
     } catch (RuntimeException e) {
     throw new DeploymentUnitProcessingException(e);
     }
     }*/

    @Override
    public void undeploy(DeploymentUnit context) {
    }
}
