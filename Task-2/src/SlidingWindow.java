public class SlidingWindow {
    public static void main(String[] args) {
        // Total requests in each hour of a 24-hour day.
        int[] rpmByHour={1200,1350,1500,1800,2200,2600,3100,3500,3900,4200,4600,5000,5400,5700,6000,6200,5900,5500,5100,4700,4000,3200,2400,1700};
        int k=3, sum=0;
        for(int i=0;i<k;i++)sum+=rpmByHour[i];
        int max=sum,bestStart=0;
        for(int end=k;end<rpmByHour.length;end++){
            sum+=rpmByHour[end];
            sum-=rpmByHour[end-k];
            if(sum>max){max=sum;bestStart=end-k+1;}
        }
        System.out.printf("Busiest 3-hour window: %02d:00 to %02d:00%n",bestStart,bestStart+k);
        System.out.println("Total requests in window: "+max);
        System.out.printf("Average requests per minute: %.2f%n",max/(k*60.0));
    }
}
