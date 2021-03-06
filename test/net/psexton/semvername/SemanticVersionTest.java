/*
 * SemanticVersionTest.java, part of the semvername-java project
 * Created on Feb 2, 2016, 11:25:42 AM
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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author PSexton
 */
public class SemanticVersionTest {
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Empty is valid
     */
    @Test
    public void validDefault() {
        SemanticVersion semver = new SemanticVersion();
        assertEquals(new Integer(0), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("0.0.0", semver.toString());
    }
    
    /**
     * Valid ctr, no prerelease
     */
    @Test
    public void validNoPre() {
        SemanticVersion semver = new SemanticVersion(1, 2, 3);
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("1.2.3", semver.toString());
    }
    
    /**
     * Valid ctr, no prerelease
     */
    @Test
    public void validWithPre() {
        SemanticVersion semver = new SemanticVersion(1, 2, 3, "beta1");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("beta1", semver.getPrerelease());
        assertEquals("1.2.3-beta1", semver.toString());
    }
    

    /**
     * Invalid ctr, null major
     */
    @Test
    public void invalidNullMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(null, 2, 3, "beta1");
    }

    /**
     * Invalid ctr, null minor
     */
    @Test
    public void invalidNullMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, null, 3, "beta1");
    }
    
    /**
     * Invalid ctr, null patch
     */
    @Test
    public void invalidNullPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, 2, null, "beta1");
    }
    
    /**
     * Invalid ctr, null prerelease
     */
    @Test
    public void invalidNullPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, 2, 3, null);
    }
    
    /**
     * Invalid ctr, negative major
     */
    @Test
    public void invalidNegativeMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(-1, 2, 3, "beta1");
    }

    /**
     * Invalid ctr, negative minor
     */
    @Test
    public void invalidNegativeMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, -2, 3, "beta1");
    }
    
    /**
     * Invalid ctr, negative patch
     */
    @Test
    public void invalidNegativePatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, 2, -3, "beta1");
    }
    
    /**
     * Invalid ctr, disallowed char in prerelease patch
     */
    @Test
    public void invalidIllegalPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = new SemanticVersion(1, 2, 3, "@");
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setMajor() {
        SemanticVersion orig = new SemanticVersion();
        SemanticVersion modified = orig.setMajor(1);
        assertNotSame(orig, modified);
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setMinor() {
        SemanticVersion orig = new SemanticVersion();
        SemanticVersion modified = orig.setMinor(2);
        assertNotSame(orig, modified);
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setPatch() {
        SemanticVersion orig = new SemanticVersion();
        SemanticVersion modified = orig.setPatch(3);
        assertNotSame(orig, modified);
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setPrerelease() {
        SemanticVersion orig = new SemanticVersion();
        SemanticVersion modified = orig.setPrerelease("rc");
        assertNotSame(orig, modified);
    }
    
    /**
     * Basic checking of hashcodes
     */
    @Test
    public void testHashCode() {
        SemanticVersion version1a = new SemanticVersion(1, 0, 0);
        SemanticVersion version1b = new SemanticVersion(1, 0, 0);
        SemanticVersion version2 = new SemanticVersion(1, 0, 1);
        
        assertEquals(version1a.hashCode(), version1b.hashCode());
        assertEquals(version1a.hashCode(), version1a.hashCode());
        assertFalse(version1a.hashCode() == version2.hashCode());
    }
}
