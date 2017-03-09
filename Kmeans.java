
package kmeans;

import java.io.*;
import java.util.Scanner;
import java.lang.Math;

public class Kmeans {
	// Exceptions Handling //
    public static void main(String[] args) throws FileNotFoundException, IOException, ArithmeticException{
	
        int[][] A, C;	
	int counter = 0, k, n, o, p, NearCentroid;
	double distance, smallestDistance;
        Node[] z;
	Node newNode;
	
	Scanner s;
								
	s = new Scanner(new FileReader("input8.txt"));
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n Please enter total number of Clusters: ");
        k=Integer.parseInt(bf.readLine());
							
	A = new int[1000][1000];     // Screen View Sizes
	for (int a1=0; a1<1000; a1++)
	{
            for (int b1=0; b1< 1000; b1++)
		{
                A[a1][b1]=0;
		}
	}
       
				
	//  Partitioning randomly the data set into k-groups, Evenly
	z = new Node[k]; //Array of Node heads for k partitions
        for (int i=0; i<k; i++)
        {
            z[i]=new Node();
        }
        C = new int[k][2];//Array for centroids in k number
        n = 1200/k;
        for (int i=k-1; i>0; --i)
        {
            C[i][0]=(i)*n;//o for centroid i
            C[i][1]=(i)*n;//p for centroid i
        }
        
        
        while(s.hasNextInt())
        {
            ++counter;
            o=s.nextInt();
            p=s.nextInt();
           
            
            smallestDistance=1200*Math.sqrt(2);// smallest distance
            NearCentroid=k-1;
            for (int i=0; i<k; ++i)
            {
                distance= distanceOf(o,p,C[i][0],C[i][1]);
                if (distance<=smallestDistance)
                {
                    smallestDistance=distance;
                    NearCentroid=i;
                }
            }
            
            newNode = new Node();
            newNode.o=o;
            newNode.p=p;
            newNode.label=NearCentroid;
            Node.add(z[NearCentroid],newNode);
        }
        
        boolean Labelsvariation = true;
        int incrementCounter=0;
        
        while(Labelsvariation)
        {
            Labelsvariation=false;
            System.out.println("\n The Division of Clusters- Cluster  "+(incrementCounter++));
            System.out.println();
           // System.out.println();

            // Computation of centroid (o, p)
	  
            int sumo,sump,count; // sum variables		
            double meanO , meanP; // mean varaiables	
            Node pointer;
                
                
            for (int i=0; i<k; ++i)
            {
                pointer = z[i];
                
                sumo=0;		
                sump=0;		
               	count=0;
                meanO = 0;
                meanP = 0;
                
                while(pointer.next!=null)
                {
                    sumo+=pointer.next.o;
                    sump+=pointer.next.p;
                    count++;
                    pointer=pointer.next;
                    
                }
                if(count!=0)
                {
                    meanO=sumo/count;
                    meanP=sump/count;
                }
                else
                {
                    meanO=500;     // Centroid Default Value setting
                    meanP=200;
                }
                
                
                C[i][0]= (int)meanO;
                C[i][1]= (int)meanP;
                System.out.println(" The Centroid obtained at"+(i+1)+" coordinates are :["+C[i][0]+","+C[i][1]+"]");
                
            }
        
					
	// In the entire data set, we compute k distances 
	//from n to the centroid of k groups
            for (int i=0; i<k; ++i)
            {
                pointer = z[i];// Centroids of k groups
                
                while(pointer.next!=null)
                {
		
                    smallestDistance=distanceOf(pointer.next.o,pointer.next.p,C[i][0],C[i][1]);
                    NearCentroid=i;
                    
                    for (int j=0; j<k; ++j)
                    {
                        distance= distanceOf(pointer.next.o,pointer.next.p,C[j][0],C[j][1]);
                        if (distance<smallestDistance)
                        {
                            smallestDistance=distance;
                            NearCentroid=j;
                        }
                    }
							
		//  Swapping each point to the group that has the shortest of the centroid distance
                    
                    if(NearCentroid!=i)
                    {
                        Node temp = pointer.next;
                        pointer.next=pointer.next.next;
                        temp.next=null;
                        temp.label=NearCentroid;
                        Node.add(z[NearCentroid], temp);
                        Labelsvariation=true;
                    }
                    else 
                    {
                        pointer=pointer.next;
                    }
            }
            
           // System.out.println();
        
        }
            
// Repeat untill no point change in label. 
        Node nochange;
        File fin = new File("Output8.txt");
        FileOutputStream fout = new FileOutputStream(fin);
        PrintWriter pwt = new PrintWriter(fout);
        
        for (int i=0; i<k; ++i)
        {
            nochange=z[i];
            while(nochange.next!=null)
            {   
                //Writing to output file
                pwt.write(nochange.next.o+"\t"+nochange.next.p+"\t"+(nochange.next.label+1)+"\n");
                System.out.print("\n"+nochange.next.o+"\t"+nochange.next.p+"\t"+(nochange.next.label+1));
                System.out.println();
                nochange=nochange.next;
            }
        }
        pwt.flush();
        fout.close();
        pwt.close();
    }
    }
    public static double distanceOf(int xi, int yi, int xj, int yj )
    {
        double dist;
        dist=Math.sqrt((xi-xj)*(xi-xj)+(yi-yj)*(yi-yj));   // Euclidean Distance
        return dist;
    }
    public static class Node 
    {
        int x;
        int y;
        int closest; //closest centroid
        Node next;
        private int o;
        private int p;
        private int label;
        
        Node()
        {
            x=0;	
            y=0;
            closest=-1;
            next=null;
        }
		
		
        static void add(Node x1,Node y1)
        {
            if (x1.next==null) 	
                x1.next=y1;
            else 
                add(x1.next,y1);
        }		
    }

}