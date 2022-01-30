package simulator.model;

import java.util.List;


public class MostCrowdedStrategy implements LightSwitchingStrategy  {
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int index;			
		List<Vehicle> v1;
		List<Vehicle> v2;
		if(roads.isEmpty())
			return -1;
		else if(currGreen==-1) {
			index=0;
			v1 = qs.get(0);
			for(int i=0;i<qs.size();i++) {
				v2 = qs.get(i);
				if(v1.size()<v2.size()) {
					index=i;
					v1 = qs.get(index);
				}
			}
			return index;
		}
		else if((currTime-lastSwitchingTime)<timeSlot)
			return currGreen;
		else {
			index=currGreen;
			if(currGreen+1>=qs.size())
				v1 = qs.get(0);
			else
				v1=qs.get(currGreen+1 %roads.size());
			for(int i=currGreen+1;i<qs.size();i++) {
				v2 = qs.get(i);
				if(v1.size()<v2.size()) {
					index=i;
					v1 = qs.get(index);
				}
			}
			for(int i=0;i<currGreen;i++) {
				v2 = qs.get(i);
				if(v1.size()<v2.size()) {
					index=i;
					v1 = qs.get(index);
				}
			}
			return index;
		}
	}
}
