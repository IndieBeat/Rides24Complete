package businessLogic;

import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import domain.Driver;
import domain.Ride;

public class DriverAdapter extends AbstractTableModel {
	protected Driver d;
	protected String[] columnNames = new String[] { "from", "to", "date", "places", "price" };

	public DriverAdapter(Driver d) {
		this.d = d;
	}

	public int getColumnCount() {
		return this.columnNames.length;
	}
	
	public String getColumnName(int i) {
		if (i >= 0 && i < columnNames.length) {
			return columnNames[i];
		}
		return "";
	}

	public int getRowCount() {
		return this.d.getCreatedRides().size();
	}

	public Object getValueAt(int row, int col) {
		Ride r = null;
		Iterator<Ride> it = this.d.getCreatedRides().iterator();
		for (int i = 0; i <= row && it.hasNext(); i++) {
			r = it.next();
		}

		if (r != null) {
			if (col == 0)
				return r.getFrom();
			else if (col == 1)
				return r.getTo();
			else if (col == 2)
				return r.getDate();
			else if (col == 3)
				return r.getnPlaces();
			else if (col == 4)
				return r.getPrice();
			else
				return null;
		}
		return null;
	}
}
