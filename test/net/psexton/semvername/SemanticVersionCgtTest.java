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

import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests isCompatiblyGreaterThan method of SemanticVersion class.
 * @author PSexton
 */
public class SemanticVersionCgtTest {
    
    @Test
    public void sameMajorZeroLhsLess() {
        SemanticVersion version04 = SemanticVersion.valueOf("0.4.0");
        SemanticVersion version041 = SemanticVersion.valueOf("0.4.1");
        
        assertThat(version04.isCompatiblyGreaterThan(version041), is(false)); // 0.4.0 !c> 0.4.1
    }
    
    @Test
    public void sameMajorZeroLhsMore() {
        SemanticVersion version04 = SemanticVersion.valueOf("0.4.0");
        SemanticVersion version041 = SemanticVersion.valueOf("0.4.1");
        
        assertThat(version041.isCompatiblyGreaterThan(version04), is(false)); // 0.4.1 !c> 0.4.0
    }
    
    @Test
    public void prerelease() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.2.3");
        SemanticVersion version1p1 = SemanticVersion.valueOf("1.2.3-p1");
        
        assertThat(version1.isCompatiblyGreaterThan(version1p1), is(true)); // 1.2.3 c> 1.2.3-p1
    }
    
    @Test
    public void differentMajorN() {
        SemanticVersion version10 = SemanticVersion.valueOf("10.20.30");
        SemanticVersion version1 = SemanticVersion.valueOf("1.2.3");
        
        assertThat(version10.isCompatiblyGreaterThan(version1), is(false)); // 10.20.30 !c> 1.2.3
    }
    
    @Test
    public void sameMajorNLhsLess() {
        SemanticVersion version12 = SemanticVersion.valueOf("1.2.0");
        SemanticVersion version13 = SemanticVersion.valueOf("1.3.0");
        
        assertThat(version12.isCompatiblyGreaterThan(version13), is(false)); // 1.2.0 !c> 1.3.0
    }
    
    @Test
    public void sameMajorNLhsMore() {
        SemanticVersion version12 = SemanticVersion.valueOf("1.2.0");
        SemanticVersion version13 = SemanticVersion.valueOf("1.3.0");
        
        assertThat(version13.isCompatiblyGreaterThan(version12), is(true)); // 1.3.0 c> 1.2.0
    }
    
    @Test
    public void equalityIsCgt() {
        SemanticVersion version1 = SemanticVersion.valueOf("1.2.3");
        assertThat(version1.isCompatiblyGreaterThan(version1), is(true));
    }
}
