/*
 * SemanticVersionValueOfValidity.java, part of the semvername-java project
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
public class SemanticVersionValueOfTest {
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Null is invalid
     */
    @Test
    public void invalidNull() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf(null);
    }
    
    /**
     * Empty is invalid
     */
    @Test
    public void invalidEmpty() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("");
    }
    
    /**
     * Valid, no prerelease
     */
    @Test
    public void validNoPre() {
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("1.2.3", semver.toString());
    }
    
    /**
     * Valid, with prerelease
     */
    @Test
    public void validWithPre() {
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-beta1");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("beta1", semver.getPrerelease());
        assertEquals("1.2.3-beta1", semver.toString());
    }
    
    /**
     * Invalid, no minor or patch
     */
    @Test
    public void invalidNoMinorNoPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1");
    }

    /**
     * Invalid, no minor or patch
     */
    @Test
    public void invalidNoPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2");
    }
    
    /**
     * Invalid, too many numeric parts
     */
    @Test
    public void invalidTooManyParts() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3.4");
    }
    
    /**
     * Invalid, hyphen but empty prerelease
     */
    @Test
    public void invalidNullPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-");
    }
    
    /**
     * Invalid, negative major
     */
    @Test
    public void invalidNegativeMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("-1.2.3-beta1");
    }

    /**
     * Invalid, negative minor
     */
    @Test
    public void invalidNegativeMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.-2.3-beta1");
    }
    
    /**
     * Invalid, negative patch
     */
    @Test
    public void invalidNegativePatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.-3-beta1");
    }
    
    /**
     * Invalid, disallowed char in prerelease
     */
    @Test
    public void invalidIllegalPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-@");
    }
    
    /**
     * Invalid, disallowed char in major
     */
    @Test
    public void invalidIllegalMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("one.2.3");
    }
    
    /**
     * Invalid, disallowed char in minor
     */
    @Test
    public void invalidIllegalMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2w.3");
    }
    
    /**
     * Invalid, disallowed char in patch
     */
    @Test
    public void invalidIllegalPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3E");
    }
    
    /**
     * Invalid, missing major
     */
    @Test
    public void invalidMissingMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf(".2.3");
    }
    
    /**
     * Invalid, missing minor
     */
    @Test
    public void invalidMissingMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1..3");
    }
    
    /**
     * Invalid, missing patch
     */
    @Test
    public void invalidMissingPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.");
    }
    
    /**
     * Invalid, extra period
     */
    @Test
    public void invalidExtraPeriod() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2..3");
    }
}
