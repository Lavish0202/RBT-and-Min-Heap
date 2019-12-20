/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author 13523
 */

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class risingCity  {
    //Objects of classes of red black and min heap 
        public static RedBlackTreeimp rbt = new RedBlackTreeimp();
	public static MinHeapImpl mh = new MinHeapImpl();
        
	public final static String INSERT = "Insert";
	public final static String PRINT = "PrintBuilding";

	public static int[] arrs = new int[20000];
	public static int arr_i = 0;
	public static Building current_b = null;
        //getting the count of the time elapsed for current building
	static int cb_count = 0;
	
	

	
//Main function
	public static void main(String[] args) throws Exception {
                // wrtiting output to a seprate file
		PrintStream ps = new PrintStream(new File("output.txt"));
		System.setOut(ps);
		
		FileReader ff = null;
		try {
                    //reading input from the input file
                    File file = new File(args[0]);
			ff = new FileReader(file);
			BufferedReader br = new BufferedReader(ff);
                    try ( 
                            PrintWriter pw = new PrintWriter("output_file.txt")) {
                        
                        pw.print("");
                    }
			construction(br);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ff.close();
		}
	}


	//construction_function: for initial setting the input to trees
	private static void construction(BufferedReader bfr) throws Exception {
		String sline = null;
                //checking line by line the string
		ArrayList<String> arr_l = new ArrayList<String>();
		while((sline = bfr.readLine()) != null) {
			arr_l.add(sline);
			}
                //increases the global time and execution on conditions like completion of the building,completion of time etc
		for (int i = 0;; i++) {
			if(mh.isEmpty() && rbt.root == rbt.nil && current_b == null && arr_l.isEmpty()) {
				break;
			}
			
			if(arr_l.size() > 0 && get_time(arr_l.get(0)) == i) {
				computeInput(arr_l.remove(0));
			}
			
			if(current_b==null) {
				current_b=(Building) mh.extractMin();
				cb_count = 0;
			}
			
			if(current_b != null) {
				current_b.setExecutedTime(current_b.getExecutedTime()+1);
				cb_count++;
			}
			

			if(current_b != null && current_b.getExecutedTime()==current_b.getTotalTime())
			{	System.out.println("(" + current_b.getBuildingId() + ","
					+ (i+1)+")");
				rbt.removeBuilding(current_b.getBuildingId());
				current_b=null;
				cb_count=0;				
			}
			else if(cb_count==5)
			{
				mh.insert(current_b);
				current_b=null;
				cb_count = 0;				
			}

		}}
                //Gets the time in the input using split before :
		private static int get_time(String line) throws Exception {
		String[] str;
		int flag = -1;
		line = line.trim();
		if (line.contains(":")) {
			str = line.split(":");
			flag = Integer.parseInt(str[0]);
		} else {
			if (line.length() > 1)
				throw new Exception("Invalid Input:" + line);
		}
		return flag;
	}
                //Process the input after processing every line
		private static void computeInput(String line) throws Exception {
		String[] str;
		line = line.trim();
		str = line.split(":");
		compute_line(str[1]);

	}
                // Processes every line
	private static void compute_line(String line) throws Exception {
		// System.out.println(line);
		String inst_set = null;
		String[] str;
		str = line.split("\\(");
		inst_set = str[0];
		execute_tree(inst_set, str[1]);
	}

                
	private static void execute_tree(String i_set, String str) throws Exception {
		int building_iD = 0;
		int sch_time = 0;
		String[] s;
		/*
		creating or overwriting the input file
		*/
		str = str.trim();
		i_set = i_set.trim();
		if (str.charAt(str.length() - 1) == ')') {
			str = (String) str.substring(0, str.length() - 1);
		} else {
			throw new Exception("It give some wrong solution :(");
		}

		s = str.split("\\,");
            //comparsion Starts --> with instruction processed
            switch (i_set) {
                case INSERT:
                    building_iD = Integer.parseInt(s[0]);
                    sch_time = Integer.parseInt(s[1]);
                    //Create Building start with 5 and 10 for first time
                    Building j = create_building(building_iD, 0, sch_time);
                    //insert_to_construction(j);
                    break;
            //end of comparison
                case PRINT:
                    int get_rbid = -1;
                    //variable to flag the print of running buliding 
                    boolean isPrintrb = false;
                    if (current_b != null) {
                        get_rbid = current_b.getBuildingId();
                        isPrintrb = true;
                    }
                    if (s.length > 1) {
                        //variable to flag the building if found by id
                        boolean isFound = false;
                        int sid = Integer.parseInt(s[0]);
                        int eid = Integer.parseInt(s[1]);
                        //List of the building stored in the array list
                        List<Building> res = new ArrayList<>();
                        rbt.findBuildingsBetweenRange(rbt.root, sid, eid, res);
                        for (int i = 0; i < res.size(); i++) {
                            Building sb = res.get(i);
                            if (isFound) {
                                System.out.print(",");
                            }
                            System.out.print("(" + sb.getBuildingId() + ","
                                    + sb.getExecutedTime() + ","
                                    + sb.getTotalTime() + ")");
                            isFound = true;
                        }
                        if (!isFound) {
                            System.out.print("(0,0,0)");
                        }
                    } else {
                        building_iD = Integer.parseInt(s[0]);
                        Building sb = search_in_RBT(building_iD);
                        if (sb != null
                                && sb.getExecutedTime() < sb.getTotalTime()) {
                            System.out.print("(" + sb.getBuildingId() + ","
                                    + sb.getExecutedTime() + ","
                                    + sb.getTotalTime() + ")");
                        } else {
                            System.out.print("(0,0,0)");
                        }
                        if (get_rbid == building_iD) {
                            isPrintrb = false;
                        }
                    }
                    System.out.println();
                    break;
                default:
                    throw new Exception("This is not right here :(");
            }
	}
	private static Building create_building(int building_iD, int executedTime, int totalTime) {
		Building building = new Building(building_iD, executedTime, totalTime);
//insertion in min heap
                mh.insert(building);
		rbt.insertBuilding(building);
//array for the building at index i
		arrs[arr_i] = building.getBuildingId();
		
		arr_i++;
//calling for the sorting action
		sort();
		
		return building;
	}
//sort function for array
                private static void sort() {
		int temp = 0;
		for (int i = 0; i < arr_i; i++) {
			for (int j = 1; j < (arr_i-1); j++) {
				if (arrs[j - 1] > arrs[j]) {
					temp = arrs[j - 1];
					arrs[j - 1] = arrs[j];
					arrs[j] = temp;
				}
			}
		}
	}
// main execution method
// String functions such as spit so as to process instruction
// search job in Red black tree
	private static Building search_in_RBT(int building_iD) {
		return rbt.findBuilding(building_iD);
	}
	
}//end of class








