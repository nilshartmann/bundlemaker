package org.bundlemaker.core.ui.editor.transformation;

import static org.bundlemaker.core.ui.editor.transformation.DslUtils.asIModuleIdentifier;
import static org.bundlemaker.core.ui.editor.transformation.DslUtils.nullSafeAsArray;

import java.util.List;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.transformation.RemoveResourcesTransformation;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedTransformation;
import org.bundlemaker.core.transformations.dsl.transformationDsl.CreateModule;
import org.bundlemaker.core.transformations.dsl.transformationDsl.EmbedInto;
import org.bundlemaker.core.transformations.dsl.transformationDsl.From;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.RemoveFrom;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet;
import org.bundlemaker.core.transformations.dsl.transformationDsl.Transformation;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;
import org.eclipse.emf.common.util.EList;

public class TransformationExecutor {

  private final IModularizedSystem  _system;

  private final TransformationModel _model;

  public TransformationExecutor(IModularizedSystem system, TransformationModel model) {
    _system = system;
    _model = model;
  }

  public void apply() {
    EList<Transformation> transformations = _model.getTransformations();

    for (Transformation transformation : transformations) {
      if (transformation instanceof RemoveFrom) {
        apply((RemoveFrom) transformation);
      } else if (transformation instanceof EmbedInto) {
        EmbedInto embedInto = (EmbedInto) transformation;
        apply(embedInto);
      } else if (transformation instanceof CreateModule) {
        CreateModule createModule = (CreateModule) transformation;
        apply(createModule);
      }
    }

    _system.applyTransformations();
  }

  private void apply(CreateModule createModule) {
    ResourceSetBasedTransformation transformation = new ResourceSetBasedTransformation();
    ModuleIdentifier module = createModule.getModule();
    ResourceSetBasedModuleDefinition moduleDefinition = transformation.addModuleDefinition(module.getModulename(),
        module.getVersion());
    EList<From> froms = createModule.getFrom();
    for (From from : froms) {
      ResourceSet resourceSet = from.getResourceSet();
      moduleDefinition.addResourceSet(asIModuleIdentifier(resourceSet.getModuleIdentifier()),
          nullSafeAsArray(resourceSet.getIncludeResources()), nullSafeAsArray(resourceSet.getExcludeResources()));
    }

    add(transformation);
  }

  private void apply(EmbedInto embedInto) {
    EmbedModuleTransformation embedModuleTransformation = new EmbedModuleTransformation();
    embedModuleTransformation.setHostModuleIdentifier(asIModuleIdentifier(embedInto.getHostModule()));

    List<IModuleIdentifier> embeddedModulesIdentifiers = embedModuleTransformation.getEmbeddedModulesIdentifiers();
    EList<ModuleIdentifier> modules = embedInto.getModules();
    for (ModuleIdentifier moduleIdentifier : modules) {
      embeddedModulesIdentifiers.add(asIModuleIdentifier(moduleIdentifier));
    }

    add(embedModuleTransformation);
  }

  private void apply(RemoveFrom removeFrom) {

    RemoveResourcesTransformation removeTransformation = new RemoveResourcesTransformation();
    ResourceSetWrapper wrapper = ResourceSetWrapper.create(removeFrom.getResourceSet());

    removeTransformation.addResourceSet(wrapper.getModuleIdentifier(), wrapper.getIncludes(), wrapper.getExcludes());
    add(removeTransformation);
  }

  private void add(ITransformation transformation) {
    _system.getTransformations().add(transformation);
  }

}
