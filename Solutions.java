/**
 * This class contains solutions to 4 unique problems.
 *
 * @author (Or Pinto)
 * @version (06/10/2024)
 */

public class Ex13
{
    /*
     * Time Complexity: O(log n)
     * The search space is halved in every iteration, therefore making the time complexity O(log n).
     * 
     * Space Complexity: O(1)
     * There is no memory usage dependancy for this method.
     */
    public static int findSingle(int[] a)
    {
        int left = 0; // We are declaring a left pointer, initiailized at 0.
        int right = a.length - 1; // We are declaring a right pointer, that is initialized at the end of the array.

        while (left < right) // As long as the left pointer is to the left of the right pointer we want to keep iterating.
        {
            // We are dealing with even indexes because we know for sure that the next one to them must be a match in the sequeunce.
            
            int mid = (left + right) / 2; // We are taking the median index of the left right pointers.

            if (mid % 2 != 0) // If the middle index is odd, we want to make it even so we subtract 1.
                mid--;

            if (a[mid] != a[mid + 1]) // If the current cell (which we know for sure is an even index, so that it must be that the next element is the same assuming we are looking at a proper sequence) differs from the next cell, we want to move the right pointer towards the median.
                right = mid; // Moving the right pointer to the median.
            else
                left = mid + 2; // If the above condition isn't met, we advance the the left pointer 2 steps (2 steps because we want to end at an even index), not an odd one.
        }
        
        return left; // At the end the left pointer points at where the single index is.
    }
    
    
    
    
    
    
    
    /*
     * Time Complexity: O(n)
     * There are no nested loops, and the individual loops only run a maximum of n times, making the time complexity O(n).
     * 
     * Space Complexity: O(n)
     * We are storing a pair of arrays each with the length of n, making the space complexity O(n).
     */
    public static int waterVolume(int[] heights)
    {
        // Our ultimate goal is to look for every single cell what are the local maximums and picking the minimal one of both, then subtracting the height of our cell.
        
        int n = heights.length; // n is just the heights length, we use it multiple times so making a variable is more readable.
        int[] leftMax = new int[n]; // Defining the left maximums array (for every cell it's local maximums to the left).
        int[] rightMax = new int[n]; // Defining the right maximums array (for every cell it's local maximums to the right).

        leftMax[0] = heights[0]; // We initialize the left maximum to be the initial height, because if we don't the next loop will have an error because we look for i - 1 and for i = 0 it is -1, an invalid index.
        for (int i = 1; i < n; i++)
            leftMax[i] = Math.max(leftMax[i - 1], heights[i]); // If the adjacent cell is higher it means it is the local maximum.

        rightMax[n - 1] = heights[n - 1]; // We initialize the right maximum to be the last height in the array.
        for (int i = n - 2; i >= 0; i--) // We run from n - 2 because we search for i + 1, and in the first iteration it will be i - 1, which is the last index of the array.
            rightMax[i] = Math.max(rightMax[i + 1], heights[i]); // If the adjacent cell is higher it means it is the local maximum.

        int waterVolume = 0; // This sums up the total water volume stored by the heights container.
        for (int i = 0; i < n; i++)
            waterVolume += Math.min(leftMax[i], rightMax[i]) - heights[i]; // We compute the volume of water stored in the current cell by just subtracting the minimal height of the local maximums from the current cell height.

        return waterVolume; // Returning the total water volume.
    }
    
    
    
    
    
    
    // This is a characters array contianing all the lowercase English alphabet letters.
    public static char[] alphabet = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    
    
    public static String findPassword(Password p, int length, String s, int i)
    {
        if (i >= alphabet.length) // It means that the current pointing index on the alphabet is exceeding the length.
            return "-"; // In case we do not match a proper password, we return a default "-".
        if (p.isPassword(s))
            return s;
        if (length <= 0)
            return "-"; // In case we do not match a proper password, we return a default "-".
        String ret = findPassword(p, length, s, i + 1); // This is the case where we alternate the current character being set.
        s += alphabet[i]; // We are concatenating the current alphbaetical character to the string.
        String ret2 = findPassword(p, length - 1, s, 0); // This is where we add the NEXT character being added.
        
        if (!ret.equals("-")) // If it didn't return a "-", it means it's the valid password, therefore we return it.
            return ret;
        if (!ret2.equals("-")) // If it didn't return a "-", it means it's the valid password, therefore we return it.
            return ret2;
        return "-"; // In case we do not match a proper password, we return a default "-".
    }
    
    public static String findPassword(Password p, int length)
    {
        // This method overloads to an external method that receives both an empty string and an index (initially 0).
        return findPassword(p, length, "", 0);
    }
    
    
    
    
    
    
    /* 
    * This receives a general matrix with a cell in the matrix and returns true if it's a cell within it's boundaries and otherwise false.
    */
    public static boolean isValid(boolean[][] mat, int x, int y)
    {
        return (x >= 0 && x < mat.length && y >= 0 && y < mat[0].length);
    }
    
    /*
     * This method is intended to cleanse all connectable true regions based on an initial cell.
     */
    public static void removeRegion(boolean[][] mat, int x, int y)
    {
        if (isValid(mat, x, y)) // Making sure we are within the boundaries of the matrix to not cause a runtime error.
        {
            if (mat[x][y] == true) // If the current cell is true.
            {
                mat[x][y] = false; // As our current cell is true, and our goal is to 'paint' it false, then we must put it's value at false.
                removeRegion(mat, x + 1, y); // We are iterating to the adjacent right cell to repeat process.
                removeRegion(mat, x, y + 1); // We are iterating to the adjacent down cell to repeat process.
                removeRegion(mat, x, y - 1); // We are iterating to the adjacent up cell to repeat process.
                removeRegion(mat, x - 1, y); // We are iterating to the adjacent left cell to repeat process.
            }
        }
    }
    
    public static int cntTrueReg(boolean[][] mat, int x, int y)
    {
        if (!isValid(mat, x, y))
            return 0; // If this is currently an invalid cell, then we return 0 as this can not be a region.
        
        if (mat[x][y] == true) // If the current cell's value is true.
        {
            removeRegion(mat, x, y); // We want to 'cleanse' all the adjacent cells recursively, it makes it that all the cells that are connectable to the initial cell get cleared to false.
            return 1 + cntTrueReg(mat, x + 1, y) + cntTrueReg(mat, x, y + 1); // We return 1 plus the other counted regions because we have verified this area is indeed a true region.
        } else {
            return cntTrueReg(mat, x + 1, y) + cntTrueReg(mat, x, y + 1); // We return just what the pair of calls return because this is an invalid region, that could be counted as 0.
        }
    }
    
    public static int cntTrueReg(boolean[][] mat)
    {
        // We are overloading this with an external method that also receives a pair of indexes representing the location indexes of the cell, initially (0, 0).
        return cntTrueReg(mat, 0, 0);
    }
    
    
    public static void main(String[] args)
    {
        Password p = new Password(5);
        System.out.println(p.getPassword());
        System.out.println(Ex13.findPassword(p,5));
    }
}
