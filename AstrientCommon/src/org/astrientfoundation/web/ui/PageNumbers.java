/*******************************************************************************
 * Copyright (c) 2009 Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors:
 * 
 * Astrient Foundation Inc. 
 * www.astrientfoundation.org
 * rashid@astrientfoundation.org
 * Rashid Mayes 2009
 *******************************************************************************/
package org.astrientfoundation.web.ui;

public class PageNumbers
{
    private int max;
    private int[] numbers;
    
    public PageNumbers(int max, int current, int size, int deviation)
    {
        this.max = max;
        
        numbers = new int[Math.min(max,size)];
        if ( numbers.length == max )
        {
            for ( int i = 0; i < numbers.length; i++ )
            {
                numbers[i] = (i+1); 
            }
        }
        else
        {
            
            int rangeStart = Math.max(2,current - deviation);
            int rangeEnd = Math.min(max-1,current + deviation);
            int spread = rangeEnd - rangeStart + 1;
            
            int fillStart = Math.max(0,(numbers.length/2) - deviation);
            int fillEnd = Math.min(numbers.length,fillStart+spread-1);
           
            //System.out.println("a " + current + " " + rangeStart + " " +  rangeEnd + " " + spread + " " + fillStart + " " + fillEnd);  
            
            if ( rangeStart < fillStart )
            {
                fillStart = rangeStart - 1;
                fillEnd = Math.min(numbers.length,fillStart+spread-1);
            }
            
            int rightSlots = ((numbers.length-1) - fillEnd);
            //System.out.println( rightSlots + " " + (numbers.length-1) + " " + fillEnd);
            
            if ( rightSlots >= (max-rangeEnd) )
            {
                fillStart = numbers.length - spread; 
                fillEnd = Math.min(numbers.length,fillStart+spread-1);
            }
            
            //System.out.println("b " + current + " " + rangeStart + " " +  rangeEnd + " " + spread + " " + fillStart + " " + fillEnd);  
            
            for ( int n = rangeStart, i = 0; i < spread; i++ )
            {
                numbers[fillStart+i] = n+i;
            }
            
            
            for ( int pos = fillStart-1; pos >= 0; pos-- )
            {
                numbers[pos] = prevInt(pos+1,numbers[pos+1]);
            }
            
            for ( int pos = fillEnd+1; pos < (numbers.length); pos++ )
            {
                numbers[pos] = nextInt(pos,numbers[pos-1]);
            }
            /*
            //numbers[0] = 1;
            //numbers[numbers.length-1] = max;
            //int openSlots = numbers.length -2; 
            
            int around = Math.min(5,numbers.length);
            int start = Math.max(current - (around/2),1);
            
            int fillPoint =  (numbers.length/2) - (around/2) - 1;
            

            
            int lSlots = numbers.length - (fillPoint+around);
            
            System.out.println( start + " " +  current + " " + fillPoint + " " + lSlots);
            
            if ( fillPoint >= start )
            {
                fillPoint = start-1;
            }
            else if ( (start+around) >= (max-1) )
            {
                fillPoint = numbers.length - (((start+around)-(max-1)) - 1);
                
                System.out.println("b " + numbers.length + " " + start + " " + around + " " + max + " " + (numbers.length - (((start+around)-max) - 1)));
                
            }
            System.out.println("a " + start + " " +  current + " " + fillPoint);  
            
            
            int stop = Math.min(around,numbers.length);
            for ( int i = 0; i <= stop; i++ )
            {
                numbers[fillPoint+i] = start+i; 
            }
            
            for ( int i = fillPoint; i > 0; i-- )
            {
                numbers[i-1] = prevInt(i,numbers[i]);
            }
            
            for ( int i = fillPoint+around; i < (numbers.length-1); i++ )
            {
                numbers[i+1] = nextInt(i,numbers[i]);
            }  */         
        }
        
    }
    
    private int nextInt(int pos, int seed)
    {
        int open = numbers.length - pos;
        int remaining = max - seed;
        int step = (remaining / open) + seed;
        
        /*System.out.println("t = "+pos + " " + seed + " " + step);
        
        if ( step > 10 ) step += 5;
        
        step = step - (step % 10);*/
       
        
        return step;
    }
    
    
    private int prevInt(int pos, int seed)
    {
        int prev = seed - ((pos == 0 || seed <= 2 ) ? seed / (pos+1) : seed / pos);
 
        if ( false && prev > 10 )
        {
            prev += 5;
            prev = prev - (prev % 10);
        }
       
        //System.out.println("t = "+pos + " " + seed + " " + prev);
        return Math.max(1,prev);
    }
    
    public int[] numbers()
    {
        return numbers;
    }
    
    
    public static void main(String[] args)
    {
        // 1 2 3 4 50 60 70 80 90 100
        //1 2 3 4 5 25 70 80 90 100
        
        PageNumbers numbers = new PageNumbers(2,1,10,2);
        //System.out.println(numbers.nextInt(7, 8));
        for ( int i : numbers.numbers() )
        {
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();
        /*
        //numbers = new PageNumbers(100,1,10);

        System.out.println( (n = numbers.nextInt(6, 8)) );
        System.out.println( (n = numbers.nextInt(7, n)) );
        System.out.println( (n = numbers.nextInt(8, n)) );
        System.out.println( (n = numbers.prevInt(3, 4)) );
        System.out.println( (n = numbers.prevInt(2, n)) );

        for ( int j = 1; j <= 250; j++ )
        {
            numbers = new PageNumbers(250,j,20,2);
            System.out.print(j + "\t");
            for ( int i : numbers.numbers() )
            {
                System.out.print(i);
                System.out.print(' ');
            }
            System.out.println();
        }
        */
        System.exit(0);
    }
}
