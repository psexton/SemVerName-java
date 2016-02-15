/*
 * SemanticVersionComparableTest.java, part of the semvername-java project
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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests compareTo method of SemanticVersion class.
 * @author PSexton
 */
public class SemanticVersionComparableTest {
    
    @Test
    public void equalToSelf() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        assertThat(lhs.compareTo(lhs), is(0)); // 1.0.0 == 1.0.0
    }
    
    @Test
    public void equalToSame() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
        assertThat(lhs.compareTo(rhs), is(0)); // 1.0.0 == 1.0.0
    }
    
    @Test
    public void majorsDifferLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.9.9");
        SemanticVersion rhs = SemanticVersion.valueOf("2.0.0");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.9.9 < 2.0.0
    }
    
    @Test
    public void minorsDifferLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.1.9");
        SemanticVersion rhs = SemanticVersion.valueOf("1.2.0");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.1.9 < 1.2.0
    }
    
    @Test
    public void patchesDifferLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.1.1");
        SemanticVersion rhs = SemanticVersion.valueOf("1.1.2");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.1.1 < 1.1.2
    }
    
    @Test
    public void majorsDifferLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.9.9");
        SemanticVersion lhs = SemanticVersion.valueOf("2.0.0");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 2.0.0 > 1.9.9
    }
    
    @Test
    public void minorsDifferLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.1.9");
        SemanticVersion lhs = SemanticVersion.valueOf("1.2.0");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.2.0 > 1.1.9
    }
    
    @Test
    public void patchesDifferLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.1.1");
        SemanticVersion lhs = SemanticVersion.valueOf("1.1.2");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.1.2 > 1.1.1
    }
    
    @Test
    public void majorsAreNumeric() {
        SemanticVersion lhs = SemanticVersion.valueOf("2.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("10.0.0");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 2.0.0 < 10.0.0
    }
    
    @Test
    public void minorsAreNumeric() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.2.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.10.0");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.2.0 < 1.10.0
    }
    
    @Test
    public void patchesAreNumeric() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.2");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.10");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.2 < 1.0.10
    }
    
    @Test
    public void prereleasesDifferEmptyLhs() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-pre");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0 > 1.0.0-pre
    }
    
    @Test
    public void prereleasesDifferEmptyRhs() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-pre");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-pre < 1.0.0
    }
    
    @Test
    public void singleAlphaIdLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-beta");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-alpha < 1.0.0-beta
    }
    
    @Test
    public void singleAlphaIdLhsMore(){
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-beta");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0-beta > 1.0.0-alpha
    }
    
    @Test
    public void singleNumericIdLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-9");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-10");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-9 < 1.0.0-10
    }
    
    @Test
    public void singleNumericIdLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-9");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-10");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0-10 > 1.0.0-9
    }
    
    @Test
    public void differentNumberOfIdMixedLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha.1");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-alpha < 1.0.0-alpha.1
    }
    
    @Test
    public void differentNumberOfIdMixedLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha.1");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0-alpha.1 > 1.0.0-alpha
    }
    
    @Test
    public void numericIdIsLowerLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha.beta");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-alpha.1 < 1.0.0-alpha.beta
    }
    
    @Test
    public void numericIdIsLowerLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha.beta");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0-alpha.beta > 1.0.0-alpha.1
    }
    
    @Test
    public void differentNumberOfIdBothAlphaLhsLess() {
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-alpha.beta");
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-beta");
        assertThat(lhs.compareTo(rhs), is(lessThan(0))); // 1.0.0-alpha.beta < 1.0.0-beta
    }
    
    @Test
    public void differentNumberOfIdBothAlphaLhsMore() {
        SemanticVersion rhs = SemanticVersion.valueOf("1.0.0-alpha.beta");
        SemanticVersion lhs = SemanticVersion.valueOf("1.0.0-beta");
        assertThat(lhs.compareTo(rhs), is(greaterThan(0))); // 1.0.0-beta > 1.0.0-alpha.beta
    }
    
    // =====
    
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
    
}
