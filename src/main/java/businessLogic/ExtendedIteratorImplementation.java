package businessLogic;

import java.util.List;
import java.util.Vector;

public class ExtendedIteratorImplementation implements ExtendedIterator{
	List<String> list;
	Integer index;
	
	public ExtendedIteratorImplementation(List<String> list) {
		this.index=0;
		this.list=list;
	}
	
	@Override
	public boolean hasNext() {
		return index < (list.size()-1);
	}

	@Override
	public String next() {
		index++;
		return list.get(index);
	}

	@Override
	public Object previous() {
		index--;
		return list.get(index);
	}

	@Override
	public boolean hasPrevious() {
		return index > 0;
	}

	@Override
	public void goFirst() {
		index=0;
	}

	@Override
	public void goLast() {
		index=list.size()-1;
	}

}
