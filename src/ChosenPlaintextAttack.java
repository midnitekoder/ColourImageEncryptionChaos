import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ChosenPlaintextAttack {
	public static void logisticMap(int m,int M, double a, double x0, ArrayList<Double> chaosArray)
	{
		int i;
		double x1,temp;
		for(i=0;i<m;i++)
		{
			x1=a*x0*(1-x0);
			temp=x0;
			x0=x1;
			x1=temp;
		}
		for(i=0;i<M;i++)
		{
			x1=a*x0*(1-x0);
			temp=x0;
			x0=x1;
			x1=temp;
			chaosArray.add(i,x0);
			
		}
			
	}
	public static void main(String[] args){
	
	ArrayList< Double> chaosArray= new ArrayList< Double>();
	ArrayList<Double> sortedChaosArray=new ArrayList<Double>();
	ArrayList<Integer> transformationMatrix=new ArrayList<Integer>();
	double x0=0.1234567898765,y0=0.56789123456789;
	double a=4.0,a1=3.99;
	int m=200,i,j,k,n=200;
	int M=20,height,width,rows,columns,N;
	
		
	BufferedImage img=null;
	try {
		img = ImageIO.read(new File("/home/ankit/workspaceLuna/ImageEncryptionChaos/encryptedImage.jpg"));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
			
			rows=img.getWidth();
			columns=img.getHeight();
			M=columns;
			N=rows;
			System.out.println(rows+" "+columns);
			int[] colors = new int[img.getWidth() * img.getHeight()];
		    img.getRGB(0, 0, img.getWidth(), img.getHeight(), colors, 0, img.getWidth());

		    int[] red = new int[colors.length];
		    int[] green = new int[colors.length];
		    int[] blue = new int[colors.length];

		    for ( i = 0; i < colors.length; i++) {
		      Color color = new Color(colors[i]);
		      red[i] = color.getRed();
		      green[i] = color.getGreen();
		      blue[i] = color.getBlue();
		    }
		    ArrayList< Double> chaosArray1= new ArrayList< Double>();
			double[][] y1=new double[M][3*N];
			double[][] y2=new double[M][3*N];
			int[][] TN=new int[M][3*N];
			logisticMap(n, 3*M*N, a1, y0, chaosArray1);
			System.out.println("Done till here");
			double[] yn=new double[3*M*N];
			k=0;
			for(i=0;i<M;i++)
				for(j=0;j<3*N;j++)
				{
					y1[i][j]=chaosArray1.get(k);
					yn[k]=chaosArray1.get(k);
					k++;
				}
			//pick each subarray of size 3N and sort it and later place it in TN2
			for(k=0;k<3*M*N;k=k+3*N)
				Collections.sort(chaosArray1.subList(k, (k+3*N-1)));
			k=0;
			
			for(i=0;i<M;i++)
				for(j=0;j<3*N;j++)
				{
					y2[i][j]=chaosArray1.get(k);
					
					k++;
				}
		    double temp1=10, temp2=14,temp;
			int[] z1n=new int[3*M*N];
			int[] z2n=new int[3*M*N];
			for(i=0;i<3*M*N;i++)
			{
				temp=Math.pow(temp1, temp2);
				z1n[i]=(int) ((yn[i]*temp)%3);
				z2n[i]=(int)((yn[i]*temp)%256);
			}
			
			
			System.out.println(M+" "+N);
			  int[] Rp=new int[M*N];
			    int[] Gp=new int[M*N];
			    int[] Bp=new int[M*N];
			int[][] R2=new int[M][N];
			int[][] G2=new int[M][N];
			int[][] B2=new int[M][N];
			
		    
		  
		    for(i=0;i<M*N;i++)
		    {
		    	Rp[i]=red[i];
		    	Gp[i]=green[i];
		    	Bp[i]=blue[i];
		    }
		    
			int Cprev=0,Cnow;
			int Pprev=0,Pnow;
			int r=0,g=0,b=0;
			for(i=0;i<3*M*N;i++)
			{
				switch(z1n[i])
				{
				case 0:
					if(r<M*N){
					Cnow=Rp[r];
					Pnow=(Cnow-z2n[i]-Cprev-Pprev)%256;
					if(Pnow<0)
						Pnow=Pnow+256;
					Pprev=Pnow;
					Rp[r]=Pnow;
					Cprev=Cnow;
					r++;
					break;
					}
				case 1:
					if(g<M*N){
						Cnow=Gp[g];
						Pnow=(Cnow-z2n[i]-Cprev-Pprev)%256;
						if(Pnow<0)
							Pnow=Pnow+256;
						Pprev=Pnow;
						Gp[g]=Pnow;
						Cprev=Cnow;
						g++;
						break;
						}
				case 2:
					if(b<M*N){
						Cnow=Bp[b];
						Pnow=(Cnow-z2n[i]-Cprev-Pprev)%256;
						if(Pnow<0)
							Pnow=Pnow+256;
						Pprev=Pnow;
						Bp[b]=Pnow;
						Cprev=Cnow;
						b++;
						break;
						}
				}
			}
			
			k=0;
			for(i=0;i<M;i++)
				for(j=0;j<N;j++)
				{
					R2[i][j]=Rp[k];
					G2[i][j]=Gp[k];
					B2[i][j]=Bp[k];
					k++;
				}
			int[][] P21=new int[M][3*N];
			int[][] P22=new int[M][3*N];
			for(i=0;i<M;i++)
				for(j=0;j<N;j++){
					P22[i][j]=R2[i][j];
					P22[i][j+N]=G2[i][j];
					P22[i][j+2*N]=B2[i][j];
				}
			for(i=0;i<M;i++){
				for(j=0;j<3*N;j++)
					for(k=0;k<3*N;k++)
						if(y2[i][j]==y1[i][k])
						{
							TN[i][j]=k;
							break;
						}
			}
			System.out.println("TN created.");	
			for(i=0;i<M;i++)
				for(j=0;j<3*N;j++)
					P21[i][TN[i][j]]=P22[i][j];
			int flag=0;
			double l;
			for( l=0.00000000000001;l<=4.0;l=l+0.00000000000001)
			{
				System.out.print("Checking for l="+l);
			logisticMap(m,(M*3),l,x0,chaosArray);
		    sortedChaosArray= new ArrayList<Double>(chaosArray);
			Collections.sort(sortedChaosArray);
		    //System.out.println("next Transformation Matrix");
			for(i=0;i<3*M;i++)
			{
				for(j=0;j<3*M;j++)
				{
					//System.out.println(chaosArray.get(j)+" "+sortedChaosArray.get(i));
					if(chaosArray.get(j)==sortedChaosArray.get(i))
					{
						transformationMatrix.add(i,j);
						//System.out.println(j);
						break;
					}
				}
			}
			
			  int[][] P1=new int[3*M][N];
			    int[][] P12=new int[3*M][N];
			for(i=0;i<M;i++)
				for(j=0;j<N;j++)
				{
					P12[i][j]=P21[i][j];
					P12[i+M][j]=P21[i][j+N];
					P12[i+2*M][j]=P21[i][j+2*N];
				}
			
			for(i=0;i<3*M;i++)
				for(j=0;j<N;j++)
					P1[transformationMatrix.get(i)][j]=P12[i][j];
		
			int[][] pRed=new int[M][N];
			int[][] pGreen=new int[M][N];
			int[][] pBlue=new int[M][N];
		    
		    for(i=0;i<M;i++)
		    	for(j=0;j<N;j++)
		    	{
		    		pRed[i][j]=P1[i][j];
		    	
		    	}
		    
		    for(i=M;i<2*M;i++)
		    	for(j=0;j<N;j++)
		    	{
		    		pGreen[i-M][j]=P1[i][j];
		    	
		    	}
		    
		    for(i=2*M;i<3*M;i++)
		    	for(j=0;j<N;j++)
		    	{
		    		pBlue[i-2*M][j]=P1[i][j];
		    		
		    	}
		    /*for(i=0;i<3*M;i++)
		    {
		    	for(j=0;j<N;j++)
		    		System.out.print(P1[i][j]+" ");
		    	System.out.println();
		    }*/
		   /* BufferedImage image = new BufferedImage(N,M, BufferedImage.TYPE_INT_RGB);
	        for ( i = 0; i < M; i++) {
	            for ( j = 0; j < N; j++) {
	                Color c = new Color(pRed[i][j], pGreen[i][j], pBlue[i][j]);
	            	//Color c=new Color(red[i*N+j],green[i*N+j],blue[i*N+j]);
	                image.setRGB(j,i, c.getRGB());
	                
	            }
	        }*/
		    try {
				BufferedImage img2=ImageIO.read(new File("/home/ankit/workspaceLuna/ImageEncryptionChaos/decryptedImage.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    int[] colors2 = new int[img.getWidth() * img.getHeight()];
		    img.getRGB(0, 0, img.getWidth(), img.getHeight(), colors2, 0, img.getWidth());

		    int[] red2 = new int[colors2.length];
		    int[] green2 = new int[colors2.length];
		    int[] blue2 = new int[colors2.length];

		    for ( i = 0; i < colors2.length; i++) {
		      Color color = new Color(colors2[i]);
		      red2[i] = color.getRed();
		      green2[i] = color.getGreen();
		      blue2[i] = color.getBlue();
		    }
		    flag=0;
	        for(i=0;i<M;i++)
	        {
	        	for(j=0;j<N;j++)
	        	{
	        		if((pRed[i][j]!=red2[i*N+j])||(pGreen[i][j]!=green2[i*N+j])||(pBlue[i][j]!=blue[i*N+j]))
	        		{
	        			flag=1;
	        			break;
	        		}
	        			
	        			
	        	}
	        	if(flag==1)
	        		break;
	        	
	        }
	        if(flag==0)
	        	break;
	        else
        		System.out.println(": Unsuccessful");
			}
			
	        /*try {
				ImageIO.write(image, "jpg", new File("decryptedImage.jpg"));
				ImageIcon icon=new ImageIcon(image);
				lblImage.setBounds(100,100, image.getWidth(),image.getHeight());
				
				lblImage.setIcon(icon);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		 		if(flag==0)
		 			System.out.println("Found the value of a: "+l);
	}

}
