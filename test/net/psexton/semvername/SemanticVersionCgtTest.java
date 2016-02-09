/*
 * SemanticVersionCgtTest.java, part of the SemVerName project
 * Created on Feb 9, 2016, 4:04:47 PM
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
 * Tests isCompatiblyGreaterThan method of SemanticVersion class.
 * @author PSexton
 */
public class SemanticVersionCgtTest {
    
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
