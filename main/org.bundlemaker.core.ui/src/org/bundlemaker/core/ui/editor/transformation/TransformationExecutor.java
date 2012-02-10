package org.bundlemaker.core.ui.editor.transformation;

//import static org.bundlemaker.core.ui.editor.transformation.DslUtils.asIModuleIdentifier;
//import static org.bundlemaker.core.ui.editor.transformation.DslUtils.nullSafeAsArray;


public class TransformationExecutor {

  // private final IModularizedSystem _system;
  //
  // private final TransformationModel _model;
  //
  // public TransformationExecutor(IModularizedSystem system, TransformationModel model) {
  // _system = system;
  // _model = model;
  // }
  //
  // public void apply(IProgressMonitor progressMonitor) {
  // EList<Transformation> transformations = _model.getTransformations();
  //
  // for (Transformation transformation : transformations) {
  // if (transformation instanceof RemoveFrom) {
  // apply((RemoveFrom) transformation);
  // } else if (transformation instanceof EmbedInto) {
  // EmbedInto embedInto = (EmbedInto) transformation;
  // apply(embedInto);
  // } else if (transformation instanceof CreateModule) {
  // CreateModule createModule = (CreateModule) transformation;
  // apply(createModule);
  // } else if (transformation instanceof ClassifyModules) {
  // ClassifyModules classifyModules = (ClassifyModules) transformation;
  // apply(classifyModules);
  // }
  // }
  //
  // _system.applyTransformations(progressMonitor);
  // }
  //
  // private void apply(CreateModule createModule) {
  //
  // Layer layer = createModule.getLayer();
  // IPath classification = null;
  // if (layer != null) {
  // String layer2 = layer.getLayer();
  // if (layer2 != null) {
  // classification = new Path(layer2);
  // }
  // }
  //
  // XResourceSetBasedTransformation transformation = new XResourceSetBasedTransformation(classification);
  // ModuleIdentifier module = createModule.getModule();
  // ResourceSetBasedModuleDefinition moduleDefinition = transformation.addModuleDefinition(module.getModulename(),
  // module.getVersion());
  // EList<From> froms = createModule.getFrom();
  // for (From from : froms) {
  // ResourceSet resourceSet = from.getResourceSet();
  // moduleDefinition.addResourceSet(asIModuleIdentifier(resourceSet.getModuleIdentifier()),
  // nullSafeAsArray(resourceSet.getIncludeResources()), nullSafeAsArray(resourceSet.getExcludeResources()));
  // }
  //
  // add(transformation);
  // }
  //
  // private class XResourceSetBasedTransformation extends ResourceSetBasedTransformation {
  // private final IPath _classification;
  //
  // /**
  // * @param classification
  // */
  // public XResourceSetBasedTransformation(IPath classification) {
  // super();
  // _classification = classification;
  // }
  //
  // /*
  // * (non-Javadoc)
  // *
  // * @see
  // * org.bundlemaker.core.transformations.resourceset.ResourceSetBasedTransformation#setClassification(org.bundlemaker
  // * .core.transformations.resourceset.ResourceSetBasedModuleDefinition,
  // * org.bundlemaker.core.modules.modifiable.IModifiableResourceModule)
  // */
  // @Override
  // protected void setClassification(ResourceSetBasedModuleDefinition moduleDefinition,
  // IModifiableResourceModule targetResourceModule) {
  // if (_classification != null) {
  // targetResourceModule.setClassification(_classification);
  // } else {
  // super.setClassification(moduleDefinition, targetResourceModule);
  // }
  // }
  //
  // }
  //
  // private void apply(EmbedInto embedInto) {
  // EmbedModuleTransformation embedModuleTransformation = new EmbedModuleTransformation();
  // embedModuleTransformation.setHostModuleIdentifier(asIModuleIdentifier(embedInto.getHostModule()));
  //
  // List<IModuleIdentifier> embeddedModulesIdentifiers = embedModuleTransformation.getEmbeddedModulesIdentifiers();
  // EList<ModuleIdentifier> modules = embedInto.getModules();
  // for (ModuleIdentifier moduleIdentifier : modules) {
  // embeddedModulesIdentifiers.add(asIModuleIdentifier(moduleIdentifier));
  // }
  //
  // add(embedModuleTransformation);
  // }
  //
  // private void apply(RemoveFrom removeFrom) {
  //
  // RemoveResourcesTransformation removeTransformation = new RemoveResourcesTransformation();
  // ResourceSetWrapper wrapper = ResourceSetWrapper.create(removeFrom.getResourceSet());
  //
  // removeTransformation.addResourceSet(wrapper.getModuleIdentifier(), wrapper.getIncludes(), wrapper.getExcludes());
  // add(removeTransformation);
  // }
  //
  // private void apply(ClassifyModules classifyModules) {
  // String modulePattern = classifyModules.getModules();
  // String classificationPattern = classifyModules.getClassification();
  // String exclude = classifyModules.getExcludedModules();
  // ClassifyTransformation classifyTransformation = new ClassifyTransformation(modulePattern, exclude,
  // classificationPattern);
  // add(classifyTransformation);
  //
  // }
  //
  // private void add(ITransformation transformation) {
  // _system.getTransformations().add(transformation);
  // }

}
