package org.bundlemaker.core.internal.projectdescription;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectDescriptionTest {

  /**
   * <p>
   * OK
   * </p>
   */
  @Test
  public void testMoveUp() {

    BundleMakerProjectDescription description = new BundleMakerProjectDescription(null);

    IProjectContentProvider provider1 = new FileBasedContentProvider();
    IProjectContentProvider provider2 = new FileBasedContentProvider();
    List<IProjectContentProvider> selectedItems = new LinkedList<IProjectContentProvider>();
    selectedItems.add(provider1);
    selectedItems.add(provider2);

    // create list with selected items at position 3 and 5 (starting from 0)
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(provider1);
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(provider2);

    // assert at position 3
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(3), provider1);
    Assert.assertSame(description.getContentProviders().get(5), provider2);

    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(2), provider1);
    Assert.assertSame(description.getContentProviders().get(4), provider2);
    
    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(1), provider1);
    Assert.assertSame(description.getContentProviders().get(3), provider2);
    
    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(0), provider1);
    Assert.assertSame(description.getContentProviders().get(2), provider2);
    
    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(0), provider1);
    Assert.assertSame(description.getContentProviders().get(2), provider2);
  }
  
  /**
   * <p>
   * OK
   * </p>
   */
  @Test
  public void testMoveDown() {

    BundleMakerProjectDescription description = new BundleMakerProjectDescription(null);

    IProjectContentProvider provider1 = new FileBasedContentProvider();
    IProjectContentProvider provider2 = new FileBasedContentProvider();
    List<IProjectContentProvider> selectedItems = new LinkedList<IProjectContentProvider>();
    selectedItems.add(provider1);
    selectedItems.add(provider2);

    // create list with selected items at position 0 and 2 (starting from 0)
    description.addContentProvider(provider1);
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(provider2);
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());

    // assert
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(0), provider1);
    Assert.assertSame(description.getContentProviders().get(2), provider2);

    // move down the content provider
    description.moveDownContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(1), provider1);
    Assert.assertSame(description.getContentProviders().get(3), provider2);
    
    // move down the content provider
    description.moveDownContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(2), provider1);
    Assert.assertSame(description.getContentProviders().get(4), provider2);
    
    // move down the content provider
    description.moveDownContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(3), provider1);
    Assert.assertSame(description.getContentProviders().get(5), provider2);
    
    // move down the content provider
    description.moveDownContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(3), provider1);
    Assert.assertSame(description.getContentProviders().get(5), provider2);
  }
  
  @Test
  public void testMoveUpAndDown() {
    
    BundleMakerProjectDescription description = new BundleMakerProjectDescription(null);

    IProjectContentProvider provider1 = new FileBasedContentProvider();
    IProjectContentProvider provider2 = new FileBasedContentProvider();
    List<IProjectContentProvider> selectedItems = new LinkedList<IProjectContentProvider>();
    selectedItems.add(provider1);
    selectedItems.add(provider2);

    // create list with selected items at position 3 and 5 (starting from 0)
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(provider1);
    description.addContentProvider(new FileBasedContentProvider());
    description.addContentProvider(provider2);

    // assert at position 3
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(3), provider1);
    Assert.assertSame(description.getContentProviders().get(5), provider2);

    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(2), provider1);
    Assert.assertSame(description.getContentProviders().get(4), provider2);
    
    // move up the content provider
    description.moveUpContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(1), provider1);
    Assert.assertSame(description.getContentProviders().get(3), provider2);
    
    // move down the content provider
    description.moveDownContentProviders(selectedItems);
    Assert.assertEquals(6, description.getContentProviders().size());
    Assert.assertSame(description.getContentProviders().get(2), provider1);
    Assert.assertSame(description.getContentProviders().get(4), provider2);
  }
}
