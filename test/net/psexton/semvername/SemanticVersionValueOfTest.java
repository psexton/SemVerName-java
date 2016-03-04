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
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-preBeta1");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("preBeta1", semver.getPrerelease());
        assertEquals("1.2.3-preBeta1", semver.toString());
    }
    
    /**
     * Valid, with build
     */
    @Test
    public void validWithBuild() {
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0+nightly021");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("nightly021", semver.getBuild());
        assertEquals("1.0.0+nightly021", semver.toString());
    }
    
    /**
     * Valid, pre and build
     */
    @Test
    public void validWithPreAndBuild() {
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0-dev+63hdeq");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("dev", semver.getPrerelease());
        assertEquals("63hdeq", semver.getBuild());
        assertEquals("1.0.0-dev+63hdeq", semver.toString());
    }
    
    /**
     * Valid, dot separated prerelease parts
     */
    @Test
    public void valid2SectionPre() {
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-beta.1");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("beta.1", semver.getPrerelease());
        assertEquals("1.2.3-beta.1", semver.toString());
    }
    
    /**
     * Valid, multiple dot separated prerelease parts
     */
    @Test
    public void valid3SectionPre() {
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-beta.26.32h");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("beta.26.32h", semver.getPrerelease());
        assertEquals("1.2.3-beta.26.32h", semver.toString());
    }
    
    /**
     * Valid, dot separated build parts
     */
    @Test
    public void valid3SectionBuild() {
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0+nightly.021.3q");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("nightly.021.3q", semver.getBuild());
        assertEquals("1.0.0+nightly.021.3q", semver.toString());
    }
    
    /**
     * Valid, both pre and build strings
     */
    @Test
    public void validSectionedPreAndSectionedBuild() {
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-alpha.1+2016.02.09");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(2), semver.getMinor());
        assertEquals(new Integer(3), semver.getPatch());
        assertEquals("alpha.1", semver.getPrerelease());
        assertEquals("2016.02.09", semver.getBuild());
        assertEquals("1.2.3-alpha.1+2016.02.09", semver.toString());
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
     * Invalid, plus sign but empty build
     */
    @Test
    public void invalidNullBuild() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3+");
    }
    
    /**
     * Invalid, negative major
     */
    @Test
    public void invalidNegativeMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("-1.2.3");
    }

    /**
     * Invalid, negative minor
     */
    @Test
    public void invalidNegativeMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.-2.3");
    }
    
    /**
     * Invalid, negative patch
     */
    @Test
    public void invalidNegativePatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.-3");
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
     * Invalid, trailing separator in prerelease
     */
    @Test
    public void invalidIllegalDotSuffixInPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-foo.");
    }
    
    /**
     * Invalid, leading separator in prerelease
     */
    @Test
    public void invalidIllegalDotPrefixInPrerelease() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-.foo");
    }
    
    /**
     * Invalid, disallowed char in build
     */
    @Test
    public void invalidIllegalBuild() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3+@");
    }
    
    /**
     * Invalid, disallowed char in build
     */
    @Test
    public void invalidIllegalDotSuffixInBuild() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3+bar.");
    }
    
    /**
     * Invalid, trailing separator in build
     */
    @Test
    public void invalidIllegalDotPrefixInBuild() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3+.bar");
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
    
    /**
     * All zeroes is valid
     */
    @Test
    public void validAllZero() {
        SemanticVersion semver = SemanticVersion.valueOf("0.0.0");
        assertEquals(new Integer(0), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("0.0.0", semver.toString());
    }
    
    /**
     * Only patch nonzero is valid
     */
    @Test
    public void validMajorMinorZero() {
        SemanticVersion semver = SemanticVersion.valueOf("0.0.1");
        assertEquals(new Integer(0), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(1), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("0.0.1", semver.toString());
    }
    
    /**
     * Only minor nonzero is valid
     */
    @Test
    public void validMajorPatchZero() {
        SemanticVersion semver = SemanticVersion.valueOf("0.1.0");
        assertEquals(new Integer(0), semver.getMajor());
        assertEquals(new Integer(1), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("0.1.0", semver.toString());
    }
    
    /**
     * Only major nonzero is valid
     */
    @Test
    public void validMinorPatchZero() {
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("", semver.getPrerelease());
        assertEquals("1.0.0", semver.toString());
    }
    
    /**
     * Valid, with hyphen in prerelease
     */
    @Test
    public void validWithHyphenInPre() {
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0-abc-def");
        assertEquals(new Integer(1), semver.getMajor());
        assertEquals(new Integer(0), semver.getMinor());
        assertEquals(new Integer(0), semver.getPatch());
        assertEquals("abc-def", semver.getPrerelease());
        assertEquals("1.0.0-abc-def", semver.toString());
    }
    
    /**
     * Invalid, leading zero in major
     */
    @Test
    public void invalidLeadingZeroInMajor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("01.2.3");
    }
    
    /**
     * Invalid, leading zero in minor
     */
    @Test
    public void invalidLeadingZeroInMinor() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.02.3");
    }
    
    /**
     * Invalid, leading zero in patch
     */
    @Test
    public void invalidLeadingZeroInPatch() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.03");
    }
    
    /**
     * Invalid, underscore in pre
     */
    @Test
    public void invalidUnderscoreInPre() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0-abc_def");
    }
    
    /**
     * Invalid, illegal character in a prerelease identifier
     */
    @Test
    public void invalidIllegalPrereleaseIdentifier() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3-foo.$.bar");
    }
    
    /**
     * Invalid, underscore in build
     */
    @Test
    public void invalidUnderscoreInBuild() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.0.0+abc_def");
    }
    
    /**
     * Invalid, illegal character in a build identifier
     */
    @Test
    public void invalidIllegalBuildIdentifier() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersion semver = SemanticVersion.valueOf("1.2.3+foo.$.bar");
    }
}
