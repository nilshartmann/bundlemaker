package org.bundlemaker.analysis.ui.view.table;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class BaseTable<T> extends Composite {
	private boolean compareOrder = true;
	private int columnCount = 0;
	private Table table;

	public BaseTable(Composite parent) {
		this(parent, SWT.FULL_SELECTION | SWT.CENTER | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	}

	public BaseTable(Composite parent, int style) {
		super(parent, SWT.CENTER);

		setLayout(new GridLayout(1, false));
		Font tableFont = new Font(getDisplay(), "ARIAL", 10, SWT.NONE);
		table = new Table(this, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(tableFont);
		table.setLayoutData(
			new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL)
		);
		addTableListener();
	}

	public Table getTable() {
		return table;
	}

	public void removeAll() {
		table.removeAll();
	}

	protected void selectRow(T rowData) {

	}

	private Double getDouble(String value) {
		if (value.length() == 0) {
			return -1.0;
		} else {
			int pos = value.indexOf(" ");
			if (pos > 0) {
				return Double.valueOf(value.substring(0, pos));
			} else {
				return Double.valueOf(value.replace(',', '.'));
			}
		}
	}

	private void addTableListener() {
		table.addListener(
			SWT.Selection,
			new Listener() {
				public void handleEvent(Event event) {
					if( (event.item != null) && (event.item.getData() != null) ) {
						selectRow((T) event.item.getData());
					}
				}
			}
		);
	}

	public TableItem addRow(T rowData, String... columValues) {
		TableItem item = new TableItem(table, SWT.NULL);
		item.setData(rowData);
		for (int i = 0; i < columValues.length; i++) {
			item.setText(i, columValues[i]);
		}
		return item;
	}

	public TableColumn addTableColumn(String columnName, int columnWidth, boolean isStringType) {

		TableColumn column = new TableColumn(getTable(), SWT.NULL);
		column.setText(columnName);
		column.setData("ColumnNr", columnCount++);
		column.setData("isStringType", isStringType);

		column.setWidth(columnWidth);

		column.addListener(
			SWT.Selection,
			new Listener() {
				public void handleEvent(Event e) {
					try {
						TableColumn tableColumn = (TableColumn) e.widget;
						Integer columnNr = (Integer) tableColumn.getData("ColumnNr");
						Boolean isStringType = (Boolean) tableColumn.getData("isStringType");

						TableItem[] items = getTable().getItems();
						Collator collator = Collator.getInstance(Locale.getDefault());

						for (int i = 1; i < items.length; i++) {
							String value1 = items[i].getText(columnNr);
							for (int j = 0; j < i; j++) {
								String value2 = items[j].getText(columnNr);
								int compareValue = 0;
								if (isStringType) {
									if (compareOrder) {
										compareValue = collator.compare(value1, value2);
									} else {
										compareValue = collator.compare(value2, value1);
									}
								} else {
									Double doubleValue1 = getDouble(value1);
									Double doubleValue2 = getDouble(value2);
									if (compareOrder) {
										compareValue = doubleValue1.compareTo(doubleValue2);
									} else {
										compareValue = doubleValue2.compareTo(doubleValue1);
									}
								}
								if (compareValue < 0) {
									Object data = items[i].getData();
									String[] values = new String[columnCount];
									for (int col = 0; col < columnCount; col++) {
										values[col] = items[i].getText(col);
									}
									items[i].dispose();
									TableItem item = new TableItem(getTable(), SWT.NONE, j);
									item.setText(values);
									item.setData(data);
									items = getTable().getItems();
									break;
								}
							}
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
					if (compareOrder) {
						compareOrder = false;
					} else {
						compareOrder = true;
					}
				}
			}
		);

		return column;
	}

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
