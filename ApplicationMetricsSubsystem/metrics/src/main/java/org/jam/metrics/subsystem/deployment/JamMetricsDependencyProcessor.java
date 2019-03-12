/*
 * Copyleft 2015 
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *  ΙΔΕΑ : Everything is a potential metric .
 */

package org.jam.metrics.subsystem.deployment;

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
 * @author Panagiotis Sotiropoulos
 */
public class JamMetricsDependencyProcessor implements DeploymentUnitProcessor {

    private static final ModuleIdentifier ORG_JAM_METRICS = ModuleIdentifier.create("org.jam.metrics.jam-metrics");
    private static final ModuleIdentifier ORG_JAM_METRICS_API = ModuleIdentifier.create("org.jam.metrics.jam-metrics-api");
    private static final ModuleIdentifier ORG_JAM_METRICS_PROPERTIES = ModuleIdentifier.create("org.jam.metrics.jam-metrics-properties");
    private static final ModuleIdentifier ORG_JAM_METRICS_LIBRARY = ModuleIdentifier.create("org.jam.metrics.jam-metrics-library");
    private static final ModuleIdentifier ORG_JAM_METRICS_LIBRARY2 = ModuleIdentifier.create("org.jam.metrics.jam-metrics-library2");

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

        ModuleDependency dep = new ModuleDependency(moduleLoader, ORG_JAM_METRICS, false, false, true, false);
        dep.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep);

        ModuleDependency dep2 = new ModuleDependency(moduleLoader, ORG_JAM_METRICS_API, false, false, true, false);
        dep2.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep2.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep2);

        ModuleDependency dep3 = new ModuleDependency(moduleLoader, ORG_JAM_METRICS_PROPERTIES, false, false, true, false);
        dep3.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep3.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep3);

        ModuleDependency dep4 = new ModuleDependency(moduleLoader, ORG_JAM_METRICS_LIBRARY, false, false, true, false);
        dep4.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep4.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep4);

        ModuleDependency dep5 = new ModuleDependency(moduleLoader, ORG_JAM_METRICS_LIBRARY2, false, false, true, false);
        dep5.addImportFilter(PathFilters.getMetaInfFilter(), true);
        dep5.addExportFilter(PathFilters.getMetaInfFilter(), true);
        moduleSpecification.addSystemDependency(dep5);
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
