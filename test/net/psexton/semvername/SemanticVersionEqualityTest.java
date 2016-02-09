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
    
}
