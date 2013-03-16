package org.bundlemaker.core.ui.stage.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

public class StageSubMenu extends CompoundContributionItem {

  public final static String               STAGE_SUBMENU_ID = "org.bundlemaker.core.ui.stage.StageSubMenu";

  private final AddModeActionGroup         _addModeActionGroup;

  private final ManipulateStageActionGroup _manipulateStageActionGroup;

  public StageSubMenu() {
    super(STAGE_SUBMENU_ID);

    _addModeActionGroup = new AddModeActionGroup();
    _manipulateStageActionGroup = new ManipulateStageActionGroup();
  }

  @Override
  protected IContributionItem[] getContributionItems() {

    List<IContributionItem> contributionItems = new LinkedList<IContributionItem>();
    _addModeActionGroup.update();
    _addModeActionGroup.fill(contributionItems);

    contributionItems.add(new Separator());
    _manipulateStageActionGroup.fill(contributionItems);

    return contributionItems.toArray(new IContributionItem[0]);
  }

}
