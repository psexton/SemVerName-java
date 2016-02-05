/*
 * SemanticVersionComparisonTest.java, part of the semvername-java project
 * Created on Feb 2, 2016, 3:40:09 PM
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
public class SemanticVersionComparisonTest {
    

    /**
     * Valid, no prerelease
     */
    @Test
    public void equality() {
        SemanticVersion version1a = SemanticVersion.valueOf("1.0.0");
        SemanticVersion version1b = SemanticVersion.valueOf("1.0.0");
        SemanticVersion version2 = SemanticVersion.valueOf("2.0.0");
            
        assertTrue(version1a.equals(version1b));
        assertFalse(version1a.equals(version2));
    }
    
    @Test
    public void noEqualityWithNull() {
        SemanticVersion instance = SemanticVersion.valueOf("1.2.3");
        
        assertFalse(instance.equals(null));
    }
    
    @Test
    public void noEqualityWithDifferentClass() {
        SemanticVersion instance = SemanticVersion.valueOf("1.2.3");
        
        assertFalse(instance.equals("1.2.3"));
    }
    
    @Test
    public void compareSelf() {
        SemanticVersion versionA = SemanticVersion.valueOf("1.0.0");
        SemanticVersion versionB = SemanticVersion.valueOf("1.0.0");
        assertEquals(versionA.compareTo(versionB), 0);
    }
    
    @Test
    public void compareMajors() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.3.5");
        SemanticVersion version2 = SemanticVersion.valueOf("2.0.0");
        
        assertEquals(version1.compareTo(version2), -1); // 1 < 2
        assertEquals(version2.compareTo(version1), 1); // 2 > 1
    }
    
    @Test
    public void compareMinors() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.0.1");
        SemanticVersion version2 = SemanticVersion.valueOf("1.2.0");
        
        assertEquals(version2.compareTo(version1), 1);
    }
    
    @Test
    public void comparePatches() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.0.0");
        SemanticVersion version2 = SemanticVersion.valueOf("1.0.2");
        
        assertEquals(version2.compareTo(version1), 1); // 2 > 0
    }
    
    @Test
    public void comparePrereleases() {
        SemanticVersion version2 = SemanticVersion.valueOf("1.2.3");
        SemanticVersion version2p1 = SemanticVersion.valueOf("1.2.3-p1");
        
        assertEquals(version2.compareTo(version2p1), 1); // 2 > 2p1
        assertEquals(version2p1.compareTo(version2), -1); // 2 > 2p1
    }
    
    @Test
    public void nonLexicographicComparisons() {
        SemanticVersion version10 = SemanticVersion.valueOf("10.20.30");
        SemanticVersion version2 = SemanticVersion.valueOf("1.2.3");
        SemanticVersion version2p1 = SemanticVersion.valueOf("1.2.3-p1");
        
        assertEquals(version10.compareTo(version10), 0); // 10 == 10
        assertEquals(version2.compareTo(version10), -1); // 2 < 10
        assertEquals(version10.compareTo(version2), 1); // 10 > 2
        
        assertEquals(version2p1.compareTo(version2p1), 0); // 2p1 == 2p1
        assertEquals(version2p1.compareTo(version10), -1); // 2p1 < 10
        assertEquals(version10.compareTo(version2p1), 1); // 10 > 2p1
    }
    
    @Test
    public void sortArray() {
        SemanticVersion version1 = SemanticVersion.valueOf("5.3.1");
        SemanticVersion version2 = SemanticVersion.valueOf("2.3.5");
        SemanticVersion version3 = SemanticVersion.valueOf("1.9.10");
        SemanticVersion version4 = SemanticVersion.valueOf("3.10.5");
        SemanticVersion version2p1 = SemanticVersion.valueOf("2.3.5-beta1");
        SemanticVersion version2p2 = SemanticVersion.valueOf("2.3.5-beta2");
        
        SemanticVersion[] expecteds = new SemanticVersion[]{version3, version2p1, version2p2, version2, version4, version1};
        
        SemanticVersion[] actuals = new SemanticVersion[]{version1, version2, version3, version4, version2p2, version2p1};
        Arrays.sort(actuals);
        
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test
    public void compatiblyGreaterThanMajorZero() {
        SemanticVersion version04 = SemanticVersion.valueOf("0.4.0");
        SemanticVersion version041 = SemanticVersion.valueOf("0.4.1");
        
        assertFalse(version041.isCompatiblyGreaterThan(version04));
        assertFalse(version04.isCompatiblyGreaterThan(version041));
    }
    
    @Test
    public void compatiblyGreaterThanMajorN() {
        SemanticVersion version10 = SemanticVersion.valueOf("10.20.30");
        SemanticVersion version1 = SemanticVersion.valueOf("1.2.3");
        SemanticVersion version1p1 = SemanticVersion.valueOf("1.2.3-p1");
        
        assertTrue(version1.isCompatiblyGreaterThan(version1p1));
        assertFalse(version10.isCompatiblyGreaterThan(version1));
    }
    
    @Test
    public void compatiblyGreaterThanSameMajorN() {
        SemanticVersion version12 = SemanticVersion.valueOf("1.2.0");
        SemanticVersion version13 = SemanticVersion.valueOf("1.3.0");
        
        assertTrue(version13.isCompatiblyGreaterThan(version12));
        assertFalse(version12.isCompatiblyGreaterThan(version13));
    }
    
    @Test
    public void equalityIsCgt() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.2.3");
        assertTrue(version1.isCompatiblyGreaterThan(version1));
    }
}
