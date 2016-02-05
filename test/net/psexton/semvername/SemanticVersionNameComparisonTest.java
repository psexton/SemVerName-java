/*
 * SemanticVersionNameComparisonTest.java, part of the semvername-java project
 * Created on Feb 5, 2016, 1:35:42 PM
 *
 * semvername-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * semvername-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with semvername-java. If not, see <http://www.gnu.org/licenses/>.
 */
package net.psexton.semvername;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PSexton
 */
public class SemanticVersionNameComparisonTest {
    
    /**
     * Valid, no prerelease
     */
    @Test
    public void equality() {
        SemanticVersionName versionName1a = SemanticVersionName.valueOf("foo-1.0.0");
        SemanticVersionName versionName1b = SemanticVersionName.valueOf("foo-1.0.0");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("foo-2.0.0");
            
        assertTrue(versionName1a.equals(versionName1b));
        assertFalse(versionName1a.equals(versionName2));
    }
    
    @Test
    public void noEqualityWithNull() {
        SemanticVersionName instance = SemanticVersionName.valueOf("apple-1.2.3");
        
        assertFalse(instance.equals(null));
    }
    
    @Test
    public void noEqualityWithDifferentClass() {
        SemanticVersionName instance = SemanticVersionName.valueOf("apple-1.2.3");
        
        assertFalse(instance.equals("apple-1.2.3"));
    }
    
    @Test
    public void noEqualityWithDifferentNames() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-1.2.3");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("banana-1.2.3");
        
        assertFalse(versionName1.equals(versionName2));
        assertFalse(versionName2.equals(versionName1));
    }
    
    @Test
    public void noEqualityWithDifferentSemvers() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-1.2.3");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("apple-1.2.4");
        
        assertFalse(versionName1.equals(versionName2));
        assertFalse(versionName2.equals(versionName1));
    }
    
    @Test
    public void compareSelf() {
        SemanticVersionName versionName1a = SemanticVersionName.valueOf("foo-1.0.0");
        SemanticVersionName versionName1b = SemanticVersionName.valueOf("foo-1.0.0");
        
        assertEquals(versionName1a.compareTo(versionName1b), 0);
        assertTrue(versionName1a.cgt(versionName1b));
    }
    
    @Test
    public void compareNames() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-4.5.6");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("banana-1.2.3");
        
        assertEquals(versionName1.compareTo(versionName2), -1);
        assertEquals(versionName2.compareTo(versionName1), 1);
    }
    
    @Test
    public void compareSemvers() {
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("banana-1.2.3");
        SemanticVersionName versionName3 = SemanticVersionName.valueOf("banana-4.5.6");
        
        assertEquals(versionName2.compareTo(versionName3), -1);
        assertEquals(versionName3.compareTo(versionName2), 1);
    }
    
    @Test
    public void sortArray() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-4.5.6");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("banana-1.2.3");
        SemanticVersionName versionName3 = SemanticVersionName.valueOf("banana-7.8.9");
        SemanticVersionName versionName4 = SemanticVersionName.valueOf("apple_jack-0.1.2-pre1");
        
        SemanticVersionName[] expecteds = new SemanticVersionName[]{versionName1, versionName4, versionName2, versionName3};
        
        SemanticVersionName[] actuals = new SemanticVersionName[]{versionName4, versionName1, versionName3, versionName2};
        Arrays.sort(actuals);
        
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test
    public void noCgtWithDifferentNames() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-4.5.6");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("banana-1.2.3");
        
        assertFalse(versionName1.isCompatiblyGreaterThan(versionName2));
        assertFalse(versionName2.isCompatiblyGreaterThan(versionName1));
    }
    
    @Test
    public void noCgtWithNonCgtSemver() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-4.5.6");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("apple-1.2.3");
        
        assertFalse(versionName1.isCompatiblyGreaterThan(versionName2));
        assertFalse(versionName2.isCompatiblyGreaterThan(versionName1));
    }
    
    @Test
    public void cgtWithSameNameAndCgtSemver() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("apple-1.2.3");
        SemanticVersionName versionName2 = SemanticVersionName.valueOf("apple-1.3.2");
        
        assertTrue(versionName2.isCompatiblyGreaterThan(versionName1));
    }
    
    @Test
    public void equalityIsCgt() {
        SemanticVersionName versionName1 = SemanticVersionName.valueOf("abc-1.2.3");
        assertTrue(versionName1.isCompatiblyGreaterThan(versionName1));
    }
}
