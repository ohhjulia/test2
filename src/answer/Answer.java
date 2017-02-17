package answer;

public class Answer {
	
	
    public static int maxProduct3(int arr[]){
		
		int product2,product3;
		int numOfNegative=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]<0)
				numOfNegative++;
		}
		//System.out.println(numOfNegative);
		
		for(int i=0;i<3;i++)
			for(int j=1;j<arr.length-i;j++)
				if(arr[j]<arr[j-1]){
					int temp=arr[j];
					    arr[j]=arr[j-1];
					    arr[j-1]=temp;	
				}
	/*	for(int n:arr){
			System.out.print(n+",");
		}
		System.out.println();*/
		
		product2=arr[arr.length-1]*arr[arr.length-2];
		
		if (numOfNegative>=2){
			for(int i=0;i<3;i++)
				for(int j=arr.length-1;j>i;j--)
					if(arr[j]<arr[j-1]){
						int temp=arr[j];
						    arr[j]=arr[j-1];
						    arr[j-1]=temp;	
					}
			if(numOfNegative==arr.length){
				return arr[arr.length-3]*product2;
			}
				
			product3=Math.max(product2*arr[arr.length-3], arr[0]*arr[1]*arr[arr.length-1]);
			return product3;
		}
			
		    
			product3=product2*arr[arr.length-3];
		
		
		return product3;
	} 
	
	public static Integer maxProduct(String str){
		//str=3,8,0,1,2
		if(str==null)return null;
		String array[]=str.split(",");
		if(array.length<3)
			return null;

		int arr[]=new int[array.length];
		for(int i=0;i<array.length;i++)
			arr[i]=Integer.parseInt(array[i]);
		if(array.length==3){
			return arr[0]*arr[1]*arr[2];
		}
		return maxProduct3(arr);
	
	}
	
}
