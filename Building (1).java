/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 13523
 */
public class Building implements Comparable<Building> {
	int buildingId;
	int totalTime;
	int exTime;

	public Building(int buildingId, int exTime, int totalTime) {
		this.buildingId = buildingId;
		this.exTime = exTime;
		this.totalTime = totalTime;
		//System.out.println("Constructor called");
	}
	//JAVA GET methods
	public int getBuildingId() {
		return buildingId;
	}
	public int getExecutedTime() {
		return exTime;
	}
	public int getTotalTime() {
		return totalTime;
	}
	//JAVA SET methods
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public void setExecutedTime(int exTime) {
		this.exTime = exTime;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
        //comparsion 
	public int compareTo(Building j) {
		if(this.exTime - j.exTime == 0) {
			return this.buildingId - j.buildingId;
		}
		return this.exTime - j.exTime;
	}
	@Override
	public String toString() {
		return "Building [buildingId=" + buildingId + ", exTime=" + exTime
				+ ", totalTime=" + totalTime + "]";
	}
	

	
}