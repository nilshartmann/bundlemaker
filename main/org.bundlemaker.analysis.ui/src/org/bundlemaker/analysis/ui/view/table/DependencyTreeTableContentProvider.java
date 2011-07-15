package org.bundlemaker.analysis.ui.view.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * ContentProvider fuer den TreeViewer der Dependencies View zur Beschreibung der Struktur des Models
 * 
 * <p>
 * Dieser ContentProvider beschreib die Datenstruktur von Abhaengigkeiten die ueber das Interface IDependency realisiert
 * werden
 * 
 * @author Kai Lehmann
 * 
 */
public class DependencyTreeTableContentProvider implements ITreeContentProvider {

  private final Object[]                EMPTY_ARRAY         = new Object[0];

  // Comparator der Abhaengigkeiten nach der Gewichtung absteigend sortiert
  private final Comparator<IDependency> compareByWeight     = new Comparator<IDependency>() {
                                                              @Override
                                                              public int compare(IDependency o1, IDependency o2) {
                                                                return o2.getWeight() - o1.getWeight();
                                                              }
                                                            };

  private final Comparator<IDependency> compareByViolations = new Comparator<IDependency>() {
                                                              @Override
                                                              public int compare(IDependency o1, IDependency o2) {
                                                                Integer o1Violations = o1.getViolations() != null ? o1
                                                                    .getViolations().size() : 0;
                                                                Integer o2Violations = o2.getViolations() != null ? o2
                                                                    .getViolations().size() : 0;
                                                                // if( (o1Violations == 0) && (o2Violations == 0) ) {
                                                                // return 0;
                                                                // }
                                                                o1Violations = o1Violations * o1.getWeight();
                                                                o2Violations = o2Violations * o2.getWeight();
                                                                return o2.getViolationWeight()
                                                                    - o1.getViolationWeight();
                                                              }
                                                            };

  private final Comparator<IDependency> compareByFrom       = new Comparator<IDependency>() {
                                                              @Override
                                                              public int compare(IDependency o1, IDependency o2) {
                                                                return o2.getFrom().getName()
                                                                    .compareTo(o1.getFrom().getName());
                                                              }
                                                            };

  private final Comparator<IDependency> compareByTo         = new Comparator<IDependency>() {
                                                              @Override
                                                              public int compare(IDependency o1, IDependency o2) {
                                                                return o2.getTo().getName()
                                                                    .compareTo(o1.getTo().getName());
                                                              }
                                                            };

  private Comparator<IDependency>       currentComparator   = compareByWeight;

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // TODO Auto-generated method stub

  }

  public void sort(String columnName) {
    if (columnName.equals("From")) {
      currentComparator = compareByFrom;
    } else if (columnName.equals("To")) {
      currentComparator = compareByTo;
    } else if (columnName.equals("Weight")) {
      currentComparator = compareByWeight;
    } else if (columnName.equals("Violations")) {
      currentComparator = compareByViolations;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object[] getElements(Object inputElement) {
    if (inputElement instanceof List<?>) {

      List<IDependency> dependencies = (List<IDependency>) inputElement;
      Collections.sort(dependencies, currentComparator);

      return dependencies.toArray();
    }
    return getChildren(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof IDependency) {
      IDependency dependency = (IDependency) parentElement;

      List<IDependency> dependencies = new ArrayList<IDependency>();

      if (dependency.getDependencies() == null) {
        return EMPTY_ARRAY;
      } else {
        dependencies.addAll(dependency.getDependencies());
      }

      Collections.sort(dependencies, compareByWeight);
      return dependencies.toArray();
    }
    return EMPTY_ARRAY;
  }

  @Override
  public Object getParent(Object element) {

    if (element instanceof IDependency) {
      IDependency dependency = (IDependency) element;
      if (dependency.getFrom().getParent() == null) {
        return EMPTY_ARRAY;
      }

      return dependency.getFrom().getParent().getDependency(dependency.getTo());
    }
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof IDependency) {
      IDependency dependency = (IDependency) element;
      if (dependency.getDependencies() != null) {
        return dependency.getDependencies().size() > 0;
      }
      // !dependency.getFrom().getChildren().isEmpty();
    }
    return false;
  }

}
