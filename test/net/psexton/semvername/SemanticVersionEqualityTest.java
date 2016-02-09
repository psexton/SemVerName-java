/*
 * SemanticVersionEqualityTest.java, part of the semvername-java project
 * Created on Feb 9, 2016, 3:40:55 PM
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests equals method of SemanticVersion class.
 * @author PSexton
 */
public class SemanticVersionEqualityTest {
    
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
    
    /**
     * Equal to self
     */
    @Test
    public void equalToSelf() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        
        assertTrue(lhs.equals(lhs));
    }
    
    /**
     * Equal to same
     */
    @Test
    public void equalToSame() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
        
        assertTrue(lhs.equals(rhs));
    }
    
    /**
     * Majors differ
     */
    @Test
    public void majorsDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("2.0.0");
            
        assertFalse(lhs.equals(rhs));
    }
    
    /**
     * Minors differ
     */
    @Test
    public void minorsDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.1.0");
            
        assertFalse(lhs.equals(rhs));
    }
    
    /**
     * Patches differ
     */
    @Test
    public void patchesDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.1");
            
        assertFalse(lhs.equals(rhs));
    }
    
    /**
     * Prereleases differ, left is empty
     */
    @Test
    public void prereleasesDifferEmptyLhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-pre");
            
        assertFalse(lhs.equals(rhs));
    }
    
    /**
     * Prereleases differ, right is empty
     */
    @Test
    public void prereleasesDifferEmptyRhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-pre");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
            
        assertFalse(lhs.equals(rhs));
    }
    
    /**
     * Prereleases differ, neither is empty
     */
    @Test
    public void prereleasesDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-beta");
            
        assertFalse(lhs.equals(rhs));
    }
}
