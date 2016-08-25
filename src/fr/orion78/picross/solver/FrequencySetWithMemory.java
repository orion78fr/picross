package fr.orion78.picross.solver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FrequencySetWithMemory<T> {
	private static class Accumulator{
		private int i = 1;
		public void iterate(){
			i++;
		}
	}
	private Map<T, Accumulator> hm = new HashMap<T, Accumulator>();
	private Map<T, Accumulator> oldHm = new HashMap<T, Accumulator>();
	
	
	public void add(T e){
		if(hm.containsKey(e)){
			hm.get(e).iterate();
		} else {
			if(oldHm.containsKey(e)){
				hm.put(e, oldHm.get(e));
				oldHm.remove(e);
			} else {
				hm.put(e, new Accumulator());
			}
		}
	}
	
	public T getBest(){
		if(hm.size() == 0){
			return null;
		}
		Iterator<Entry<T, Accumulator>> it = hm.entrySet().iterator();
		
		Entry<T, Accumulator> e = it.next();
		T best = e.getKey();
		Accumulator max = e.getValue();
		
		while(it.hasNext()){
			e = it.next();
			if(e.getValue().i > max.i){
				best = e.getKey();
				max = e.getValue();
			}
		}
		
		hm.remove(best);
		
		oldHm.put(best, max);
		
		return best;
	}

	public boolean isEmpty() {
		return hm.isEmpty();
	}
}
