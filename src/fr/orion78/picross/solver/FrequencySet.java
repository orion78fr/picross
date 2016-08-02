package fr.orion78.picross.solver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FrequencySet<T> {
	private static class Accumulator{
		private int i = 1;
		public void iterate(){
			i++;
		}
	}
	private Map<T, Accumulator> hm = new HashMap<T, Accumulator>();
	
	public void add(T e){
		if(hm.containsKey(e)){
			hm.get(e).iterate();
		} else {
			hm.put(e, new Accumulator());
		}
	}
	
	public T getBest(){
		if(hm.size() == 0){
			return null;
		}
		Iterator<Entry<T, Accumulator>> it = hm.entrySet().iterator();
		
		Entry<T, Accumulator> e = it.next();
		T best = e.getKey();
		int max = e.getValue().i;
		
		while(it.hasNext()){
			e = it.next();
			if(e.getValue().i > max){
				best = e.getKey();
				max = e.getValue().i;
			}
		}
		
		hm.remove(best);
		
		return best;
	}

	public boolean isEmpty() {
		return hm.isEmpty();
	}

}
