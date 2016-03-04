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

import static org.hamcrest.Matchers.is;
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
        
        assertThat(instance.equals(null), is(false));
    }
    
    @Test
    public void noEqualityWithDifferentClass() {
        SemanticVersion instance = SemanticVersion.valueOf("1.2.3");
        
        assertThat(instance.equals("1.2.3"), is(false));
    }
    
    /**
     * Equal to self
     */
    @Test
    public void equalToSelf() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        
        assertThat(lhs.equals(lhs), is(true));
    }
    
    /**
     * Equal to same
     */
    @Test
    public void equalToSame() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
        
        assertThat(lhs.equals(rhs), is(true));
    }
    
    /**
     * Majors differ
     */
    @Test
    public void majorsDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("2.0.0");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Minors differ
     */
    @Test
    public void minorsDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.1.0");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Patches differ
     */
    @Test
    public void patchesDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.1");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Prereleases differ, left is empty
     */
    @Test
    public void prereleasesDifferEmptyLhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-pre");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Prereleases differ, right is empty
     */
    @Test
    public void prereleasesDifferEmptyRhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-pre");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Prereleases differ, neither is empty
     */
    @Test
    public void prereleasesDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-beta");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Builds differ, left is empty
     */
    @Test
    public void buildsDifferEmptyLhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0+pre");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Builds differ, right is empty
     */
    @Test
    public void buildsDifferEmptyRhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0+pre");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
            
        assertThat(lhs.equals(rhs), is(false));
    }
    
    /**
     * Builds differ, neither is empty
     */
    @Test
    public void buildsDiffer() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0+alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0+beta");
            
        assertThat(lhs.equals(rhs), is(false));
    }
}
